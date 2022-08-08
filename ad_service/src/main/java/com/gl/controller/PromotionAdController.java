package com.gl.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gl.entity.PromotionAd;
import com.gl.service.IPromotionAdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 广告表 前端控制器
 * </p>
 *
 * @author Long
 * @since 2021-12-21
 */
@RestController
public class PromotionAdController {
    @Autowired
    private IPromotionAdService promotionAdService;

    @GetMapping("/ad/getAdsBySpaceId/{spaceId}")
    public ResponseEntity<List<PromotionAd>> selectAllPromotionAds(@PathVariable("spaceId") Integer spaceId){
//        List<PromotionAd> allPromotionAds = promotionAdService.list(new QueryWrapper<PromotionAd>().lambda().eq(PromotionAd::getSpaceId,spaceId));
        List<PromotionAd> promotionAds = promotionAdService.findPromotionAdsBySid(spaceId);
        return ResponseEntity.ok(promotionAds);
    }

    @GetMapping("/PromotionAd/{id}")
    public ResponseEntity<PromotionAd> selectPromotionAdById(@PathVariable("id") Integer id){
        PromotionAd promotionAdById = promotionAdService.findPromotionAdById(id);
        System.out.println(promotionAdById);
        return ResponseEntity.ok(promotionAdById);

    }


    @GetMapping("/PromotionAds")
    public ResponseEntity<List<PromotionAd>> selectAll(){
        List<PromotionAd> allPromotionAds = (List<PromotionAd>) promotionAdService.findAllPromotionAds();
        System.out.println(allPromotionAds);
        return ResponseEntity.ok(allPromotionAds);

    }
}
