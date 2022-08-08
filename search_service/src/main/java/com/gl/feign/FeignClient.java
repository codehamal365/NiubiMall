package com.gl.feign;

import com.gl.entity.Course;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@org.springframework.cloud.openfeign.FeignClient(value = "course-service")
public interface FeignClient {
    @GetMapping("/courses")
     List<Course> selectAll();
}
