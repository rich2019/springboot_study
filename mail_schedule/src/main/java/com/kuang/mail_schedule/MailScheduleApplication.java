package com.kuang.mail_schedule;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MailScheduleApplication {

    public static void main(String[] args) {
        SpringApplication.run(MailScheduleApplication.class, args);
    }

}
