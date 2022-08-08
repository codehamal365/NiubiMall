package com.gl.service.impl;

import com.github.wxpay.sdk.WXPayUtil;
import com.gl.entity.Course;
import com.gl.feign.CourseFeignClient;
import com.gl.service.WxPayService;
import com.gl.utlils.HttpClientUtils;
import com.gl.utlils.WxPayProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class WxPayServiceImpl implements WxPayService {
    @Autowired
    private WxPayProperties wxPayProperties;
    @Autowired
    private CourseFeignClient courseFeignClient;
    @Override
    public Map<String, String> createNative(String orderNo, String remoteAddr,Course course) {
        Map<String,String> resultMap = new HashMap<>();
        // 组装参数
        HashMap<String,String> params = new HashMap<>();
        try {
            // 调用统一下单的接口
            // 公众号id
            params.put("appid","wx307113892f15a42e");
            // 商户号
            params.put("mch_id","1508236581");
            // 随机数
            params.put("nonce_str", WXPayUtil.generateNonceStr());
            // 商品描述
            params.put("body",course.getCourseName());
            // 订单号
            params.put("out_trade_no", orderNo);
            // 商品金额 单位分
            int price = Integer.valueOf(course.getDiscounts().intValue());
            params.put("total_fee",price+"");
            params.put("total_fee","1");
            // 终端IP
            params.put("spbill_create_ip",remoteAddr);
            // 通知地址 (回调地址)
            params.put("notify_url","http://hmiyic.natappfree.cc/wxpay/callback");
            // 交易类型
            params.put("trade_type","NATIVE");

            // 组装签名，将参数转化为xml字符串，并且在字符串的最后追加计算的签名
            String xmlParams = WXPayUtil.generateSignedXml(params,"HJd7sHGHd6djgdgFG5778GFfhghghgfg");
            log.info(xmlParams);
            // 发送请求  post请求
            String result = HttpClientUtils.postParameters("https://api.mch.weixin.qq.com/pay/unifiedorder", xmlParams);
            log.info("resultXml" + result);

            // 将响应结果转化为map
            resultMap = WXPayUtil.xmlToMap(result);
            String code_url = resultMap.get("code_url");
            System.out.println(code_url);

        } catch (Exception e) {
            log.error(String.valueOf(new RuntimeException("下单错误")));
            e.printStackTrace();
        }
        return resultMap;
    }
}
