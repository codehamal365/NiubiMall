package com.gl;

import com.gl.dao.ElasticsearchDAO;
import com.gl.entity.Course;
import com.gl.entity.ElasticsearchEntity;
import com.gl.feign.FeignClient;
import com.gl.service.impl.CourseIndexService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class SearchServiceApplicationTests {
    @Autowired
    private ElasticsearchDAO elasticsearchDAO;

    @Autowired
    private FeignClient feignClient;

    @Autowired
    private CourseIndexService courseIndexService;





    @Test
    void contextLoads() throws IOException {
        courseIndexService.initCourseIndex();

    }

    @Test
    void contextLoads2() throws IOException {
        List<Course> courses = feignClient.selectAll();
        List<ElasticsearchEntity> list = new ArrayList<>();
        for (int i = 0; i < courses.size(); i++) {
            list.add(new ElasticsearchEntity(String.valueOf(i),courses.get(i)));
        }
         elasticsearchDAO.insertBatch("course",list);
    }


    @Test
    void contextLoads3() throws IOException {
        List<Course> list = courseIndexService.searchByCourseName("大数据");
        System.out.println(list);
    }


    @Test
    void aaa(){
        System.out.println("amarsoft".substring(3,5));
    }
}
