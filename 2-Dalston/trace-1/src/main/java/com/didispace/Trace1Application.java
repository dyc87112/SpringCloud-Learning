package com.didispace;

import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@EnableDiscoveryClient
@SpringBootApplication
public class Trace1Application {

	private final Logger logger = Logger.getLogger(getClass());

//	@Bean
//	public AlwaysSampler defaultSampler() {
//		return new AlwaysSampler();
//	}

	@Bean
	@LoadBalanced
	RestTemplate restTemplate() {
		return new RestTemplate();
	}

    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public String info() {
        logger.info("===<call trace-1>===");
        return "trace-1,"+restTemplate().getForEntity("http://trace-2/info", String.class).getBody();
    }

	@RequestMapping(value = "/trace-1", method = RequestMethod.GET)
	public String trace() {
		logger.info("===<call trace-1>===");
		return restTemplate().getForEntity("http://trace-2/trace-2", String.class).getBody();
	}

	public static void main(String[] args) {
		SpringApplication.run(Trace1Application.class, args);
	}

}
/*
-- 日志
-- trace-1
INFO [trace-1,f410ab57afd5c145,a9f2118fa2019684,false] 25028 --- [nio-9101-exec-1] ication$$EnhancerBySpringCGLIB$$d8228493 : ===<call trace-1>===

-- trace-2
INFO [trace-2,f410ab57afd5c145,e9a377dc2268bc29,false] 23112 --- [nio-9102-exec-1] ication$$EnhancerBySpringCGLIB$$e6cb4078 : ===<call trace-2>===
*/

/*
从上面的控制台输出内容中，我们可以看到多了一些形如[trace-1,f410ab57afd5c145,a9f2118fa2019684,false]的日志信息，而这些元素正是实现分布式服务跟踪的重要组成部分，

它们每个值的含义如下：
	第一个值：trace-1，它记录了应用的名称，也就是 application.properties 中 spring.application.name 参数配置的属性。
	第二个值：f410ab57afd5c145，Spring Cloud Sleuth 生成的一个ID，称为 Trace ID，它用来标识一条请求链路。一条请求链路中包含一个 Trace ID，多个 Span ID。
	第三个值：a9f2118fa2019684，Spring Cloud Sleuth 生成的另外一个ID，称为 Span ID，它表示一个基本的工作单元，比如：发送一个HTTP请求。
	第四个值：false，表示是否要将该信息输出到 Zipkin 等服务中来收集和展示。

上面四个值中的 Trace ID 和 Span ID 是 Spring Cloud Sleuth 实现分布式服务跟踪的核心。
在一次服务请求链路的调用过程中，会保持并传递同一个 Trace ID，从而将整个分布于不同微服务进程中的请求跟踪信息串联起来，
以上面输出内容为例，trace-1 和 trace-2 同属于一个前端服务请求来源，所以他们的 Trace ID 是相同的，处于同一条请求链路中。

*/

/*
在工程中引入spring-cloud-starter-sleuth依赖之后， 它会自动的为当前应用构建起各通信通道的跟踪机制，
比如：
    通过诸如RabbitMQ、Kafka（或者其他任何Spring Cloud Stream绑定器实现的消息中间件）传递的请求；
    通过Zuul代理传递的请求；
    通过RestTemplate发起的请求；
*/

