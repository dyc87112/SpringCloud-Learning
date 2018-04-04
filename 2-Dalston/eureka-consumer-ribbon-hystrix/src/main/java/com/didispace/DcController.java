package com.didispace;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author 翟永超
 * @create 2017/4/15.
 * @blog http://blog.didispace.com
 */
@RestController
public class DcController {

    @Autowired
    ConsumerService consumerService;

    @GetMapping("/consumer")
    public String dc() throws InterruptedException{
        return consumerService.consumer();
    }

    @Service
    class ConsumerService {

        @Autowired
        RestTemplate restTemplate;

        // 在为具体执行逻辑的函数上增加 @HystrixCommand 注解来指定服务降级方法
        @HystrixCommand(fallbackMethod = "fallback")
        public String consumer() {
            return restTemplate.getForObject("http://eureka-client/dc", String.class);
        }

        public String fallback() {
            return "fallbck："+"服务请求超时异常测试，服务提供方输出延迟5秒。" +
                    "服务消费方触发了服务请求超时异常，服务消费者就通过HystrixCommand注解中指定的降级逻辑进行执行，因此该请求的结果返回了fallback。" +
                    "这样的机制，对自身服务起到了基础的保护，同时还为异常情况提供了自动的服务降级切换机制。";
        }

    }

}

