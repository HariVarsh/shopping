package com.mindtree.assignment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@SpringBootApplication
public class AssignmentApplication {
	private static Logger logger = LogManager.getRootLogger();
	public static void main(String[] args) {
		SpringApplication.run(AssignmentApplication.class, args);
		logger.info("Starting the springboot app...");

	}

}
