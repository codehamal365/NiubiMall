package com.gl.service.impl;

import com.gl.entity.CourseOrientation;
import com.gl.mapper.CourseOrientationMapper;
import com.gl.service.ICourseOrientationService;
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

@CacheConfig(cacheNames = "courseOrientation")
@Service
public class CourseOrientationServiceImpl extends ServiceImpl<CourseOrientationMapper, CourseOrientation> implements ICourseOrientationService {
    @Autowired
    private CourseOrientationMapper courseOrientationMapper;

    @Cacheable("courseOrientations")
    @Override
    public List<CourseOrientation> findAllCourseOrientations() {
        return courseOrientationMapper.selectAllCourseOrientations();
    }

    @Cacheable(value = "courseOrientationsByParentId",key = "T(String).valueOf(#parentId)")
    @Override
    public List<CourseOrientation> findCourseOrientationsByParentId(Integer parentId) {
        return courseOrientationMapper.selectCourseOrientationsByParentId(parentId);
    }
}
