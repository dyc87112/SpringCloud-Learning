package com.didispace;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@EnableDiscoveryClient
@SpringBootApplication
public class ConsulApplication {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private DiscoveryClient discoveryClient;

	@RequestMapping(value = "/hello", method = RequestMethod.GET)
	public String hello() {
		for(String service : discoveryClient.getServices()) {
			logger.info("service : " + service);
			List<ServiceInstance> list = discoveryClient.getInstances(service);
			for (ServiceInstance serviceInstance : list) {
				logger.info("====：" + serviceInstance.getUri() + ", " + serviceInstance.getMetadata());
			}
		}
		return "hello-1";
	}

	public static void main(String[] args) {
		SpringApplication.run(ConsulApplication.class, args);
	}

}
