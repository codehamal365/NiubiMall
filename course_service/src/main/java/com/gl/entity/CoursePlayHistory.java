package com.gl.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 
 * </p>
 *
 * @author Long
 * @since 2021-12-21
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("course_play_history")
@ApiModel(value = "CoursePlayHistory对象", description = "")
public class CoursePlayHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("用户id")
    private Integer userId;

    @ApiModelProperty("课程id")
    private Integer courseId;

    @ApiModelProperty("章节id")
    private Integer sectionId;

    @ApiModelProperty("课时id")
    private Integer lessonId;

    @ApiModelProperty("历史播放节点(s)")
    private Integer historyNode;

    @ApiModelProperty("最高历史播放节点")
    private Integer historyHighestNode;

//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
//    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @ApiModelProperty("记录创建时间")
    private Timestamp createTime;

//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
//    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @ApiModelProperty("更新时间")
    private Timestamp updateTime;

    @ApiModelProperty("是否删除")
    private Boolean isDel;


}
