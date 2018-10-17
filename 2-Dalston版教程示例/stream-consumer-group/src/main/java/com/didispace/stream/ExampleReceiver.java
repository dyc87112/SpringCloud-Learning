package com.didispace.stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

@EnableBinding(ExampleBinder.class)
public class ExampleReceiver {

    private static Logger logger = LoggerFactory.getLogger(ExampleReceiver.class);

    @StreamListener(ExampleBinder.NAME)
    public void receive(String payload) {
        logger.info("Received: " + payload);
    }

}