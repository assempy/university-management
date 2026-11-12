
package com.university.universitymanagement.config;

import javax.sql.DataSource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Properties;

@Configuration
@EnableJpaRepositories(
        basePackages = "com.university.universitymanagement.course.repository",
        entityManagerFactoryRef = "courseEntityManagerFactory",
        transactionManagerRef = "courseTransactionManager"
)
public class CourseDbConfig {

    @Bean
    @ConfigurationProperties(prefix = "app.datasource.course")
    public DataSource courseDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean courseEntityManagerFactory(
            @Qualifier("courseDataSource") DataSource dataSource) {

        LocalContainerEntityManagerFactoryBean factory =
                new LocalContainerEntityManagerFactoryBean();

        factory.setDataSource(dataSource);
        factory.setPackagesToScan(
                "com.university.universitymanagement.course.entity"
        );
        factory.setPersistenceUnitName("course");

        factory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

        Properties properties = new Properties();
        properties.put("hibernate.hbm2ddl.auto", "update");
        properties.put("hibernate.dialect",
                "org.hibernate.dialect.PostgreSQLDialect");

        factory.setJpaProperties(properties);

        return factory;
    }

    @Bean
    public PlatformTransactionManager courseTransactionManager(
            @Qualifier("courseEntityManagerFactory")
            jakarta.persistence.EntityManagerFactory entityManagerFactory) {

        return new JpaTransactionManager(entityManagerFactory);
    }
}
