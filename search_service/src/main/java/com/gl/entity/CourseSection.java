package com.gl.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 课程章节表
 * </p>
 *
 * @author Long
 * @since 2021-12-21
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("course_section")
@ApiModel(value = "CourseSection对象", description = "课程章节表")
public class CourseSection implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("课程id")
    private Integer courseId;

    @ApiModelProperty("章节名")
    private String sectionName;

    @ApiModelProperty("章节描述")
    private String description;

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

    @ApiModelProperty("排序字段")
    private Integer orderNum;

    @ApiModelProperty("状态，0:隐藏；1：待更新；2：已发布")
    private Integer status;

    // 课程小结
    private List<CourseLesson> courseLessons;


}
