package org.johoco.nacha;

import org.johoco.nacha.properties.ApplicationProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableConfigurationProperties(ApplicationProperties.class)
@EnableMongoRepositories
@EnableMongoAuditing
public class NachaAchProcessorApplication {
    
	public static void main(String[] args) {
		SpringApplication.run(NachaAchProcessorApplication.class, args);
	}

}
