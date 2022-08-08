package com.gl.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;

@FeignClient("search-service")
public interface SearchFeignClient {
    @PostMapping("/course/init")
    public void  initCourse() throws IOException;
}
