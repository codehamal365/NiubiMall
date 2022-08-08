package com.gl.service.impl;

import com.gl.entity.PromotionAd;
import com.gl.mapper.PromotionAdMapper;
import com.gl.service.IPromotionAdService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * <p>
 * 广告表 服务实现类
 * </p>
 *
 * @author Long
 * @since 2021-12-21
 */
@CacheConfig(cacheNames = "promotion")
@Service
public class PromotionAdServiceImpl extends ServiceImpl<PromotionAdMapper, PromotionAd> implements IPromotionAdService {
    @Autowired
    private PromotionAdMapper promotionAdMapper;

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Cacheable(cacheNames = "PromotionAds")
    @Override
    public List<? extends Object> findAllPromotionAds() {
          return promotionAdMapper.selectAllPromotionAds();
    }

    @Cacheable(cacheNames = "PromotionAd",key = "T(String).valueOf(#id)")
    @Override
    public PromotionAd findPromotionAdById(Integer id) {
          return promotionAdMapper.selectPromotionAdById(id);
    }

    @Cacheable(cacheNames = "PromotionAdsBySid",key = "T(String).valueOf(#sid)")
    @Override
    public List<PromotionAd> findPromotionAdsBySid(int sid) {
        return promotionAdMapper.selectPromotionAdsBySid(sid);
    }
}
