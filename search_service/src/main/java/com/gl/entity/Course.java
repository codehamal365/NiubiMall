package com.gl.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
 * 课程
 * </p>
 *
 * @author Long
 * @since 2021-12-21
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "Course对象", description = "课程")
public class Course implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("课程名")
    private String courseName;

    @ApiModelProperty("课程一句话简介")
    private String brief;

    @ApiModelProperty("原价")
    private Double price;

    @ApiModelProperty("原价标签")
    private String priceTag;

    @ApiModelProperty("优惠价")
    private Double discounts;

    @ApiModelProperty("优惠标签")
    private String discountsTag;

    @ApiModelProperty("描述markdown")
    private String courseDescriptionMarkDown;

    @ApiModelProperty("课程描述")
    private String courseDescription;

    @ApiModelProperty("课程展示图")
    private String courseImgUrl;

    @ApiModelProperty("是否新品")
    private Boolean isNew;

    @ApiModelProperty("广告语")
    private String isNewDes;

    @ApiModelProperty("最后操作者")
    private Integer lastOperatorId;

//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
//    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @ApiModelProperty("自动上架时间")
    private Timestamp autoOnlineTime;

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

    @ApiModelProperty("总时长(分钟)")
    private Integer totalDuration;

    @ApiModelProperty("课程状态，0-草稿，1-上架")
    private Integer status;

    @ApiModelProperty("课程排序，用于后台保存草稿时用到")
    private Integer sortNum;

    @ApiModelProperty("课程预览第一个字段")
    private String previewFirstField;

    @ApiModelProperty("课程预览第二个字段")
    private String previewSecondField;

    @ApiModelProperty("销量")
    private Integer sales;

    private Integer typeId;

    private Integer orientationId;

    private Integer teacherId;

    // 课程方向
    private CourseOrientation courseOrientation;
    //课程类型
    private CourseType courseType;
    //讲师
    private Teacher teacher;
    // 课程活动
    private List<CourseActivity> courseActivities;
    // 课程章节
    private List<CourseSection> courseSections;



}
