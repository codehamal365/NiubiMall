package com.gl.service;

import com.gl.entity.CourseComment;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 课程留言表 服务类
 * </p>
 *
 * @author Long
 * @since 2022-01-20
 */
public interface ICourseCommentService extends IService<CourseComment> {
    List<CourseComment> getCommentsByCourseId(@Param("courseid") Integer courseid,
                                              @Param("offset") Integer offset,
                                              @Param("pageSize") Integer pageSize);

}
