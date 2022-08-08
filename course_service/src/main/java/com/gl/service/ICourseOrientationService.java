package com.gl.service;

import com.gl.entity.CourseOrientation;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Long
 * @since 2021-12-21
 */
public interface ICourseOrientationService extends IService<CourseOrientation> {
    List<CourseOrientation> findAllCourseOrientations();

    List<CourseOrientation> findCourseOrientationsByParentId(Integer parentId);
}
