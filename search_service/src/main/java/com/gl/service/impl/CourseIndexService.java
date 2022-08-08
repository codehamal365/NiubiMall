package com.gl.service.impl;

import com.gl.dao.ElasticsearchDAO;
import com.gl.entity.*;
import com.gl.feign.FeignClient;
import com.gl.service.ICourseService;
import com.gl.util.JSONUtil;
import com.gl.util.JwtUtil;
import com.gl.util.RsaUtil;
import lombok.SneakyThrows;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class CourseIndexService implements ICourseService {
    // 定义索引名称
    public static final String INDEX_NAME = "course";

    @Autowired
    private RestHighLevelClient client;

    @Autowired
    private ElasticsearchDAO dao;

    private static final Integer EXPIRE_DATE = 60 * 30;

    // 通过feign调用远程服务的查询所有课程功能
    @Autowired
    private FeignClient feignClient;

    public void initCourseIndex() throws IOException {
        // 如果存在该索引，则删除索引
        if (dao.existIndex(INDEX_NAME)) {
            dao.deleteIndex(INDEX_NAME);
        }
        // 不存在，则创建该索引
        dao.createIndex(INDEX_NAME);
        List<ElasticsearchEntity> list = new ArrayList<>();
        // 通过feign调用远程服务，查询到所有课程的集合
        List<Course> courses = feignClient.selectAll();
        System.out.println(courses);
        // 将课程集合封装到ES实体类集合
        for (int i = 0; i < courses.size(); i++) {
            list.add(new ElasticsearchEntity(String.valueOf(i), courses.get(i)));
        }
        // 批量插入数据
        dao.insertBatch("course", list);
    }

    @Override
    public void saveOrUpdate(Course course) {
        try {
            dao.saveOrUpdate(INDEX_NAME, new ElasticsearchEntity(String.valueOf(course.getId()), course));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void removeById(Long id) {
        try {
            dao.deleteByQuery(INDEX_NAME, QueryBuilders.termQuery("id", id));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Course> searchByCourseName(String courseName) {
        try {
            // 使用match查询，对课程名称搜索
            SearchSourceBuilder builder = new SearchSourceBuilder();
            // 执行搜索方式
            builder.query(QueryBuilders.matchQuery("courseName", courseName));
            List<Course> list = null;
            list = dao.search(INDEX_NAME, builder, Course.class);
            return list;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    @SneakyThrows
    @Override
    public PageEntity<Course> searchCoursePage(Map<String, String> map, HttpServletRequest request) {
        try {
            // 读取header
            String token = request.getHeader("Authorization");

            //解析该token为用户对象
            User user = JwtUtil.getUserInfoFromToken(token, RsaUtil.publicKey);
            // 给token做延时处理，生成一个新的token 原有基础上增加30分钟
            String userToken = JwtUtil.generateToken(user.getId().toString(), user.getUsername(), user.getRealname(), user.getIcon(), RsaUtil.privateKey, EXPIRE_DATE);
            //获得当前页数和长度
            int current = Integer.valueOf(map.get("current"));
            int size = Integer.valueOf(map.get("size"));
            //获得过滤条件和排序方式
            String search = map.get("search");
            String sort = map.get("sort");
            Map<String, String> searchMap = JSONUtil.parseMap(search);
            Map<String, String> sortMap = JSONUtil.parseMap(sort);
            //执行分页查询
            PageEntity<Course> coursePageEntity = dao.searchPage(INDEX_NAME, searchMap, sortMap, (current - 1) * size, size, Course.class);
            // 将加密信息包装到分页对象中一起返回为前端
            UserVO userVO = new UserVO();
            userVO.setUser(user);
            userVO.setToken(userToken);
            coursePageEntity.setUserVO(userVO);
            return coursePageEntity;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }
}
