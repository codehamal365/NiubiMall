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

/**
 * <p>
 * 课节视频表
 * </p>
 *
 * @author Long
 * @since 2021-12-21
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("course_media")
@ApiModel(value = "CourseMedia对象", description = "课节视频表")
public class CourseMedia implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("课程媒体主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("课程Id")
    private Integer courseId;

    @ApiModelProperty("章ID")
    private Integer sectionId;

    @ApiModelProperty("课时ID")
    private Integer lessonId;

    @ApiModelProperty("封面图URL")
    private String coverImageUrl;

    @ApiModelProperty("时长（06:02）")
    private String duration;

    @ApiModelProperty("媒体资源文件对应的EDK")
    private String fileEdk;

    @ApiModelProperty("文件大小MB")
    private Double fileSize;

    @ApiModelProperty("文件名称")
    private String fileName;

    @ApiModelProperty("媒体资源文件对应的DK")
    private String fileDk;

//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
//    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @ApiModelProperty("创建时间")
    private Timestamp createTime;

//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
//    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @ApiModelProperty("更新时间")
    private Timestamp updateTime;

    @ApiModelProperty("是否删除，0未删除，1删除")
    private Boolean isDel;

    @ApiModelProperty("时长，秒数（主要用于音频在H5控件中使用）")
    private Integer durationNum;

    @ApiModelProperty("媒体资源文件ID")
    private String fileId;


}
