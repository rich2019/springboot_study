package com.kuang.mail_schedule;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@SpringBootTest
class MailScheduleApplicationTests {

    @Autowired
    private JavaMailSenderImpl mailSender;

    @Test
    void contextLoads() {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setSubject("打卡");
        mailMessage.setText("记得打卡");

//        mailMessage.setTo("3272485171@qq.com");
        mailMessage.setTo("1327571308@qq.com");
        mailMessage.setFrom("1327571308@qq.com" );

        mailSender.send(mailMessage);
    }

}
