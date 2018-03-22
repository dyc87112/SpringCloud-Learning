package com.didispace;

import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@EnableDiscoveryClient
@SpringBootApplication
public class Trace1Application {

	private final Logger logger = Logger.getLogger(getClass());

    /*
    在开发调试期间，通常会收集全部跟踪信息输出到远程仓库，
    将其值设置为1，
        或者
    通过创建 AlwaysSampler 的 Bean（它实现的 isSampled 方法始终返回true）来覆盖默认的 PercentageBasedSampler 策略，比如：
    */
    /*
    @Bean
	public AlwaysSampler defaultSampler() {
		return new AlwaysSampler();
	}
	*/

	@Bean
	@LoadBalanced
	RestTemplate restTemplate() {
		return new RestTemplate();
	}

    @RequestMapping(value = "/info", method = RequestMethod.GET)
	@GetMapping("/info")
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

在Sleuth中采用了抽象收集的方式来为跟踪信息打上收集标记，第四个boolean类型的值，它代表了该信息是否要被后续的跟踪信息收集器获取和存储。
*/

/*
在工程中引入 spring-cloud-starter-sleuth 依赖之后， 它会自动的为当前应用构建起各通信通道的跟踪机制，
比如：
    通过诸如 RabbitMQ、Kafka（或者其他任何 Spring Cloud Stream 绑定器实现的消息中间件）传递的请求；
    通过 Zuul 代理传递的请求；
    通过 RestTemplate 发起的请求；
*/

/*
在Sleuth中的抽样收集策略是通过Sampler接口实现的，它的定义如下：
public interface Sampler {
    boolean isSampled(Span span);
}

通过实现 isSampled 方法，Spring Cloud Sleuth 会在产生跟踪信息的时候调用它来为跟踪信息生成是否要被收集的标志。
需要注意的是，即使isSampled返回了false，它仅代表该跟踪信息不被输出到后续对接的远程分析系统（比如：Zipkin），对于请求的跟踪活动依然会进行，所以我们在日志中还是能看到收集标识为false的记录。

默认情况下，Sleuth 会使用 PercentageBasedSampler 实现的抽样策略，以请求百分比的方式配置和收集跟踪信息，
在 application.properties 中配置下面的参数对其百分比值进行设置，它(跟踪信息取样比例)的默认值为 0.1 ，代表收集 10% 的请求跟踪信息。

spring.sleuth.sampler.percentage=0.1

在开发调试期间，通常会收集全部跟踪信息输出到远程仓库，我们可以将其值设置为1，
或者也可以通过创建 AlwaysSampler的Bean（它实现的 isSampled 方法始终返回true）来覆盖默认的 PercentageBasedSampler 策略，比如：
@Bean
public AlwaysSampler defaultSampler() {
    return new AlwaysSampler();
}

在实际使用时，通过与Span对象中存储信息的配合，我们可以根据实际情况做出更贴近需求的抽样策略，比如实现一个仅对包含指定Tag的抽样策略：
public class TagSampler implements Sampler {
    private String tag;
    public TagSampler(String tag) {
        this.tag = tag;
    }
    @Override
    public boolean isSampled(Span span) {
        return span.tags().get(tag) != null;
    }
}

*/