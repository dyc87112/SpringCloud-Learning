package com.didispace.web;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@Service
public class HelloService {

    private final Logger logger = Logger.getLogger(getClass());

    @Autowired
    RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "helloFallback", commandKey = "helloKey")
    public String hello() {
        long start = System.currentTimeMillis();

        StringBuilder result = new StringBuilder();

        // GET
        result.append(restTemplate.getForEntity("http://HELLO-SERVICE/hello", String.class).getBody()).append("<br>");
        result.append(restTemplate.getForEntity("http://HELLO-SERVICE/hello1?name={1}", String.class, "didi").getBody()).append("<br>");

        Map<String, String> params = new HashMap<>();
        params.put("name", "dada");
        result.append(restTemplate.getForEntity("http://HELLO-SERVICE/hello1?name={name}", String.class, params).getBody()).append("<br>");

        UriComponents uriComponents = UriComponentsBuilder.fromUriString(
                "http://HELLO-SERVICE/hello1?name={name}")
                .build()
                .expand("dodo")
                .encode();
        URI uri = uriComponents.toUri();
        result.append(restTemplate.getForEntity(uri, String.class).getBody()).append("<br>");

        // POST
        User user = new User("didi", 20);
        String postResult = restTemplate.postForObject("http://HELLO-SERVICE/hello3", user, String.class);
        result.append(postResult).append("<br>");

        user = new User("didi", 30);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity("http://HELLO-SERVICE/hello3", user, String.class);
        result.append(responseEntity.getBody()).append("<br>");

//        user = new User("didi", 40);
//        URI responseURI = restTemplate.postForLocation("http://HELLO-SERVICE/hello3", user);
//        result.append(responseURI).append("<br>");
//
//        Long id = 10001L;
//        restTemplate.delete("http://USER-SERVICE/user/{1}",  id);

        long end = System.currentTimeMillis();

        logger.info("Spend time : " + (end - start) );
        return result.toString();
    }

    public String helloFallback() {
        return "error";
    }

}