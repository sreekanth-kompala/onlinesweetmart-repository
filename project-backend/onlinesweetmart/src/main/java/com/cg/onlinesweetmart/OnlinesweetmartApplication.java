package com.cg.onlinesweetmart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class OnlinesweetmartApplication {
	public static void main(String[] args) {
		SpringApplication.run(OnlinesweetmartApplication.class, args);
	}
}