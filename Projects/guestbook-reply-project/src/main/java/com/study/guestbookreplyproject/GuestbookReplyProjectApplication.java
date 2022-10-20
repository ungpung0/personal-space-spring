package com.study.guestbookreplyproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class GuestbookReplyProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(GuestbookReplyProjectApplication.class, args);
    }

}
