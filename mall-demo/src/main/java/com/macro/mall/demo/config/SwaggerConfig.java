package com.macro.mall.demo.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import com.macro.mall.common.config.BaseSwaggerConfig;
import com.macro.mall.common.domain.SwaggerProperties;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger API文档相关配置
 * Created by macro on 2019/4/8.
 */
@Configuration
@EnableSwagger2
@EnableKnife4j
public class SwaggerConfig extends BaseSwaggerConfig {

    @Override
    public SwaggerProperties swaggerProperties() {
        return SwaggerProperties.builder()
                .apiBasePackage("com.macro.mall.demo.controller")
                .title("mall-demo系统")
                .description("SpringCloud版本中的一些示例")
                .contactName("macro")
                .version("1.0")
                .enableSecurity(true)
                .build();
    }

}
