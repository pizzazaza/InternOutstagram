package com.intern.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.core.io.FileSystemResource;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import com.intern.config.ServletContextConfig;

import java.io.IOException;

@Configuration
// 자동으로 webMvcConfigurationSupport 클래스가 자동으로 빈으로 등록된다. addResourceHandlers이용.
@EnableWebMvc
// dispatcherServlet단에서 사용되는 controller를 주입받는다. RootApplication에서 dao와 service를
// 주입받은 상태이므로 controller가 의존하는것들에 대해서 사용가능하다.
@ComponentScan(basePackages = {"com.intern.controller"})
public class ServletContextConfig extends WebMvcConfigurerAdapter {
	private static final Logger logger = LoggerFactory.getLogger(ServletContextConfig.class);
    @Value("${app.file.max.size}")
    private int maxFileSize;
    @Value("${app.file.tmp.dir}")
    private String tmpDir;
    
    @Bean
    public ViewResolver viewResolver() {
        // 논리적인 View 이름을 사용하여 템플릿 파일 장원을 사용하여 랜더링 되는 View 객체를 결정한다.
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        // JSTL을 사용하기 위해 사용.
        viewResolver.setViewClass(JstlView.class);
        viewResolver.setPrefix("/WEB-INF/views/");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }

    @Bean
    public MultipartResolver multipartResolver() throws IOException {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setMaxUploadSize(maxFileSize);
        multipartResolver.setUploadTempDir(new FileSystemResource(tmpDir));
        return multipartResolver;
    }
    //파일 송수신을 위한 Config, 파일의 최대 크기와 임시 저장공간 지정 
    
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
    }


}