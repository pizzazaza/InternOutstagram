package com.intern.datanode.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import com.intern.datanode.aop.LoggingAdvice;

@Configuration
@EnableAspectJAutoProxy
public class AspectJAutoProxyConfig {
    @Bean
    public LoggingAdvice loggingAspect() {
        return new LoggingAdvice();
    }
}