package com.university.universitymanagement.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import java.util.Properties;

@Configuration
@EnableJpaRepositories(
        basePackages = "com.university.universitymanagement.grade.repository",
        entityManagerFactoryRef = "gradeEntityManagerFactory",
        transactionManagerRef = "gradeTransactionManager"
)
public class GradeDbConfig {

    @Bean
    @ConfigurationProperties(prefix = "app.datasource.grade")
    public DataSource gradeDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean gradeEntityManagerFactory(
            @Qualifier("gradeDataSource") DataSource dataSource) {

        LocalContainerEntityManagerFactoryBean factory =
                new LocalContainerEntityManagerFactoryBean();

        factory.setDataSource(dataSource);
        factory.setPackagesToScan(
                "com.university.universitymanagement.grade.entity"
        );
        factory.setPersistenceUnitName("grade");

        factory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

        Properties properties = new Properties();
        properties.put("hibernate.hbm2ddl.auto", "update");
        properties.put("hibernate.dialect",
                "org.hibernate.dialect.PostgreSQLDialect");

        factory.setJpaProperties(properties);

        return factory;
    }

    @Bean
    public PlatformTransactionManager gradeTransactionManager(
            @Qualifier("gradeEntityManagerFactory")
            jakarta.persistence.EntityManagerFactory entityManagerFactory) {

        return new JpaTransactionManager(entityManagerFactory);
    }
}
