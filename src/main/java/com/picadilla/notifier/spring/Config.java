package com.picadilla.notifier.spring;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.EnvironmentStringPBEConfig;
import org.jasypt.spring31.properties.EncryptablePropertyPlaceholderConfigurer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@ComponentScan(basePackages = {"com.picadilla.notifier.domain", "com.picadilla.notifier.service"})
@EnableTransactionManagement
public class Config {

    @Value("${database.url}")
    private String dbUrl;
    @Value("${database.username}")
    private String dbUsermame;
    @Value("${database.password}")
    private String dbPassword;

    @Value("${mail.host}")
    private String mailHost;
    @Value("${mail.port}")
    private int mailPort;
    @Value("${mail.address}")
    private String mailAddress;
    @Value("${mail.password}")
    private String mailPassword;
    @Value("${mail.signature}")
    private String mailSignature;

    private static final String ENCRYPTION_KEY = "#G4m3D3v";
    private static final String ENCRYPTION_ALGORITHM = "PBEWithMD5AndDES";

    @Bean
    public static EnvironmentStringPBEConfig environmentVariablesConfiguration() {
        EnvironmentStringPBEConfig pbeConfig = new EnvironmentStringPBEConfig();
        pbeConfig.setAlgorithm(ENCRYPTION_ALGORITHM);
        pbeConfig.setPassword(ENCRYPTION_KEY);
        return pbeConfig;
    }

    @Bean
    public static StandardPBEStringEncryptor configurationEncryptor() {
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setConfig(environmentVariablesConfiguration());
        return encryptor;
    }

    @Bean
    public static EncryptablePropertyPlaceholderConfigurer propertyConfigurer() {
        EncryptablePropertyPlaceholderConfigurer configurer = new EncryptablePropertyPlaceholderConfigurer(configurationEncryptor());
        configurer.setLocations(new ClassPathResource("/application.properties"), new ClassPathResource("/message.properties"));
        return configurer;
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(emf);
        return transactionManager;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean emfb = new LocalContainerEntityManagerFactoryBean();
        emfb.setDataSource(dataSource());
        emfb.setPackagesToScan("com.picadilla.notifier.domain");
        emfb.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        emfb.setJpaDialect(new HibernateJpaDialect());
        return emfb;
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl(dbUrl);
        dataSource.setUsername(dbUsermame);
        dataSource.setPassword(dbPassword);
        return dataSource;
    }

    @Bean
    public MailSender mailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(mailHost);
        mailSender.setPort(mailPort);
        mailSender.setUsername(mailAddress);
        mailSender.setPassword(mailPassword);
        mailSender.setJavaMailProperties(emailProperties());
        return mailSender;
    }

    private Properties emailProperties() {
        Properties emailProperties = new Properties();
        emailProperties.put("mail.smtp.starttls.enable", "true");
        return emailProperties;
    }

    @Bean
    public SimpleMailMessage templateMessage() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("\"" + mailSignature + "\" <" + mailAddress + ">");
        return message;
    }

}
