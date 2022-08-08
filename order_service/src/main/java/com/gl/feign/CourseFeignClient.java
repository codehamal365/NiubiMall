package com.gl.feign;

import com.gl.entity.Course;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("course-service")
public interface CourseFeignClient {
    @GetMapping("/course/{id}")
    public Course selectCourseById(@PathVariable("id") Integer id);

    @GetMapping("/course/sale/{courseId}/{sales}")
    public void updateSale(@PathVariable("courseId") Integer courseId,@PathVariable("sales") Integer sales);


}
