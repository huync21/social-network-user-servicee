package com.example.socialnetworkuserserviceapplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableConfigurationProperties
@EnableAsync
public class SocialNetworkUserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SocialNetworkUserServiceApplication.class, args);
    }

}
