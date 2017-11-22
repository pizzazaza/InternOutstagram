package ex.recommend.app;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class RecommendSessionFactory {
	public static SqlSessionFactory ssf;
     
    static{
         
        String resource = "ex/recommend/dao/mybatis-config.xml";
        InputStream inputStream = null;
         
        try {
            inputStream = Resources.getResourceAsStream(resource);
        } catch (IOException e) {
            e.printStackTrace();
        }
         
        ssf = new SqlSessionFactoryBuilder().build(inputStream);
 
    }
     
     
    public static SqlSessionFactory getSqlSessionFactory(){
        return ssf;
    }
}
/*
spring.datasource.driver-class-name=com.mysql.jdbc.Driver
spring.datasource.url=jdbc:mysql://10.113.156.122:3306/outstagram
spring.datasource.username=sejun2
spring.datasource.password=tpwns1
*/