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
@EnableBinding(value = {ExampleApplicationTests.ExampleBinder.class})
public class ExampleApplicationTests {

	@Autowired
	private ExampleBinder exampleBinder;

	@Test
	public void exampleBinderTester() {
        exampleBinder.output().send(MessageBuilder.withPayload("Produce a message from : http://blog.didispace.com").build());
	}

	public interface ExampleBinder {

		String NAME = "example-topic";

		@Output(NAME)
		MessageChannel output();

	}

}
