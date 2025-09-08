package com.mymatch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MyMatchApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyMatchApplication.class, args);
    }
}
