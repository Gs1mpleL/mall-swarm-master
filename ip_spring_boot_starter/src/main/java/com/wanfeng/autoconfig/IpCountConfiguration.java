package com.wanfeng.autoconfig;

import com.wanfeng.interceptor.IpInterceptor;
import com.wanfeng.interceptor.WebConfig;
import com.wanfeng.properties.IpProperties;
import com.wanfeng.service.IpCountService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@Import({IpProperties.class, WebConfig.class})
public class IpCountConfiguration {
    @Bean
    public IpCountService ipCountService() {
        return new IpCountService();
    }
}
