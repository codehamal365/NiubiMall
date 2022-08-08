package com.gl.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gl.entity.*;
import com.gl.mapper.CourseMapper;
import com.gl.service.ICourseSectionService;
import com.gl.service.ICourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.*;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author Long
 * @since 2021-12-21
 */

@RestController
public class CourseController {
    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private ICourseSectionService courseSectionService;

    @Autowired
    private ICourseService courseService;
//    @PostMapping("/search-courses")
//    public ResponseEntity<PageEntity<Course>> findAll(@RequestBody String args) throws JsonProcessingException {
//        // 通过jackson将字符串转化为map集合
//        ObjectMapper objectMapper = new ObjectMapper();
//        String size = "";
//        String orientationName = "";
//        String subOrientationName = "";
//        String typeName = "";
//        String courseName = "";
//        String field = "";
//        String type = "";
//        Map map = objectMapper.readValue(args, Map.class);
//        System.out.println(map);
//        String current = map.get("current").toString();
//        if (map.get("size")!=null){
//             size = map.get("size").toString();
//        }
//
//        Object search = map.get("search");
//        Object sort = map.get("sort");
//
//
//        String strSearch = search.toString();
//        String strSort = sort.toString();
//        // 转化子集合
//        Map map1 = objectMapper.readValue(strSearch, Map.class);
//        Map map2 = objectMapper.readValue(strSort, Map.class);
//
//        if (map1.get("orientation.parent.orientationName") != null){
//            orientationName = map1.get("orientation.parent.orientationName").toString();
//        }
//
//        if (map1.get("orientation.orientationName") != null){
//            subOrientationName = map1.get("orientation.orientationName").toString();
//        }
//
//        if (map1.get("type.typeName") != null){
//            typeName = map1.get("type.typeName").toString();
//        }
//
//        if (map1.get("courseName") != null){
//            courseName = map1.get("courseName").toString();
//        }
//
//        if (map2.get("field") != null){
//            field = map2.get("field").toString();
//        }
//
//        if (map2.get("type") != null){
//            type = map2.get("type").toString();
//        }
//        System.out.println("orientationName = " + orientationName);
//        System.out.println("subOrientationName = " + subOrientationName);
//        System.out.println("typeName = " + typeName);
//        System.out.println("courseName = " + courseName);
//        System.out.println("field = " + field);
//        System.out.println("type = " + type);
//        System.out.println("current = " + current);
//        System.out.println("size = " + size);
//        long total = courseService.count();
//        System.out.println("total = " + total);
//        PageEntity<Course> coursePages = courseService.findCoursePages(Integer.parseInt(current), 5, orientationName, subOrientationName, typeName, courseName, field, type);
//        for (Course coursePage : coursePages.getData()) {
//            Teacher teacher = coursePage.getTeacher();
//            System.out.println(teacher);
//            CourseType courseType = coursePage.getCourseType();
//            System.out.println(courseType);
//            CourseOrientation courseOrientation = coursePage.getCourseOrientation();
//            System.out.println(courseOrientation);
//        }
//        PageEntity<Course> listPageEntity = new PageEntity<>();
//        listPageEntity.setData(coursePages.getData());
//        listPageEntity.setPageSize(5);
//        listPageEntity.setTotal((int) total);
//        return ResponseEntity.ok(listPageEntity);
//    };

    @GetMapping("/courses")
    public List<Course> selectAll(){
        List<Course> courses = courseService.findAllCourse();
        return courses;
    }

    // 查询单个课程
    @GetMapping("/course/{id}")
    public Course selectCourseById(@PathVariable("id") Integer id){
        Course course = courseMapper.selectCourseById(id);
        return course;
    }

    // 通过课程ids查询课程集合
    @GetMapping("/course/getMyCoursesById")
    public ResponseEntity<List<Course>> selectCoursesByIds(@RequestParam("ids") String ids){
        System.out.println(ids);
        List<String> list = new ArrayList<>();
        String[] split = ids.split("&");
        for (String str : split) {
            String[] strings = str.split("=");
            list.add(strings[1]);
        }
        System.out.println(list);
        List<Course> courses = courseMapper.selectCoursesByIds(list);
        System.out.println(courses);
        return ResponseEntity.ok(courses);
    }

    // 通过课程id 和 销量 增加销量
    @GetMapping("/course/sale/{courseId}/{sales}")
    public void updateSale(@PathVariable("courseId") Integer courseId,@PathVariable("sales") Integer sales){
        UpdateWrapper<Course> wrapper = new UpdateWrapper<>();
        wrapper.eq("id", courseId);
        Course course = new Course();
        course.setSales(sales);
        courseService.update(course,wrapper);
    }
}
