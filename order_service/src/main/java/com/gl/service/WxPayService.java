package com.gl.service;

import com.gl.entity.Course;

import java.util.Map;

public interface WxPayService {
    Map<String,String> createNative(String orderNo, String remoteAddr, Course course);
}
