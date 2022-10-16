package com.study.guestbookproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing // JPA Auditing을 활성화한다.
public class GuestbookProjectApplication {
    public static void main(String[] args) {
        SpringApplication.run(GuestbookProjectApplication.class, args);
    }
}
