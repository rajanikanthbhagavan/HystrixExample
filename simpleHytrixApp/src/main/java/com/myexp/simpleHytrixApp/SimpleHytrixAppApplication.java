package com.myexp.simpleHytrixApp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@SpringBootApplication
@EnableCircuitBreaker
@EnableHystrixDashboard
@RestController
public class SimpleHytrixAppApplication {
	
	@Autowired
	RestTemplate restTemplate;

	@Bean	
	public RestTemplate getRestTemplate(){
		return new RestTemplate();
	}
	
	public static void main(String[] args) {
		SpringApplication.run(SimpleHytrixAppApplication.class, args);
	}
	
	@RequestMapping(value="/callRestAip", method=RequestMethod.GET)
	@HystrixCommand(fallbackMethod="callMylocalApi")
	public String callRestAip(){
		return restTemplate.getForObject("http://localhost:9090/simpleRestRequest", String.class);	
	}
	
	public String callMylocalApi(){
		
		return "This is called from my local API Method"; 
	}

}

