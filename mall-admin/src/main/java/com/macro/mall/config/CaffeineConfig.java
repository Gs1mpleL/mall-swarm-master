package com.macro.mall.config;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.macro.mall.model.PmsProduct;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class CaffeineConfig {
    @Bean
    public Cache<Long, List<PmsProduct>> productCaffeine() {
        return Caffeine.newBuilder()
                .initialCapacity(100)
                .maximumSize(10_000).build();
    }
}
