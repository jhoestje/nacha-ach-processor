package com.khs.payroll;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.khs.payroll.properties.ApplicationProperties;

@SpringBootApplication
@EnableConfigurationProperties(ApplicationProperties.class)
public class AchPayrollApplication {
    
	public static void main(String[] args) {
		SpringApplication.run(AchPayrollApplication.class, args);
	}

}
