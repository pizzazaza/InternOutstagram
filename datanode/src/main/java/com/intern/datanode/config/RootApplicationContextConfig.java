package com.intern.datanode.config;

import org.springframework.context.annotation.*;

//최상위에 Configuration
@Configuration
@ComponentScan(basePackages = {"com.intern.datanode.dao", "com.intern.datanode.service"})
@PropertySources({
      @PropertySource("classpath:/database.properties"),
      @PropertySource("classpath:/application.properties")
})
@Import({DbConfig.class})
public class RootApplicationContextConfig {
}