package com.gl.controller;

import com.gl.entity.Course;
import com.gl.entity.PageEntity;
import com.gl.service.ICourseService;
import com.gl.service.impl.CourseIndexService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Map;

@Slf4j
@RestController
public class CourseSearchController {
    @Autowired
    private ICourseService courseService;
    @Autowired
    private CourseIndexService courseIndexService;

    @PostMapping("/search-courses")
    public ResponseEntity<PageEntity<Course>> searchCoursePage(@RequestBody Map<String,String> args, HttpServletRequest request){
        // 本次携带进来的header信息
        String authorization = request.getHeader("Authorization");
        System.out.println(authorization);
        log.info("搜索参数：{}",args);
        PageEntity<Course> page = courseService.searchCoursePage(args,request);
        log.info("搜索结果：{}",page);
        return ResponseEntity.ok(page);
    }


    @PostMapping("/course/init")
    public void  initCourse() throws IOException {
        courseIndexService.initCourseIndex();
    }
}
