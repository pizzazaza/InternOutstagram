package com.intern.outstagram.config;

import java.io.IOException;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariDataSource;

@Configuration
@EnableTransactionManagement
@MapperScan("com.intern.outstagram.dao")
public class DbConfig {
	private static final Logger logger = LoggerFactory.getLogger(DbConfig.class);
	
	@Value("${spring.datasource.driver-class-name}")
	private String driverClassName;
	@Value("${spring.datasource.username}")
	private String username;
	@Value("${spring.datasource.url}")
	private String url;
	@Value("${spring.datasource.password}")
	private String password;
	@Value("${spring.datasource.max-pool-size}")
	private int maxPoolSize;
	@Value("${spring.datasource.minimum-idle}")
	private int minimumIdle;

	
	@Bean
	public DataSource dataSource() {
		HikariDataSource dataSource = new HikariDataSource();
		dataSource.setDriverClassName(driverClassName);
		dataSource.setMinimumIdle(minimumIdle);
		dataSource.setMaximumPoolSize(maxPoolSize);
		dataSource.setJdbcUrl(url);
		dataSource.setUsername(username);
		dataSource.setPassword(password);
		
		return dataSource;
	}
	/* 
	 * HikariDataSource를 생성 및 설정 
	 * Hikari Connection pool 생성
	 * HikariCP가 빠른 속도를 제공한다고 하여 선택
	 * BoneCP, Tomcat 보다 2배 이상의 속도 제공
	 */

	@Bean
	public PlatformTransactionManager transctionManager(DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}
	
	
	@Bean
    public SqlSessionFactoryBean sqlSessionFactory() throws IOException {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource());
        factoryBean.setConfigLocation(new ClassPathResource("mybatis/configuration.xml"));
        factoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("mybatis/mappers/*.xml"));
        factoryBean.setTypeAliasesPackage("com.intern.outstagram.domain");
        return factoryBean;
    }
	/*
	 * mybatis 설정
	 * mybatis/mappers 폴더에서 mapper.xml을 com.intern.datanode.dao 에 있는 mapper interface에 주입해준다. 
	 * configuration.xml에는 여러 자료구조를 aliase
	 * com.intern.datanode.domain에 있는 data 클래스를 aliase
	 */
	
	@Bean
	public SqlSessionTemplate sqlSession(SqlSessionFactory sqlSessionFactory) {
		return new SqlSessionTemplate(sqlSessionFactory);
	}
	
}