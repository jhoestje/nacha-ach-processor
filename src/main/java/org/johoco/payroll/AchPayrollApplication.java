package org.johoco.payroll;

import org.johoco.payroll.properties.ApplicationProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(ApplicationProperties.class)
public class AchPayrollApplication {
    
	public static void main(String[] args) {
		SpringApplication.run(AchPayrollApplication.class, args);
	}

}
