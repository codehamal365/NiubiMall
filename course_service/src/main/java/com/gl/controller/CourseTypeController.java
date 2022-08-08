package com.gl.controller;


import com.gl.entity.CourseType;
import com.gl.service.ICourseTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
public class CourseTypeController {
    @Autowired
    private ICourseTypeService iCourseTypeService;


    @GetMapping("/course-types")
    private ResponseEntity<List<CourseType>> selectAllCourseTypes(){
        List<CourseType> allCourseTypes = iCourseTypeService.findAllCourseTypes();
        return ResponseEntity.ok(allCourseTypes);
    }
}
