package com.didispace;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

/**
 * @author 翟永超
 * @create 2017/4/15.
 * @blog http://blog.didispace.com
 */
@RestController
public class DcController {

	@Autowired
	ConsumerService consumerService;

	@GetMapping("/consumer")
	public String dc() {
		return consumerService.consumer();
	}

	@Service
	class ConsumerService {

		@Autowired
		RestTemplate restTemplate;

		@HystrixCommand(fallbackMethod = "fallback")
		public String consumer() {
			return restTemplate.getForObject("http://eureka-client/dc", String.class);
		}

		public String fallback() {
			return "fallbck";
		}

	}

}
