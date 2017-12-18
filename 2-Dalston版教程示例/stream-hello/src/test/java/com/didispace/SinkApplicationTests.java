package com.didispace;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@EnableBinding(value = {SinkApplicationTests.SinkSender.class})
public class SinkApplicationTests {

	@Autowired
	private SinkSender sinkSender;

	@Test
	public void sinkSenderTester() {
		sinkSender.output().send(MessageBuilder.withPayload("produce a message ：http://blog.didispace.com").build());
	}

	public interface SinkSender {

		String OUTPUT = "input";

		@Output(SinkSender.OUTPUT)
		MessageChannel output();

	}

}
