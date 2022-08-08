package com.gl.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gl.entity.UserCourseOrder;

public interface OrderService extends IService<UserCourseOrder> {
    UserCourseOrder geneOrder(Integer courseId,Integer userId);
}
