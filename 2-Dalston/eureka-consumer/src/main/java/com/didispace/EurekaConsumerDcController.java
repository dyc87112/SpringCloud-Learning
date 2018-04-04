package com.didispace;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class EurekaConsumerDcController {

    private static final Logger logger = LoggerFactory.getLogger(EurekaConsumerDcController.class);

    /*
        LoadBalanced 是通过 Ribbon 客户端负载均衡去调用服务提供者集群的。
        即可以在获取的服务提供者实例列表中，通过 Ribbon 进行选择某实例，然后调用该服务实例。
    */
    @Autowired
    LoadBalancerClient loadBalancerClient;

    @Autowired
    RestTemplate restTemplate; // HTTP 访问操作类

    @GetMapping("/eureka/consumer/dc")
    public String dc() {
        ServiceInstance serviceInstance = loadBalancerClient.choose("eureka-client");
        String url = "http://" + serviceInstance.getHost() + ":" + serviceInstance.getPort() + "/eureka/client/dc";
        logger.info("url:"+url);
        return restTemplate.getForObject(url, String.class);
    }
}
