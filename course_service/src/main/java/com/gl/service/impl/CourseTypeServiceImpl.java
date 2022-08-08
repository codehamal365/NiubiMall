package com.gl.service.impl;

import com.gl.entity.CourseType;
import com.gl.mapper.CourseTypeMapper;
import com.gl.service.ICourseTypeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Long
 * @since 2021-12-21
 */
@CacheConfig(cacheNames = "courseType")
@Service
public class CourseTypeServiceImpl extends ServiceImpl<CourseTypeMapper, CourseType> implements ICourseTypeService {
    @Autowired
    private CourseTypeMapper courseTypeMapper;

    @Cacheable(value = "courseTypes")
    @Override
    public List<CourseType> findAllCourseTypes() {
        return courseTypeMapper.selectAllCourseTypes();
    }
}
