package com.ecommerce.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class AppApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(AppApplication.class, args);

//		System.out.println("---- REGISTERED BEANS ----");
//		for (String beanName : context.getBeanDefinitionNames()) {
//			System.out.println(beanName);
//		}
	}

}
