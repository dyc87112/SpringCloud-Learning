package com.didispace;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@EnableDiscoveryClient
@SpringBootApplication
public class Trace2Application {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@RequestMapping(value = "/info", method = RequestMethod.GET)
	public String info() {
		logger.info("===<call trace-2>===");
		return "trace-2";
	}

	@RequestMapping(value = "/trace-2", method = RequestMethod.GET)
	public String trace(HttpServletRequest request) {
		logger.info("===<call trace-2, TraceId={}, SpanId={}>===", request.getHeader("X-B3-TraceId"), request.getHeader("X-B3-SpanId"));
		return "Trace";
	}

	public static void main(String[] args) {
		SpringApplication.run(Trace2Application.class, args);
	}
}

/*
spring-cloud-starter-sleuth 组件会对该请求进行处理，在发送到trace-2之前sleuth会为在该请求的Header中增加实现跟踪需要的重要信息，
主要有下面这几个（通过查看 org.springframework.cloud.sleuth.Span 的源码获取）：
	X-B3-TraceId：一条请求链路（Trace）的唯一标识，必须值;
	X-B3-SpanId：一个工作单元（Span）的唯一标识，必须值;
	X-B3-ParentSpanId：标识当前工作单元所属的上一个工作单元，Root Span（请求链路的第一个工作单元）的该值为空;
	X-B3-Sampled：是否被抽样输出的标志，1表示需要被输出，0表示不需要被输出;
	X-Span-Name：工作单元的名称;
*/

/*
基于日志的分析系统，比如：ELK平台，它可以轻松的帮助我们来收集和存储这些跟踪日志，同时在需要的时候我们也可以根据Trace ID来轻松地搜索出对应请求链路相关的明细日志。

ELK平台主要有由 ElasticSearch、Logstash 和 Kiabana 三个开源免费工具组成：
    Elasticsearch 是个开源分布式搜索引擎，它的特点有：分布式，零配置，自动发现，索引自动分片，索引副本机制，restful风格接口，多数据源，自动搜索负载等。
    Logstash 是一个完全开源的工具，他可以对你的日志进行收集、过滤，并将其存储供以后使用。
    Kibana 也是一个开源和免费的工具，它Kibana可以为 Logstash 和 ElasticSearch 提供的日志分析友好的 Web 界面，可以帮助您汇总、分析和搜索重要数据日志。
*/