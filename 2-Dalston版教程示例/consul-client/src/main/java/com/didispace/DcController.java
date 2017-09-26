package com.didispace;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 翟永超
 * @create 2017/4/15.
 * @blog http://blog.didispace.com
 */
@RestController
public class DcController {

	@Autowired
	DiscoveryClient discoveryClient;

	@GetMapping("/dc")
	public String dc() {
		List<String> servicces = discoveryClient.getServices();
		String services = "Services: " + servicces;
		System.out.println(services);
		return services;
	}

	@GetMapping("/info")
	public String info() {
		List<String> servicces = discoveryClient.getServices();
		String services = "Services: " + servicces;
		System.out.println(services);
		return services;
	}

}
