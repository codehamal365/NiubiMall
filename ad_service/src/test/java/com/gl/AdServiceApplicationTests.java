package com.gl;

import com.gl.entity.PromotionAd;
import com.gl.mapper.PromotionAdMapper;
import com.gl.service.IPromotionAdService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class AdServiceApplicationTests {
    @Autowired
    private IPromotionAdService promotionAdService;

    @Autowired
    private PromotionAdMapper promotionAdMapper;


    @Test
    void contextLoads() {
        List<PromotionAd> allPromotionAds = (List<PromotionAd>) promotionAdService.findAllPromotionAds();
        System.out.println(allPromotionAds);
    }

    @Test
    void contextLoads2() {
        List<PromotionAd> promotionAds = promotionAdMapper.selectAllPromotionAds();
        System.out.println(promotionAds);
    }

    @Test
    void contextLoads3() {
        PromotionAd promotionAdById = promotionAdService.findPromotionAdById(1074);
        System.out.println(promotionAdById);
    }

}
