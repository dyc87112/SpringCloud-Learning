package com.didispace.config.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class ConfigClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConfigClientApplication.class);
    }

    @RefreshScope
    @RestController
    class TestController {

        @Value("${com.didispace.message}")
        private String message;

        @GetMapping("/test")
        public String test() {
            return message;
        }

    }

}
