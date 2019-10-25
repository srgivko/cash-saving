package by.sivko.cashsaving.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Objects;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@ComponentScan("by.sivko.cashsaving.configs.propertyreaders")
public class PersistenceConfig {

    @Value("${hibernate.hbm2ddl.auto}")
    private String hibernate_hbm2ddl_auto;

    @Value("${hibernate.dialect}")
    private String hibernate_dialect;

    @Value("${hibernate.show_sql}")
    private String hibernate_show_sql;

    @Value("${hibernate.format_sql}")
    private String hibernate_format_sql;

    @Value("${spring.datasource.url}")
    private String datasource_url;

    @Value("${spring.datasource.driverClassName}")
    private String datasource_driverClass;

    @Value("${spring.datasource.username}")
    private String datasource_username;

    @Value("${spring.datasource.password}")
    private String datasource_password;

    private Properties getHibernateProperties() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.hbm2ddl.auto", hibernate_hbm2ddl_auto);
        properties.setProperty("hibernate.dialect", hibernate_dialect);
        properties.setProperty("hibernate.show_sql", hibernate_show_sql);
        properties.setProperty("hibernate.format_sql", hibernate_format_sql);
        return properties;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        localContainerEntityManagerFactoryBean.setDataSource(dataSource());
        localContainerEntityManagerFactoryBean.setPackagesToScan("by.sivko.cashsaving.models");
        localContainerEntityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        localContainerEntityManagerFactoryBean.setJpaProperties(getHibernateProperties());
        return localContainerEntityManagerFactoryBean;
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(Objects.requireNonNull(datasource_driverClass));
        dataSource.setUrl(datasource_url);
        dataSource.setUsername(datasource_username);
        dataSource.setPassword(datasource_password);
        return dataSource;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
        return transactionManager;
    }

}
