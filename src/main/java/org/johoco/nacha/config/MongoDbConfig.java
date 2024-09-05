package org.johoco.nacha.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "org.johoco.nacha.repository")  
@EnableMongoAuditing
public class MongoDbConfig {
public MongoDbConfig() {
   System.out.println("here");
}
}
