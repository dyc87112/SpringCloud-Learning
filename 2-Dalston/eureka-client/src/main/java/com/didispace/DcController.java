package com.didispace;

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
    //   - DiscoveryClient 服务发现客户端，具有以下方法：
    //       - String description(); 获取描述
    //       - ServiceInstance getLocalServiceInstance(); @Deprecated 方法被删除，推荐不要使用。获取本地服务实例
    //       - List<ServiceInstance> getInstances(String serviceId);  通过服务 ID，获取当前服务的服务实例
    //       - List<String> getServices(); 获取所有服务 ID 列表

/*    @GetMapping("/dc")
    public String dc() throws InterruptedException{
        // 服务请求超时异常 测试
        Thread.sleep(5000L);

        String services = "Services: " + discoveryClient.getServices();
        System.out.println(services);
        return services;
    }*/

    @GetMapping("/dc")
    public String dc(){
        String services = "Services: " + discoveryClient.getServices();
        System.out.println(services);
        return services;
    }


}
