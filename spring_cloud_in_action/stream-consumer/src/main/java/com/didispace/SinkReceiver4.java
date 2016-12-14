package com.didispace;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;

/**
 * 分区试验接收者
 * @author 翟永超
 * @create 2016/11/8.
 * @blog http://blog.didispace.com
 */
@EnableBinding(value = {Sink.class})
public class SinkReceiver4 {

    private static Logger logger = LoggerFactory.getLogger(HelloApplication.class);


    @StreamListener(Sink.INPUT)
    public void receive(User user) {
        logger.info("Received: " + user);
    }

//    配置属性如下：
//    # Comsumer Group:input
//    spring.cloud.stream.bindings.input.group=Service-A
//
//    # Partition
//    spring.cloud.stream.bindings.input.destination=greetings
//    spring.cloud.stream.bindings.input.consumer.partitioned=true
//    spring.cloud.stream.instanceCount=2
//    spring.cloud.stream.instanceIndex=0

}
