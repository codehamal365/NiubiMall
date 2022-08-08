package com.gl.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@lombok.Data
@AllArgsConstructor
@NoArgsConstructor
public class Data {
    private Object data;
    private String state;
    private String message;
}
