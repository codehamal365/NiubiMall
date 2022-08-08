package com.gl.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageEntity<T> {
    public static final int PAGE_SIZE = 5;
    private List<T> data;
    // 当前页
    private Integer current;
    // 总数
    private Integer total;
    // 每页多少条数据
    private Integer pageSize;
    private UserVO userVO;


}
