package com.ramannada.springdemo.config;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.sql.DataSource;


@Configuration
@PropertySource({"classpath:config.properties"})
@EnableWebMvc
@EnableTransactionManagement
@ComponentScan(basePackages = "com.ramannada.springdemo")
public class WebConfig extends WebMvcConfigurerAdapter {
    //    setting jdbc
    @Value("${jdbc.driver}")
    private String jdbcDriver;
    @Value("${jdbc.url}")
    private String jdbcUrl;
    @Value("${jdbc.username}")
    private String jdbcUsername;
    @Value("${jdbc.password}")
    private String jdbcPassword;

//    setting hibernate
    @Value("${hibernate.show_sql}")
    private boolean hibernateSql;
    @Value("${hibernate.hbm2ddl.auto}")
    private String hibernateHbm2Ddl;

//    C390 properties
    @Value("${hibernate.c3p0.min_size}")
    private int c3p0MinSize;
    @Value("${hibernate.c3p0.max_size}")
    private int c3p0MaxSize;
    @Value("${hibernate.c3p0.acquire_increment}")
    private int c3p0AcquireIncrement;
    @Value("${hibernate.c3p0.timeout}")
    private int c3p0TimeOut;
    @Value("${hibernate.c3p0.max_statements}")
    private int c3p0MaxStatement;

//    db setting
    @Bean
    public DataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(jdbcDriver);
        dataSource.setUrl(jdbcUrl);
        dataSource.setUsername(jdbcUsername);
        dataSource.setPassword(jdbcPassword);
        dataSource.setDefaultAutoCommit(false);
        dataSource.setMaxWaitMillis(6000);
        dataSource.setRemoveAbandonedOnBorrow(true);
        dataSource.setLogAbandoned(true);

        return dataSource;
    }

//    init jdbc template for connection to db
    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer placeholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

//    transaction
    @Bean
    public DataSourceTransactionManager transactionManager() {
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
        transactionManager.setDataSource(dataSource());
        return transactionManager;
    }

//    support fileupload
    @Bean
    public MultipartResolver multipartResolver() {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setMaxUploadSize(1000000);
        return multipartResolver;
    }

}
