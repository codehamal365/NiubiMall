package com.gl.service;

import com.gl.entity.Course;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gl.entity.PageEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author Long
 * @since 2021-12-21
 */
public interface ICourseService extends IService<Course> {
    PageEntity<Course> findCoursePages(Integer current,
                                       Integer size,
                                       String orientationName,
                                       String subOrientationName,
                                       String typeName,
                                       String courseName,
                                       String field,
                                       String type);

    List<Course> findAllCourse();
}
