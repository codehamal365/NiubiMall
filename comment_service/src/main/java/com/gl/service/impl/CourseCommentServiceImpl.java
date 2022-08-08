package com.gl.service.impl;

import com.gl.entity.CourseComment;
import com.gl.mapper.CourseCommentMapper;
import com.gl.service.ICourseCommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 课程留言表 服务实现类
 * </p>
 *
 * @author Long
 * @since 2022-01-20
 */
@Service
public class CourseCommentServiceImpl extends ServiceImpl<CourseCommentMapper, CourseComment> implements ICourseCommentService {
    @Autowired
    private CourseCommentMapper courseCommentMapper;

    @Override
    public List<CourseComment> getCommentsByCourseId(Integer courseid, Integer offset, Integer pageSize) {
        return courseCommentMapper.getCommentsByCourseId(courseid, offset, pageSize);
    }
}
