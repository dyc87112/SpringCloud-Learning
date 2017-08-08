package com.didispace.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author 翟永超
 * @create 2017/8/8.
 * @blog http://blog.didispace.com
 */
public interface HelloService {

    @GetMapping("/hello")
    String hello(@RequestParam(value = "name") String name);

}
