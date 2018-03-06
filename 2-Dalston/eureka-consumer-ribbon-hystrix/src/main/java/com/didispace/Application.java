package com.didispace;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**使用 @EnableCircuitBreaker 或 @EnableHystrix 注解开启Hystrix的使用*/
/**Hystrix具备 服务降级、服务熔断、线程隔离、请求缓存、请求合并以及服务监控 等强大功能。*/
@EnableCircuitBreaker
@EnableDiscoveryClient
@SpringBootApplication
public class Application {

	@Bean
	@LoadBalanced
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	public static void main(String[] args) {
		new SpringApplicationBuilder(Application.class).web(true).run(args);
	}

}

// 可以使用Spring Cloud应用中的 @SpringCloudApplication 注解来修饰应用主类，该注解的具体定义如下所示。
// 我们可以看到该注解中包含了上我们所引用的三个注解，这也意味着一个Spring Cloud标准应用应包含服务发现以及断路器。
/*
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@SpringBootApplication
@EnableDiscoveryClient
@EnableCircuitBreaker
public @interface SpringCloudApplication {
}*/


/*
*
*
* */