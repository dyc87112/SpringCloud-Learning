package com.didispace.stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;

/*创建用于接收来自RabbitMQ消息的消费者SinkReceiver*/
/*@EnableBinding，该注解用来指定一个或多个定义了@Input或@Output注解的接口，以此实现对消息通道（Channel）的绑定。*/
/*通过@EnableBinding(Sink.class)绑定了Sink接口，该接口是Spring Cloud Stream中默认实现的对输入消息通道绑定的定义*/
@EnableBinding(Sink.class)
public class SinkReceiver {
    private static Logger logger = LoggerFactory.getLogger(SinkReceiver.class);
    /*@StreamListener：该注解主要定义在方法上，作用是将被修饰的方法注册为消息中间件上数据流的事件监听器，注解中的属性值对应了监听的消息通道名。
        在例子中，我们通过@StreamListener(Sink.INPUT)注解将receive方法注册为对input消息通道的监听处理器，
        所以当我们在RabbitMQ的控制页面中发布消息的时候，receive方法会做出对应的响应动作。*/
    @StreamListener(Sink.INPUT)
    public void receive(Object payload) {
        logger.info("Received: " + payload.toString());
    }

}

/*Sink 接口：实现绑定 input 通道 ；Source 接口：实现绑定 output 通道 ；Processor接口：结合 Sink 和 Source ；*/
/*当我们需要为 @EnableBinding 指定多个接口来绑定消息通道的时候，可以这样定义：@EnableBinding(value = {Sink.class, Source.class}) */

/*
public interface Sink {
	String INPUT = "input";

	@Input(Sink.INPUT)
	SubscribableChannel input();
}
*/
/*
public interface Source {
	String OUTPUT = "output";

	@Output(Source.OUTPUT)
	MessageChannel output();
}
*/
/*
 public interface Processor extends Source, Sink {}
*/
