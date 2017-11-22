package com.recommend.config;

import com.recommend.aop.LoggingAdvice;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy
public class AspectJAutoProxyConfig {
    @Bean
    public LoggingAdvice loggingAspect() {
        return new LoggingAdvice();
    }
}