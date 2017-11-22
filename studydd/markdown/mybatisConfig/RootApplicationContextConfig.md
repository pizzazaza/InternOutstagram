##RootApplicationContextConfig

'''java

	package com.test.config;

	import org.springframework.context.annotation.*;


	@Configuration
	@ComponentScan(basePackages = {"com.test.dao", "com.test.service"})
	@PropertySources({
        @PropertySource("classpath:/database.properties"),
        @PropertySource("classpath:/application.properties")
	})
	@Import({DbConfig.class})
	public class RootApplicationContextConfig {
	}


'''

