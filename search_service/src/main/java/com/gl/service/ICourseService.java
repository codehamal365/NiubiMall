package com.gl.service;

import com.gl.entity.Course;
import com.gl.entity.PageEntity;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface ICourseService {
    String INDEX_NAME = "course";

    void initCourseIndex() throws IOException;

    void saveOrUpdate(Course course);

    void removeById(Long id);

    List<Course> searchByCourseName(String courseName) throws IOException;

    PageEntity<Course> searchCoursePage(Map<String, String> map, HttpServletRequest request);
}
