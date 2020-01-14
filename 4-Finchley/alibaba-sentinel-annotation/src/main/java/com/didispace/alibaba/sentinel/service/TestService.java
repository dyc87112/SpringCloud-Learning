package com.didispace.alibaba.sentinel.service;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class TestService {

    // 限流与阻塞处理
    @SentinelResource(value = "doSomeThing", blockHandler = "exceptionHandler")
    public void doSomeThing(String str) {
        log.info(str);
    }

    public void exceptionHandler(String str, BlockException ex) {
        log.error("blockHandler：" + str, ex);
    }

    // 熔断与降级处理
    @SentinelResource(value = "doSomeThing2", fallback = "fallbackHandler")
    public void doSomeThing2(String str) {
        log.info(str);
        throw new RuntimeException("发生异常");
    }

    public void fallbackHandler(String str) {
        log.error("fallbackHandler：" + str);
    }

}