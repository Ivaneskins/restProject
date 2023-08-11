package com.ivaneskins.spring.rest.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.sql.DataSource;
import java.util.Properties;


@Configuration
@ComponentScan(basePackages = "com.ivaneskins.spring.rest")
@EnableWebMvc
@EnableTransactionManagement
public class MyConfig {


//    @Bean
//    public DataSource dataSource() {
//        ComboPooledDataSource dataSource = new ComboPooledDataSource();
//        try {
//            dataSource.setDriverClass("com.mysql.cj.jdbc.Driver");
//            dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/users?serverTimezone=Europe/Minsk&useSSL=false");
//            dataSource.setUser("root");
//            dataSource.setPassword("root");
//        } catch (PropertyVetoException e) {
//            e.printStackTrace();
//        }
//        return dataSource;
//    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource;
        dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/users?serverTimezone=Europe/Minsk&useSSL=false");
        dataSource.setUsername("root");
        dataSource.setPassword("root");

        return dataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(){
        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setDataSource(dataSource());
        factoryBean.setPersistenceUnitName("TDPersistenceUnit");
        HibernateJpaVendorAdapter adapter =  new HibernateJpaVendorAdapter();
        adapter.setShowSql(true);
        adapter.setGenerateDdl(false);
        adapter.setDatabasePlatform("org.hibernate.dialect.H2Dialect");
        adapter.setGenerateDdl(true);
        factoryBean.setJpaVendorAdapter(adapter);
        factoryBean.setPackagesToScan("com.ivaneskins.spring.rest.model");
        factoryBean.setJpaProperties(additionalProperties());
        return factoryBean;
    }

    @Bean
    public PlatformTransactionManager transactionManager(){
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(
                entityManagerFactory().getObject() );

        return transactionManager;
    }
    Properties additionalProperties() {
        return new Properties() {
            {  // Hibernate Specific:
                setProperty("hibernate.hbm2ddl.auto", "update");
                setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
            }
        };
    }

}
