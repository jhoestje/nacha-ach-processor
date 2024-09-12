package com.khs.payroll.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "com.khs.payroll.repository")  
@EnableMongoAuditing
public class MongoDbConfig {
}
