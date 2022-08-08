package com.gl.service.impl;

import com.gl.entity.Course;
import com.gl.entity.PageEntity;
import com.gl.mapper.CourseMapper;
import com.gl.service.ICourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author Long
 * @since 2021-12-21
 */
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements ICourseService {
    @Autowired
    private CourseMapper courseMapper;

    @Override
    public PageEntity<Course> findCoursePages(Integer current, Integer size, String orientationName, String subOrientationName, String typeName, String courseName, String field, String type) {
        if (current - 1 < 0){
            current = current;
        }else {
            current = current - 1;
        }
        return new PageEntity<Course>(courseMapper.selectCoursePages(current, size, orientationName, subOrientationName, typeName, courseName, field, type),current,null,5);
    }

    @Override
    public List<Course> findAllCourse() {
        return courseMapper.selectAllCourses();
    }
}
