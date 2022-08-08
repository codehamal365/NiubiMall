package com.gl.mapper;

import com.gl.entity.CourseOrientation;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Long
 * @since 2021-12-21
 */
public interface CourseOrientationMapper extends BaseMapper<CourseOrientation> {
    List<CourseOrientation> selectAllCourseOrientations();

    List<CourseOrientation> selectCourseOrientationsByParentId(Integer parentId);
}
