package com.didispace;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.rxjava.EnableRxJavaProcessor;
import org.springframework.cloud.stream.annotation.rxjava.RxJavaProcessor;
import org.springframework.context.annotation.Bean;

/**
 *
 *
 * @author 翟永超
 * @create 2016/11/8.
 * @blog http://blog.didispace.com
 */
//@EnableRxJavaProcessor
//@EnableBinding(value = {Processor.class})
public class App1 {

    private static Logger logger = LoggerFactory.getLogger(HelloApplication.class);

//    @StreamListener(Processor.INPUT)
//    @SendTo(Processor.OUTPUT)
//    public Object receiveFromInput(Object payload) {
//        logger.info("Received: " + payload);
//        return "From Input Channel Return - " + payload;
//    }

    /**原生实现**/

//    @ServiceActivator(inputChannel= Processor.INPUT, outputChannel = Processor.OUTPUT)
//    public Object receiveFromInput(Object payload) {
//        logger.info("Received: " + payload);
//        return "From Input Channel Return - " + payload;
//    }

    /**rxjava实现**/

    @Bean
    public RxJavaProcessor<String,String> processor() {
        return inputStream -> inputStream.map(data -> {
            logger.info("Received: " + data);
            return data;
        }).buffer(5).map(data -> String.valueOf("From Input Channel Return - " + data));
    }

}
