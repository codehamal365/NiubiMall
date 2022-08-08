package com.gl.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gl.entity.UserCourseOrder;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderMapper extends BaseMapper<UserCourseOrder> {
    void insertOrder(UserCourseOrder userCourseOrder);

    List<UserCourseOrder> selectOrdersByIds(@Param("ids") List<Long> ids);
}
