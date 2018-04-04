package com.didispace;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@EnableDiscoveryClient // Eureka Discovery Client 标识,并会自动化读取 Eureka 相关配置。还有向服务注册中心发现服务并进行调用。
@SpringBootApplication
public class EurekaConsumerApplication {

	// HTTP 访问操作类,初始化RestTemplate，用来真正发起REST请求。
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	public static void main(String[] args) {
		new SpringApplicationBuilder(EurekaConsumerApplication.class).web(true).run(args);
	}

}
