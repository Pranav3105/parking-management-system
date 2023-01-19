package com.pranav.pms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class ParkingManagementSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(ParkingManagementSystemApplication.class, args);
	}

}
