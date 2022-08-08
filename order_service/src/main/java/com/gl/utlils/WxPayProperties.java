package com.gl.utlils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
@ConfigurationProperties(prefix = "weixin.pay")
public class WxPayProperties {
    private String appId;
    private String partner;
    private String partnerKey;
    private String notifyUrl;
}
