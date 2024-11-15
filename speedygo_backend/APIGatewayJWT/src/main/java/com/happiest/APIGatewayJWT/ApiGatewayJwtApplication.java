package com.happiest.APIGatewayJWT;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ApiGatewayJwtApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiGatewayJwtApplication.class, args);
	}

}
