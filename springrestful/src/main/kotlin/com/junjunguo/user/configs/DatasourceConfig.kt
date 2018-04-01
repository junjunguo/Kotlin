package com.junjunguo.user.configs

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import org.springframework.transaction.annotation.EnableTransactionManagement


@Configuration
@EnableTransactionManagement
class DatasourceConfig {

    @Autowired
    private lateinit var env: Environment

//    @Bean
//    fun dataSource(): DataSource {
//        val dataSource = DriverManagerDataSource()
//        dataSource.setDriverClassName(env.getProperty("jdbc.driverClassName"))
//        dataSource.url = env.getProperty("jdbc.url")
//        dataSource.username = env.getProperty("jdbc.user")
//        dataSource.password = env.getProperty("jdbc.pass")
//        return dataSource
//    }
//
//    @Bean
//    @Throws(PropertyVetoException::class)
//    fun datasource(): DataSource {
//        val builder = EmbeddedDatabaseBuilder()
//        val dataSource = builder.build()
//
//        return builder.build()
//    }
//
//    @Bean
//    @Throws(PropertyVetoException::class)
//    fun entityManagerFactory(@Qualifier("datasource") ds: DataSource): LocalContainerEntityManagerFactoryBean {
//        val entityManagerFactory = LocalContainerEntityManagerFactoryBean()
//        entityManagerFactory.dataSource = ds
//        entityManagerFactory.setPackagesToScan(*arrayOf("com.nouhoun.springboot.jwt.integration.domain"))
//        val jpaVendorAdapter = HibernateJpaVendorAdapter()
//        entityManagerFactory.jpaVendorAdapter = jpaVendorAdapter
//        return entityManagerFactory
//    }
//
//    @Bean
//    fun transactionManager(entityManagerFactory: EntityManagerFactory): PlatformTransactionManager {
//        val transactionManager = JpaTransactionManager()
//        transactionManager.entityManagerFactory = entityManagerFactory
//        return transactionManager
//    }
}