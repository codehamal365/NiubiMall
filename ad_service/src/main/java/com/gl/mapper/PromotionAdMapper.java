package com.gl.mapper;

import com.gl.entity.PromotionAd;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 广告表 Mapper 接口
 * </p>
 *
 * @author Long
 * @since 2021-12-21
 */
public interface PromotionAdMapper extends BaseMapper<PromotionAd> {
    List<PromotionAd> selectAllPromotionAds();


    PromotionAd selectPromotionAdById(Integer id);

    List<PromotionAd> selectPromotionAdsBySid(int sid);
}
