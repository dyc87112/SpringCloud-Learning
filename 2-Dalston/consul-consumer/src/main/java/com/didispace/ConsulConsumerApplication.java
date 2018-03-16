package com.didispace;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@EnableDiscoveryClient // Consul Discovery Client 标识,并会自动化读取 Consul 相关配置,向服务注册中心发现服务并进行调用。
@SpringBootApplication
public class ConsulConsumerApplication {

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	public static void main(String[] args) {
		new SpringApplicationBuilder(ConsulConsumerApplication.class).web(true).run(args);
	}

}
