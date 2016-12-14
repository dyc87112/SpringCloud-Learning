package com.didispace;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.integration.annotation.ServiceActivator;

/**
 * @author 翟永超
 * @create 2016/11/8.
 * @blog http://blog.didispace.com
 */
//@EnableBinding(value = {Sink.class})
public class SinkReceiver3 {

    private static Logger logger = LoggerFactory.getLogger(HelloApplication.class);

    @StreamListener(Sink.INPUT)
    public void receive(User user) {
        logger.info("Received: " + user);
    }

//    @Bean
//    @InboundChannelAdapter(value = Sink.INPUT, poller = @Poller(fixedDelay = "2000"))
//    public MessageSource<String> timerMessageSource() {
//        Map<String, Object> headers = new HashMap<>();
//        headers.put("content-type", "application/user");
//        return () -> new GenericMessage<>("{\"name\":\"didi\", \"age\":30}", headers);
//    }

    // Need @Transformer
//    @ServiceActivator(inputChannel=Sink.INPUT)
//    public void receive(User user) {
//        logger.info("Received: " + user);
//    }

//    @Transformer(inputChannel = Sink.INPUT, outputChannel = Sink.INPUT)
//    public User transform(String message) throws Exception {
//        ObjectMapper objectMapper = new ObjectMapper();
//        User user = objectMapper.readValue(message, User.class);
//        return user;
//    }

}
