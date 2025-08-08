package com.soohwang.bluebuddy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BlueBuddyApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlueBuddyApplication.class, args);
    }

}
