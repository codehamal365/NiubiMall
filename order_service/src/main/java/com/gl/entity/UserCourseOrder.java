package com.gl.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCourseOrder {
    private Long id;
    private Integer userId;
    private Integer courseId;
    private Integer status;
    private Timestamp createTime;
    private Timestamp updateTime;
    private Integer isDel;
    private String tradeNo;
    private Integer payTypeId;
    private Integer price;
}
