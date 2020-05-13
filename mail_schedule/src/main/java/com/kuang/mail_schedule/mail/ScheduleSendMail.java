package com.kuang.mail_schedule.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class ScheduleSendMail {


    @Autowired
    private JavaMailSenderImpl mailSender;

    private static int i = 1;


    @Scheduled(cron = "58 0/20 * * * ?")
    public void scheduledSend() {
        System.out.println("每5分钟发送一次邮件");
        System.out.println("第" + (i++) + "次,发送邮件 " + new Date());
        sendMail();
        System.out.println("第" + i + "次,发送邮件完成 " + new Date());
        System.out.println("-------------------------");
    }

    public void sendMail() {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setSubject("打卡");
        mailMessage.setText("记得打卡");

//        mailMessage.setTo("3272485171@qq.com");
        mailMessage.setTo("1327571308@qq.com");
        mailMessage.setFrom("1327571308@qq.com");

        mailSender.send(mailMessage);
    }
}
