package com.didispace.config.server.db;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

@EnableConfigServer
@SpringBootApplication
public class ConfigServerBootstrap {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(ConfigServerBootstrap.class);

        JdbcTemplate jdbcTemplate = context.getBean(JdbcTemplate.class);
        jdbcTemplate.execute("delete from properties");
        jdbcTemplate.execute("INSERT INTO properties VALUES(1, 'com.didispace.message', 'test-stage-master', 'config-client', 'stage', 'master')");
        jdbcTemplate.execute("INSERT INTO properties VALUES(2, 'com.didispace.message', 'test-online-master', 'config-client', 'online', 'master')");
        jdbcTemplate.execute("INSERT INTO properties VALUES(3, 'com.didispace.message', 'test-online-develop', 'config-client', 'online', 'develop')");
        jdbcTemplate.execute("INSERT INTO properties VALUES(4, 'com.didispace.message', 'hello-online-master', 'hello-service', 'online', 'master')");
        jdbcTemplate.execute("INSERT INTO properties VALUES(5, 'com.didispace.message', 'hello-online-develop', 'hello-service', 'online', 'develop')");

    }

    // 1. 直接访问config-server-db获取获取信息

    // curl http://localhost:10020/config-client/stage/
    // {
    //    "name": "config-client",
    //    "profiles": [
    //        "stage"
    //    ],
    //    "label": null,
    //    "version": null,
    //    "state": null,
    //    "propertySources": [
    //        {
    //            "name": "config-client-stage",
    //            "source": {
    //                "com.didispace.message": "test-stage-master"
    //            }
    //        }
    //    ]
    //}

    // curl http://localhost:10020/hello-service/stage/develop
    // {
    //    "name": "hello-service",
    //    "profiles": [
    //        "online"
    //    ],
    //    "label": "develop",
    //    "version": null,
    //    "state": null,
    //    "propertySources": [
    //        {
    //            "name": "hello-service-online",
    //            "source": {
    //                "com.didispace.message": "hello-online-develop"
    //            }
    //        }
    //    ]
    //}

    // 2. 访问客户端获取数据
    // curl http://localhost:10025/test
    // test-stage-master

    // 3. 数据库中修改配置，刷新配置
    // curl -X POST http://localhost:10025/refresh
    // ["com.didispace.message"]

    // 4. 验证配置
    // curl http://localhost:10025/test
    // test-stage-master-xxx



}
