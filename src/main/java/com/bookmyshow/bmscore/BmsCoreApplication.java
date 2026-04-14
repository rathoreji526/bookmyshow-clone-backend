package com.bookmyshow.bmscore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication //@Configuration, @EnableAutoConfiguration, @ComponentScan
public class BmsCoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(BmsCoreApplication.class, args);
	}

}
