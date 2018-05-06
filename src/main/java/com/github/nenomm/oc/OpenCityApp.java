package com.github.nenomm.oc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OpenCityApp {

	public static final String APP_PREFIX = "oc";

	public static void main(String[] args) {
		SpringApplication.run(OpenCityApp.class, args);
	}
}
