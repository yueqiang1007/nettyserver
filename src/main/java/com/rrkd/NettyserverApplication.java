package com.rrkd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration
public class NettyserverApplication {

	public static void main(String[] args) {
		SpringApplication.run(NettyserverApplication.class, args);
	}
}
