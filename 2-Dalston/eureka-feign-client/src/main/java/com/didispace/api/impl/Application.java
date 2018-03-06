package com.didispace.api.impl;

import com.didispace.api.HelloService;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.RestController;

@EnableDiscoveryClient
@SpringBootApplication
public class Application {

	@RestController
	class HelloController implements HelloService {

		@Override
		public String hello(String name) {
			return "hello " + name;
		}

	}

	public static void main(String[] args) {
		new SpringApplicationBuilder(Application.class).web(true).run(args);
	}

}
