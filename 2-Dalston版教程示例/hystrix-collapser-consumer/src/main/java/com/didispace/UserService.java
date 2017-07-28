package com.didispace;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCollapser;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

/**
 * @author 翟永超
 * @create 2017/7/25.
 * @blog http://blog.didispace.com
 */
@Slf4j
@Service
public class UserService {

    @Autowired
    RestTemplate restTemplate;

    @HystrixCollapser(
            scope = com.netflix.hystrix.HystrixCollapser.Scope.GLOBAL,
            batchMethod = "findByIds",
            collapserProperties = {
                @HystrixProperty(name="timerDelayInMilliseconds", value = "100")
            }
    )
    public Future<String> findById(Long id) {
        log.info("findById : " + id);
        return null;
    }

    @HystrixCommand(commandProperties = @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000"))
    public List<String> findByIds(List<Long> ids) {
        log.info("findByIds : " + ids);
        List<String> result = restTemplate.getForObject("http://hystrix-collapser-provider/users?ids={1}",
                List.class, StringUtils.join(ids, ","));
        log.info(result.toString());
        return result;
    }

}
