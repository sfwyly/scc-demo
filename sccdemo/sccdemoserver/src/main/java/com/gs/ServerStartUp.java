package com.gs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * @ClassName ServerStartUp
 * @Description TODO
 * @Author 逝风无言
 * @Data 2019/9/11 19:58
 * @Version 1.0
 **/
@SpringBootApplication
@EnableConfigServer
public class ServerStartUp {
    public static void main(String[] args) {
        SpringApplication.run(ServerStartUp.class,args);
    }
}
