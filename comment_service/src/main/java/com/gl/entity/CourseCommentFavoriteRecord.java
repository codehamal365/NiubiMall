package com.gl.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 课程留言点赞表
 * </p>
 *
 * @author Long
 * @since 2022-01-20
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("course_comment_favorite_record")
@ApiModel(value = "CourseCommentFavoriteRecord对象", description = "课程留言点赞表")
public class CourseCommentFavoriteRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("用户评论点赞j记录ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("用户ID")
    private Integer userId;

    @ApiModelProperty("用户评论ID")
    private Integer commentId;

    @ApiModelProperty("是否删除，0：未删除（已赞），1：已删除（取消赞状态）")
    private Boolean isDel;

    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty("更新时间")
    private LocalDateTime updateTime;


}
