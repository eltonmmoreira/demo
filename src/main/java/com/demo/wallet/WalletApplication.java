package com.demo.wallet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class WalletApplication {

	public static void main(String[] args) {
		System.out.println("TESTE DOCKER");
		SpringApplication.run(WalletApplication.class, args);
	}

}
