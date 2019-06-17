package com.ppt.pptsso.service;

import com.ppt.pptsso.bean.RegistMsg;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.impl.AMQImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.IOException;


@Service
public class RabbitMailService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMailService.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private MailService mailService;


    public String EXCHANGE_REGIST_NAME = "ptt-fanout-registMsg";

    public String ROUTING_KEY_REGIST = "regist.sendMsg";




    /**
     * 给mq发送  发邮件的消息
     * */

    public void sendMsg(Boolean status,String url,String toEmail){
        RegistMsg registMsg = new RegistMsg();
        registMsg.setType(1);
        registMsg.setUrl(url);
        registMsg.setUserStatus(status);
        registMsg.setToEmail(toEmail);
        this.rabbitTemplate.convertAndSend(EXCHANGE_REGIST_NAME,ROUTING_KEY_REGIST,registMsg);

    }

    /**
     *  监听下消息的方法
     * */
    @RabbitListener(queues = {"ptt.regist"})
    public void receiveMsg(RegistMsg registMsg, Message message, Channel channel){
        System.out.println(registMsg+"------------------***********----------------");
        LOGGER.info("监听到mq发来的消息了...");
        try {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
            LOGGER.warn("[Consumer Message 01] ===============> " + registMsg.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        String subject = "票淘淘邮箱激活验证";
        String callbackUrl = registMsg.getUrl();
        String text = "亲~猛击我！激活票淘淘，完成邮箱绑定哦";
        String textHtml = "<a href='"+callbackUrl+ "'>"+text+"</a>";
        String from = "2502644175@qq.com";
        String to = registMsg.getToEmail();

        try {
            mailService.send(subject,textHtml,from,to);
            System.out.println("发送信息完成...");
        } catch (MessagingException e) {
            e.printStackTrace();
            LOGGER.info("邮件发送失败！...");
            return;
        }
        LOGGER.info("发送邮件成功！....");
    }

}
