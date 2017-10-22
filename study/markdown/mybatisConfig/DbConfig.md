##DbConfig

'''java
	
	package com.test.config;

	import org.apache.commons.dbcp2.BasicDataSource;
	import 	org.apache.ibatis.session.SqlSessionFactory;
	import org.mybatis.spring.SqlSessionFactoryBean;
	import org.mybatis.spring.SqlSessionTemplate;
	import org.mybatis.spring.annotation.MapperScan;
	import org.springframework.beans.factory.annotation.Value;
	import org.springframework.context.annotation.Bean;
	import org.springframework.context.annotation.Configuration;
	import org.springframework.core.io.ClassPathResource;
	import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
	import org.springframework.jdbc.datasource.DataSourceTransactionManager;
	import org.springframework.transaction.PlatformTransactionManager;
	import org.springframework.transaction.annotation.EnableTransactionManagement;

	import javax.sql.DataSource;
	import java.io.IOException;
	

	@Configuration
	@EnableTransactionManagement
	@MapperScan("com.test.dao")
	public class DbConfig {
    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;
    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;

    @Bean
    public DataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;
    }

    @Bean
    public PlatformTransactionManager transctionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    public SqlSessionFactoryBean sqlSessionFactory(DataSource dataSource) throws IOException{
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        factoryBean.setConfigLocation(new ClassPathResource("mybatis/Configuration.xml"));
        factoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("mybatis/mappers/*.xml"));
        factoryBean.setTypeAliasesPackage("com.test.domain");
        return factoryBean;
    }


    @Bean
    public SqlSessionTemplate SqlSessionTemplate(SqlSessionFactory sqlSessionFactory) throws Exception
    {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
	}



