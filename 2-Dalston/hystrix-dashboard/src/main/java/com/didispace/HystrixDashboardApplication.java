package com.didispace;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

/*为应用主类加上@EnableHystrixDashboard，启用 Hystrix Dashboard 功能。*/
@EnableHystrixDashboard
@SpringCloudApplication
public class HystrixDashboardApplication {

    public static void main(String[] args) {
        SpringApplication.run(HystrixDashboardApplication.class, args);
    }

}
