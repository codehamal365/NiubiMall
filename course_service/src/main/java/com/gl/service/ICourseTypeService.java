package com.gl.service;

import com.gl.entity.CourseType;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Long
 * @since 2021-12-21
 */
public interface ICourseTypeService extends IService<CourseType> {
    List<CourseType> findAllCourseTypes();
}
