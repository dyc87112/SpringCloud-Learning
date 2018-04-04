package com.didispace;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author 翟永超
 * @create 2017/6/24.
 * @blog http://blog.didispace.com
 */
@FeignClient(name = "eureka-client", fallback = DcClientFallback.class)
public interface DcClient {

    @GetMapping("/dc")
    String consumer();

}
