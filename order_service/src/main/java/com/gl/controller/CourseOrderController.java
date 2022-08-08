package com.gl.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayUtil;
import com.github.wxpay.sdk.WXPayXmlUtil;
import com.gl.entity.Course;
import com.gl.entity.UserCourseOrder;
import com.gl.feign.CourseFeignClient;
import com.gl.feign.SearchFeignClient;
import com.gl.mapper.OrderMapper;
import com.gl.service.OrderService;
import com.gl.service.WxPayService;
import com.gl.utlils.HttpClientUtils;
import com.gl.utlils.QRBarCodeUtil;
import com.gl.utlils.StreamUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
public class CourseOrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private WxPayService wxPayService;
    @Autowired
    private CourseFeignClient courseFeignClient;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private SearchFeignClient searchFeignClient;

    // 生成订单 和 二维码url
    @GetMapping("wxPay/{courseId}/{userId}")
    public Map<String,String> wxPay(@PathVariable("courseId") Integer courseId,
                                        @PathVariable("userId") Integer userId,
                                        HttpServletRequest request,
                                        HttpServletResponse response) throws IOException {
        UserCourseOrder order = orderService.geneOrder(courseId, userId);
        Course course = courseFeignClient.selectCourseById(courseId);
        course.setPrice(course.getDiscounts());

        Map<String, String> resultMap = wxPayService.createNative(order.getTradeNo(), request.getRemoteAddr(), course);
        resultMap.put("tradeNo",order.getTradeNo());
        System.out.println(resultMap);
        String code_url = resultMap.get("code_url");
        return resultMap;
    }

    // 生成二维码
    @GetMapping("qrCode")
    public void wxPay(String url,HttpServletResponse response) throws IOException {
        QRBarCodeUtil.createCodeToOutputStream(url,response.getOutputStream());

    }

    // 检查订单
    @GetMapping("/wxpay/checkorder")
    public ResponseEntity<String> checkOrder(@RequestParam("tradeNo") String tradeNo) throws Exception {
        // 组装请求参数
        Map<String,String> requestMap = new HashMap<>();
        requestMap.put("out_trade_no",tradeNo);
        requestMap.put("appid","wx307113892f15a42e");
        requestMap.put("mch_id","1508236581");
        requestMap.put("nonce_str", WXPayUtil.generateNonceStr());
        // 组装签名，将参数转化为xml字符串，并且在字符串的最后追加计算的签名
        String requestXml = WXPayUtil.generateSignedXml(requestMap,"HJd7sHGHd6djgdgFG5778GFfhghghgfg");

        // 发送查询请求
        String responseXml = HttpClientUtils.postParameters("https://api.mch.weixin.qq.com/pay/orderquery", requestXml);
        // 解析响应xml 获得响应结果
        Map<String, String> responseMap = WXPayUtil.xmlToMap(responseXml);
        System.out.println(responseMap);
        // 判断支付情况  如果支付成功，关闭前端的轮询定时任务
        if ("SUCCESS".equals(responseMap.get("return_code")) &&
            "SUCCESS".equals(responseMap.get("result_code")) &&
            "SUCCESS".equals(responseMap.get("trade_state"))){
            return ResponseEntity.ok("SUCCESS");
        }else {
            return ResponseEntity.ok("ERROR");
        }

    }


    // 支付回调
//    @GlobalTransactional
    @PostMapping("/wxpay/callback")
    public String wxNotify(HttpServletRequest request,HttpServletResponse response) throws Exception {
        ServletInputStream inputStream = request.getInputStream();
        // 得到回调信息
        String notifyUrl = StreamUtils.InputStreamTOString(inputStream, "UTF-8");
        System.out.println(notifyUrl);
        Map<String, String> resultMap = WXPayUtil.xmlToMap(notifyUrl);
        System.out.println(resultMap);
        // 如果支付成果
        if ("SUCCESS".equals(resultMap.get("return_code")) &&
            "SUCCESS".equals(resultMap.get("result_code")) ){
            // 返回响应
            String xml = "<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>";
            log.info("===微信公众号回调业务处理完成，告诉微信"+xml);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/xml; charset=utf-8");
            PrintWriter out = response.getWriter();
            out.print(xml);
            out.close();

            // 获取支付成功的订单号
            String tradeNo = resultMap.get("out_trade_no");
            // 通过订单号查订单
            QueryWrapper<UserCourseOrder> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("trade_no", tradeNo);
            UserCourseOrder userOrder = orderMapper.selectOne(queryWrapper);
            System.out.println(userOrder);
            // 如果已经支付 直接返回
            if (userOrder.getStatus() == 1){
                return null;
            }

            if (userOrder.getStatus() == 0){
                // 通过订单号来修改支付状态
                UpdateWrapper<UserCourseOrder> wrapper = new UpdateWrapper<>();
                wrapper.eq("trade_no", tradeNo);
                UserCourseOrder order = new UserCourseOrder();
                order.setStatus(1);
                orderService.update(order,wrapper);
                // 修改销量
                if (userOrder != null){
                    // 获得课程id
                    Integer courseId = userOrder.getCourseId();
                    Course course = courseFeignClient.selectCourseById(courseId);
                    // 得到当前课程的销量
                    Integer sales = course.getSales();
                    sales = sales + 1;
                    // 修改销量
                    courseFeignClient.updateSale(courseId,sales);
                    // 同步es数据
                    searchFeignClient.initCourse();
                }

            }
        }else {
            // 返回响应
            Map<String,String> falseMap = new HashMap<>();
            falseMap.put("return_code","FAIL");
            falseMap.put("return_msg","ERROR");
            String falseXml = WXPayUtil.mapToXml(falseMap);
            response.setContentType("text/xml");
            log.info("===交易失败");
            return "支付失败";
        }

       return null;
    }


    // 查询用户的已购课程
    @GetMapping("order/getOKOrderCourseIds/{userId}")
    public List<Integer> selectPayCourse(@PathVariable("userId") Integer userId){
        // 通过用户id 查询已购买的课程集合
        QueryWrapper<UserCourseOrder> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",userId);
        wrapper.eq("status",1);
        List<UserCourseOrder> orders = orderMapper.selectList(wrapper);
        // 将课程id封装为list集合返回前台
        List<Integer> ids = new ArrayList<>();
        for (UserCourseOrder order : orders) {
            ids.add(order.getCourseId());
        }
        return ids;
    }
}
