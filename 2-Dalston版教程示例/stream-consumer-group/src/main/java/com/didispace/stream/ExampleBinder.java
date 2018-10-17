package com.didispace.stream;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

interface ExampleBinder {

    String NAME = "example-topic";

    @Input(NAME)
    SubscribableChannel input();

}