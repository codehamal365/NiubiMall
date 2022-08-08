package com.gl.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

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
@TableName("course_orientation")
@ApiModel(value = "CourseOrientation对象", description = "")
public class CourseOrientation implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String orientationName;

    private Integer parentId;

    private CourseOrientation parent;


}
