package com.wipro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class EcommerceConfigserverApplication {

	public static void main(String[] args) {
		SpringApplication.run(EcommerceConfigserverApplication.class, args);
	}

}
