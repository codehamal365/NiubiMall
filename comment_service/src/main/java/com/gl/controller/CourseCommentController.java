package com.gl.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.gl.entity.CourseComment;
import com.gl.entity.CourseCommentFavoriteRecord;
import com.gl.service.ICourseCommentFavoriteRecordService;
import com.gl.service.ICourseCommentService;
import com.gl.service.impl.CourseCommentFavoriteRecordServiceImpl;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 课程留言表 前端控制器
 * </p>
 *
 * @author Long
 * @since 2022-01-20
 */
@Api
@RestController
public class CourseCommentController {
    @Autowired
    private ICourseCommentService courseCommentService;
    @Autowired
    private ICourseCommentFavoriteRecordService courseCommentFavoriteRecordService;

    @GetMapping("/course/comment/getCourseCommentList/{courseid}/{offset}/{pageSize}")
    public List<CourseComment> getAllCourseComment(@PathVariable("courseid") Integer courseid,
                                                   @PathVariable("offset") Integer offset,
                                                   @PathVariable("pageSize") Integer pageSize){
          return courseCommentService.getCommentsByCourseId(courseid, offset, pageSize);
    }


    @GetMapping("/course/comment/saveCourseComment")
    public void saveComment(@RequestParam("courseid") Integer courseid,
                            @RequestParam("userid") Integer userid,
                            @RequestParam("username") String username,
                            @RequestParam("comment") String comment){
        CourseComment courseComment = new CourseComment();
        courseComment.setCourseId(courseid);
        courseComment.setUserId(userid);
        courseComment.setUserName(username);
        courseComment.setComment(comment);
        courseComment.setSectionId(0);
        courseComment.setLessonId(0);
        courseComment.setLikeCount(0);
        courseComment.setType(0);
        courseComment.setStatus(0);

        LocalDateTime localDateTime = LocalDateTime.now();
        courseComment.setUpdateTime(localDateTime);
        courseComment.setCreateTime(localDateTime);

        courseCommentService.save(courseComment);
        System.out.println(courseid);
        System.out.println(userid);
        System.out.println(username);
        System.out.println(comment);
    }





    @GetMapping("/course/comment/saveFavorite/{commentId}/{userId}")
    public ResponseEntity<String> likeComment(@PathVariable("commentId") Integer commentId,
                                              @PathVariable("userId") Integer userId){
        // 查询该用户是否点过赞
        QueryWrapper<CourseCommentFavoriteRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",userId);
        queryWrapper.eq("comment_id",commentId);
        CourseCommentFavoriteRecord zan = courseCommentFavoriteRecordService.getOne(queryWrapper);
        // 如果没有点赞
        if (zan == null){
            QueryWrapper<CourseComment> wrapper = new QueryWrapper<>();
            wrapper.eq("id",commentId);
            CourseComment comment = courseCommentService.getOne(wrapper);
            Integer likeCount = comment.getLikeCount();
            UpdateWrapper<CourseComment> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("id",commentId);
            CourseComment courseComment = new CourseComment();
            courseComment.setLikeCount(likeCount + 1);
            courseCommentService.update(courseComment,updateWrapper);
            // 添加赞
            CourseCommentFavoriteRecord courseCommentFavoriteRecord = new CourseCommentFavoriteRecord();
            courseCommentFavoriteRecord.setUserId(userId);
            courseCommentFavoriteRecord.setCommentId(commentId);
            courseCommentFavoriteRecordService.save(courseCommentFavoriteRecord);
            return ResponseEntity.ok("ok");
        }else {
            QueryWrapper<CourseComment> wrapper = new QueryWrapper<>();
            wrapper.eq("id",commentId);
            CourseComment comment = courseCommentService.getOne(wrapper);
            Integer likeCount = comment.getLikeCount();
            UpdateWrapper<CourseComment> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("id",commentId);
            CourseComment courseComment = new CourseComment();
            courseComment.setLikeCount(likeCount - 1);
            courseCommentService.update(courseComment,updateWrapper);
            QueryWrapper<CourseCommentFavoriteRecord> deleteWrapper = new QueryWrapper<>();
            deleteWrapper.eq("comment_id",commentId);
            deleteWrapper.eq("user_id",userId);
            courseCommentFavoriteRecordService.remove(deleteWrapper);
            return ResponseEntity.ok("cancle");
        }


    }

}
