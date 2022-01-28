package com.ecomm;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import com.ecomm.entity.User;
import com.ecomm.repository.UserRepository;

@SpringBootApplication
@EnableEurekaClient
public class UserServiceApplication {

	@Autowired
	private UserRepository userRepository;

	@PostConstruct
	public void initOrdersTable() {
		userRepository.saveAll(Stream
				.of(new User("Guru", "Murthy", "guru@guru.com", 1),	 new User("Krishna", "Bhagavan", "krishna@Krishna.com", 2),
						new User("Hara", "Krishna", "guru@guru.com", 3),	 new User("Rama", "Chandra", "rama@Rama.com", 4),
						new User("Jaya", "Prakash", "jaya@jaya.com", 5),	 new User("Stephen", "Johm", "stephen@stephen.com", 6))
				.collect(Collectors.toList()));
	}
	
	@Bean
	@LoadBalanced
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
	
	public static void main(String[] args) {
		SpringApplication.run(UserServiceApplication.class, args);
	}

}
