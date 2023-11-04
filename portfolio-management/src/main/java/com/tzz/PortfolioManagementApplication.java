package com.tzz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
//import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
//@ComponentScan("com.tzz")
public class PortfolioManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(PortfolioManagementApplication.class, args);
	}

}
