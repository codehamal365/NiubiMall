package com.gl.controller;


import com.gl.entity.CourseOrientation;
import com.gl.service.ICourseOrientationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Long
 * @since 2021-12-21
 */
@RestController
public class CourseOrientationController {
    @Autowired
    private ICourseOrientationService courseOrientationService;

    @GetMapping("/course-orientations")
    private ResponseEntity<List<CourseOrientation>> findAllCourseOrientations(){
        return ResponseEntity.ok(courseOrientationService.findAllCourseOrientations());
    }


    @GetMapping("/course-orientations/parent/{parentId}")
    private ResponseEntity<List<CourseOrientation>> findCourseOrientationsByPid(@PathVariable("parentId") Integer parentId){
        return ResponseEntity.ok(courseOrientationService.findCourseOrientationsByParentId(parentId));
    }
}
