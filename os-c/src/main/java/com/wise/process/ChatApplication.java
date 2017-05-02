package com.wise.process;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;

import com.wise.comet.ChatServer;

/**
 * 程序入口
 */
@SpringBootApplication
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class,HibernateJpaAutoConfiguration.class})
@ComponentScan(value={"com.wise.comet","com.wise.web"})
@ImportResource("classpath:dubbo-consumer.xml")    //dubbo时启用
public class ChatApplication implements CommandLineRunner {

    private static Logger LOG = LoggerFactory.getLogger(ChatApplication.class);

    @Resource(name = "tcpChatServer")
    private ChatServer tcpChatServer;
    
    @Resource(name = "webSocketChatServer")
    private ChatServer webSocketChatServer;


    public static void main(String[] args) {
        SpringApplication.run(ChatApplication.class, args);
    }

    @Override
    public void run(String... strings) throws Exception {
        try {
            tcpChatServer.start();
            webSocketChatServer.start();
            Thread.currentThread().join();
        } catch (Exception e) {
            LOG.error("startup error!", e);
        }
    }
}
