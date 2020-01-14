package com.didispace.alibaba.dubbo.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


@EnableDiscoveryClient
@SpringBootApplication
public class DubboServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(DubboServerApplication.class, args);
    }


}