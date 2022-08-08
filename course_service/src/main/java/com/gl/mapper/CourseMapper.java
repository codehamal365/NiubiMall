package com.gl.mapper;

import com.gl.entity.Course;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author Long
 * @since 2021-12-21
 */
public interface CourseMapper extends BaseMapper<Course> {
    List<Course> selectCoursePages(@Param("current") Integer current,
                                   @Param("size") Integer size,
                                   @Param("orientationName") String orientationName,
                                   @Param("subOrientationName") String subOrientationName,
                                   @Param("typeName") String typeName,
                                   @Param("courseName") String courseName,
                                   @Param("field") String field,
                                   @Param("type") String type);

    List<Course> selectAllCourses();

    Course insertCourse(@RequestBody Course course);

    Course selectCourseById(Integer id);

    List<Course> selectCoursesByIds(List<String> ids);

}
