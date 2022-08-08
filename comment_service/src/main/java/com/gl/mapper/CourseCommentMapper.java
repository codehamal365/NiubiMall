package com.gl.mapper;

import com.gl.entity.CourseComment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 课程留言表 Mapper 接口
 * </p>
 *
 * @author Long
 * @since 2022-01-20
 */
public interface CourseCommentMapper extends BaseMapper<CourseComment> {
    List<CourseComment> getCommentsByCourseId(@Param("courseid") Integer courseid,
                                              @Param("offset") Integer offset,
                                              @Param("pageSize") Integer pageSize);
}
