package com.naver.intern.config;

import org.springframework.context.annotation.*;


@Configuration
@ComponentScan(basePackages = {"com.naver.intern.dao", "com.naver.intern.service"})
@PropertySources({
        @PropertySource("classpath:/database.properties"),
        @PropertySource("classpath:/application.properties")
})
@Import({DbConfig.class})
public class RootApplicationContextConfig {
}
