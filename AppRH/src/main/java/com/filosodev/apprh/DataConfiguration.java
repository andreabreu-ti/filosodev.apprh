package com.filosodev.apprh;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

@Configuration
public class DataConfiguration {

	@Bean
	public DataSource dataSource() {

		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		//dataSource.setDriverClassName("org.postgresql.Driver");
		//dataSource.setUrl("jdbc:postgresql://localhost:5432/AppRH"); // ?useTimezone=true&serverTimezone=UTC
		dataSource.setUrl("jdbc:sqlserver://10.51.12.12:1433;databaseName=dbAgroTest;encrypt=true;trustServerCertificate=true");
		dataSource.setUsername("jdev");
		dataSource.setPassword("jdev.123");
		return dataSource;

	}

	@Bean
	public JpaVendorAdapter jpaVendorAdapter() {

		HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
		adapter.setDatabase(Database.SQL_SERVER);
		adapter.setShowSql(true);
		adapter.setGenerateDdl(true);
		adapter.setDatabasePlatform("org.hibernate.dialect.SQLServer2016Dialect");
		adapter.setPrepareConnection(true);
		return adapter;

	}
}
