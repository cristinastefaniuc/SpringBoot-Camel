package com.demo.config;

import oracle.jms.AQjmsFactory;
import org.apache.camel.component.jms.JmsComponent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.connection.JmsTransactionManager;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
public class OracleAQQueueConnectionFactory{

    @Bean ConnectionFactory connectionFactory(DataSource dataSource) throws JMSException {
        return AQjmsFactory.getQueueConnectionFactory(dataSource);
    }

    @Bean
    public JmsTransactionManager jmsTransactionManager(final ConnectionFactory connectionFactory) {
        JmsTransactionManager jmsTransactionManager = new JmsTransactionManager();
        jmsTransactionManager.setConnectionFactory(connectionFactory);
        return jmsTransactionManager;
    }

    @Bean
    public JmsComponent jmsComponent(final ConnectionFactory connectionFactory, final JmsTransactionManager jmsTransactionManager) {
        return JmsComponent.jmsComponentTransacted(connectionFactory, jmsTransactionManager);
    }

    @Bean(name = "transactionManager")
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
