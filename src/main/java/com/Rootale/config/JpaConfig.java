package com.Rootale.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import jakarta.persistence.EntityManagerFactory;

@Configuration
@EnableJpaRepositories(
        basePackages = {
                "com.Rootale.member.repository",  // User, OAuthAccount 등
                "com.Rootale.fcm.repository",
                "com.Rootale.topic.repository"
        },
        transactionManagerRef = "jpaTransactionManager"
)
@EnableTransactionManagement
public class JpaConfig {

    @Primary  // ⭐ JPA를 기본 TransactionManager로 지정
    @Bean(name = "jpaTransactionManager")
    public PlatformTransactionManager jpaTransactionManager(EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}