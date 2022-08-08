package com.gl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gl.entity.Course;
import com.gl.mapper.CourseMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class CourseServiceApplicationTests {
    @Autowired
    private CourseMapper courseMapper;
    @Test
    void contextLoads() {
        List<Course> courses = courseMapper.selectCoursePages(null, null, null, null, null,
                null, null, null);
        System.out.println(courses);
    }
    @Test
    void contextLoads2() {
        List<Course> list = courseMapper.selectAllCourses();
        System.out.println(list);
    }

    @Test
    void contextLoads3() {

        Course course = courseMapper.selectCourseById(13);
        System.out.println(course);
    }
}
