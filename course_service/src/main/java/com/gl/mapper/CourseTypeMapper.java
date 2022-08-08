package com.gl.mapper;

import com.gl.entity.CourseType;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Long
 * @since 2021-12-21
 */
public interface CourseTypeMapper extends BaseMapper<CourseType> {
    List<CourseType> selectAllCourseTypes();
}
