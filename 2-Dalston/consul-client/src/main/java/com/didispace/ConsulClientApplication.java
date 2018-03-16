package com.didispace;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient // Discovery Client 标识,并会自动化读取相关配置,向服务注册中心发现服务并进行调用。
@SpringBootApplication
public class ConsulClientApplication {

	public static void main(String[] args) {
		new SpringApplicationBuilder(ConsulClientApplication.class).web(true).run(args);
	}

}
