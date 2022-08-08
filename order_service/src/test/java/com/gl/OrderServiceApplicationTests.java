package com.gl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.gl.entity.UserCourseOrder;
import com.gl.mapper.OrderMapper;
import com.gl.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
class OrderServiceApplicationTests {
    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderService orderService;

    @Test
    void contextLoads() {
        Timestamp timestamp = new Timestamp(new Long(200L));
        for (int i = 0; i < 100; i++) {
            orderMapper.insertOrder(new UserCourseOrder(0L,0,0,0,timestamp,timestamp,0,"aaa",3,30));
        }
    }

    @Test
    void contextLoad2s() {
        List<UserCourseOrder> userCourseOrders = orderMapper.selectOrdersByIds(Arrays.asList(683415602068455425L,
                683416955075428353L,683416956434382849L));
        System.out.println(userCourseOrders);
    }

    @Test
    void test05(){
        UserCourseOrder userCourseOrder = orderService.geneOrder(13, 1);
        System.out.println(userCourseOrder);
    }


    @Test
    void test06(){
        UpdateWrapper<UserCourseOrder> wrapper = new UpdateWrapper<>();
        wrapper.eq("trade_no", "20220118114459214607312");
        UserCourseOrder order = new UserCourseOrder();
        order.setStatus(1);
        orderService.update(order,wrapper);
    }




}
