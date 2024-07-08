package io.spring.DemoBoot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoBootApplication {

	public static void main(String[] args) {
		System.out.println("THE APPLICATION HAS NOT STARTED YET");
		SpringApplication.run(DemoBootApplication.class, args);
		System.out.println("THE APPLICATION HAS ALREADY STARTED");
	}

}