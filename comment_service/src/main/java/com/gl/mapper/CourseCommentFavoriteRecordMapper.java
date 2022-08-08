package com.gl.mapper;

import com.gl.entity.CourseCommentFavoriteRecord;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 课程留言点赞表 Mapper 接口
 * </p>
 *
 * @author Long
 * @since 2022-01-20
 */
public interface CourseCommentFavoriteRecordMapper extends BaseMapper<CourseCommentFavoriteRecord> {
       List<CourseCommentFavoriteRecord> selectFavoriteByCommentId(Integer commentId);
}
