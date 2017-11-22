package com.recommend.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.*;

//최상위에 Configuration
@Configuration
@ComponentScan(basePackages = {"com.recommend.dao", "com.recommend.service"})
@PropertySources({
      @PropertySource("classpath:/database.properties"),
      @PropertySource("classpath:/application.properties")
})
@Import({DbConfig.class})
public class RootApplicationContextConfig {
	private static final Logger logger = LoggerFactory.getLogger(RootApplicationContextConfig.class);
}