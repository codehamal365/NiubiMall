package com.gl.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 课程留言表
 * </p>
 *
 * @author Long
 * @since 2022-01-20
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("course_comment")
@ApiModel(value = "CourseComment对象", description = "课程留言表")
public class CourseComment implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("课程id")
    private Integer courseId;

    @ApiModelProperty("章节id")
    private Integer sectionId;

    @ApiModelProperty("课时id")
    private Integer lessonId;

    @ApiModelProperty("用户id")
    private Integer userId;

    @ApiModelProperty("运营设置用户昵称")
    private String userName;

    @ApiModelProperty("父级评论id")
    private Integer parentId;

    @ApiModelProperty("是否置顶：0不置顶，1置顶")
    private Boolean isTop;

    @ApiModelProperty("评论")
    private String comment;

    @ApiModelProperty("点赞数")
    private Integer likeCount;

    @ApiModelProperty("是否回复留言：0普通留言，1回复留言")
    private Boolean isReply;

    @ApiModelProperty("留言类型：0用户留言，1讲师留言，2运营马甲 3讲师回复 4小编回复 5官方客服回复")
    private Integer type;

    @ApiModelProperty("留言状态：0待审核，1审核通过，2审核不通过，3已删除")
    private Integer status;

    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty("更新时间")
    private LocalDateTime updateTime;

    @ApiModelProperty("是否删除")
    private Boolean isDel;

    @ApiModelProperty("最后操作者id")
    private Integer lastOperator;

    @ApiModelProperty("是否发送了通知,1表示未发出，0表示已发出")
    private Boolean isNotify;

    @ApiModelProperty("标记归属")
    private Boolean markBelong;

    @ApiModelProperty("回复状态 0 未回复 1 已回复")
    private Boolean replied;

    @TableField(exist = false)
    private List<CourseCommentFavoriteRecord> favoriteRecords;

}
