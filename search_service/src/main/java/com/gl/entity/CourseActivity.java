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
 * 活动课程表
 * </p>
 *
 * @author Long
 * @since 2021-12-21
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("course_activity")
@ApiModel(value = "CourseActivity对象", description = "活动课程表")
public class CourseActivity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("课程ID")
    private Integer courseId;

//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
//    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @ApiModelProperty("活动开始时间")
    private Timestamp beginTime;

//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
//    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @ApiModelProperty("活动结束时间")
    private Timestamp endTime;

    @ApiModelProperty("活动价格")
    private Double amount;

    @ApiModelProperty("库存值")
    private Integer stock;

    @ApiModelProperty("状态 0未上架 10已上架")
    private Integer status;

    @ApiModelProperty("逻辑删除 0未删除 1删除")
    private Boolean isDel;

    @ApiModelProperty("备注")
    private String remark;

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


}
