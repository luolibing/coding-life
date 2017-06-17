package cn.tim.multirep.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

/**
 * Created by LuoLiBing on 15/10/31.
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "cn.tim.multirep.second",entityManagerFactoryRef = "entityManagerFactoryLocal",
        transactionManagerRef = "transactionManagerLocal")
public class EntityManageSecond {

    @Autowired
    private JpaProperties jpaProperties;

    @Autowired
    @Qualifier(value="secondDB")
    private DataSource localDataSource;

    @Bean(name = "entityManagerLocal")
    //@Primary
    public EntityManager entityManager(EntityManagerFactoryBuilder builder) {
        return localContainerEntityManagerFactoryBean(builder).getObject().createEntityManager();
    }

    @Bean(name = "entityManagerFactoryLocal")
    //@Primary
    public LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean(EntityManagerFactoryBuilder builder) {
        LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean
                = builder.dataSource(localDataSource)
                .properties(jpaProperties.getHibernateProperties(localDataSource))
                .packages("cn.tim.multirep.second")
                .persistenceUnit("localPersistenceUnit")
                .build();
        return localContainerEntityManagerFactoryBean;
    }
    @Bean(name = "transactionManagerLocal")
    //@Primary
    PlatformTransactionManager platformTransactionManager(EntityManagerFactoryBuilder builder){
        return new JpaTransactionManager(entityManager(builder).getEntityManagerFactory());
    }
}
