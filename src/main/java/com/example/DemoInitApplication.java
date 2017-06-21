package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@SpringBootApplication
public class DemoInitApplication implements CommandLineRunner {

	@Autowired
	private Tasks tasksRepo;

	public static void main(String[] args) {
		SpringApplication.run(DemoInitApplication.class, args);

	}

	@Override
	public void run(String... strings) throws Exception{
		tasksRepo.save(new Task(null, "Name0", 3));
		tasksRepo.save(new Task(null, "Name2", 2));
		tasksRepo.save(new Task(null, "Name1", 1));
	}

}
