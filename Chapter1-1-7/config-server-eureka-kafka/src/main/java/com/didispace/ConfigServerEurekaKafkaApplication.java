package com.didispace;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.config.server.EnableConfigServer;

@EnableDiscoveryClient
@EnableConfigServer
@SpringBootApplication
public class ConfigServerEurekaKafkaApplication {

	public static void main(String[] args) {
		new SpringApplicationBuilder(ConfigServerEurekaKafkaApplication.class).web(true).run(args);
	}

}
