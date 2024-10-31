package com.numan.journalapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

@SpringBootApplication
public class JournalApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext configurableApplicationContext = SpringApplication.run(JournalApplication.class, args);
		ConfigurableEnvironment configurableEnvironment = configurableApplicationContext.getEnvironment();
		//System.out.println(configurableEnvironment.getActiveProfiles()[0]);
	}

}
