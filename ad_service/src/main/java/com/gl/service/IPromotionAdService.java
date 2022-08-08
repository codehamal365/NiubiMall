package com.gl.service;

import com.gl.entity.PromotionAd;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 广告表 服务类
 * </p>
 *
 * @author Long
 * @since 2021-12-21
 */
public interface IPromotionAdService extends IService<PromotionAd> {
    List<? extends Object> findAllPromotionAds();

    PromotionAd findPromotionAdById(Integer spaceId);

    List<PromotionAd> findPromotionAdsBySid(int sid);
}
