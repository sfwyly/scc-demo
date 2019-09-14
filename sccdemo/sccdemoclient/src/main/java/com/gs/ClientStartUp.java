package com.gs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @ClassName ClientStartUp
 * @Description TODO
 * @Author 逝风无言
 * @Data 2019/9/11 20:27
 * @Version 1.0
 **/
@SpringBootApplication
//@RestController
public class ClientStartUp {

//    @Value("${guestname}")
//    String name;
//
//    @RequestMapping(value = "/",method = RequestMethod.GET)
//    public String sayHello(){
//        return "Hello,"+name;
//    }

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
    public static void main(String[] args) {
        SpringApplication.run(ClientStartUp.class,args);
    }
}
