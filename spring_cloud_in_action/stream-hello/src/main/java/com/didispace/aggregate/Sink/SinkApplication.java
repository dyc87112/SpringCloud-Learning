package com.didispace.aggregate.Sink;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.integration.annotation.ServiceActivator;

@SpringBootApplication
@EnableBinding(Sink.class)
public class SinkApplication {

    private static Logger logger = LoggerFactory.getLogger(SinkApplication.class);

    @ServiceActivator(inputChannel=Sink.INPUT)
    public void loggerSink(Object payload) {
        logger.info("Received: " + payload);
    }
}