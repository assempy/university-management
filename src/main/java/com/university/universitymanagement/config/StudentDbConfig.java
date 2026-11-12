package com.university.universitymanagement.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import java.util.Properties;


@Configuration
@EnableJpaRepositories(
        basePackages = "com.university.universitymanagement.student.repository",
        entityManagerFactoryRef = "studentEntityManagerFactory",
        transactionManagerRef = "studentTransactionManager"
)
public class StudentDbConfig {

    @Primary
    @Bean
    @ConfigurationProperties(prefix = "app.datasource.student")
    public DataSource studentDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Primary
    @Bean
    public LocalContainerEntityManagerFactoryBean studentEntityManagerFactory(
            @Qualifier("studentDataSource") DataSource dataSource) {

        LocalContainerEntityManagerFactoryBean factory =
                new LocalContainerEntityManagerFactoryBean();

        factory.setDataSource(dataSource);
        factory.setPackagesToScan(
                "com.university.universitymanagement.student.entity"
        );
        factory.setPersistenceUnitName("student");

        factory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

        Properties properties = new Properties();
        properties.put("hibernate.hbm2ddl.auto", "update");
        properties.put("hibernate.dialect",
                "org.hibernate.dialect.PostgreSQLDialect");

        factory.setJpaProperties(properties);

        return factory;
    }

    @Primary
    @Bean
    public PlatformTransactionManager studentTransactionManager(
            @Qualifier("studentEntityManagerFactory")
            jakarta.persistence.EntityManagerFactory entityManagerFactory) {

        return new JpaTransactionManager(entityManagerFactory);
    }
}
