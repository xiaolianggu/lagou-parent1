package com.lagou.edu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EntityScan("com.lagou.edu.pojo")
//@EnableEurekaClient  // 开启Eureka Client（Eureka独有）
@EnableDiscoveryClient // 开启注册中心客户端 （通用型注解，比如注册到Eureka、Nacos等）
// 说明：从SpringCloud的Edgware版本开始，不加注解也ok，但是建议大家加上
//@EnableFeignClients  // 开启Feign 客户端功能
public class UserApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class,args);
    }
}

