package cn.tim.multirep.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManager;
import javax.persistence.SharedCacheMode;
import javax.sql.DataSource;

/**
 * Created by LuoLiBing on 15/10/29.
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "cn.tim.multirep.first", entityManagerFactoryRef = "entityManagerFactoryCloud"
        , transactionManagerRef = "transactionManagerCloud")
public class EntityManageFirst {

    @Autowired
    private JpaProperties jpaProperties;

    @Autowired
    @Qualifier(value="firstDB")
    private DataSource cloudDataSource;

    @Bean(name = "entityManagerCloud")
    @Primary
    public EntityManager entityManager(org.springframework.boot.autoconfigure.orm.jpa.EntityManagerFactoryBuilder builder) {
        return entityManagerCloud(builder).getObject().createEntityManager();
    }

    @Bean(name = "entityManagerFactoryCloud")
    @Primary
    public LocalContainerEntityManagerFactoryBean entityManagerCloud(org.springframework.boot.autoconfigure.orm.jpa.EntityManagerFactoryBuilder builder) {
        LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean =  builder.dataSource(cloudDataSource)
                .properties(jpaProperties.getHibernateProperties(cloudDataSource))
                .packages("cn.tim.multirep.first")
                .persistenceUnit("cloudPersistenceUnit")
                .build();
        localContainerEntityManagerFactoryBean.setSharedCacheMode(SharedCacheMode.ALL);
        return localContainerEntityManagerFactoryBean;
    }

    @Bean(name = "transactionManagerCloud")
    @Primary
    PlatformTransactionManager transactionManagerCloud(org.springframework.boot.autoconfigure.orm.jpa.EntityManagerFactoryBuilder builder) {
        return new JpaTransactionManager(entityManagerCloud(builder).getObject());
    }
}
