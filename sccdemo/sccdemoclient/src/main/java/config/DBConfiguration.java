package config;

import bean.DBParam;
import com.alibaba.druid.pool.DruidDataSource;
import com.google.common.collect.Lists;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.List;

/**
 * @ClassName DBConfiguration
 * @Description TODO
 * @Author 逝风无言
 * @Data 2019/9/14 13:59
 * @Version 1.0
 **/
@Configuration
@ConditionalOnProperty(name="system.db-enable",havingValue="true")
@AutoConfigureAfter(ParamConfiguration.class)
public class DBConfiguration {

    @Autowired
    ParamConfiguration paramConfiguration;

    @Bean
    public DataSource dataSource(){
        DBParam dbParam = paramConfiguration.getDbParam();

        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(dbParam.getUrl());
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUsername(dbParam.getUsername());
        dataSource.setPassword(dbParam.getPassword());
        dataSource.setInitialSize(1);
        dataSource.setMaxActive(20);

    }

    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception{
        SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSource);

        List<Resource> resources = Lists.newArrayList();
        resources.addAll(Arrays.asList(
                new PathMatchingResourcePatternResolver().getResource("classpath:mapper/*.xml")
        ));

        sessionFactoryBean.setMapperLocations(resources.toArray(
                new Resource[resources.size()]
        ));
        return sessionFactoryBean.getObject();
    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory){
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
