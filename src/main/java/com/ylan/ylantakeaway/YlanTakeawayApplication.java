package com.ylan.ylantakeaway;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author ylan
 * 项目启动类
 */
@Slf4j

@ServletComponentScan          // 扫描Servlet
@EnableTransactionManagement   // 开启事务

@SpringBootApplication
public class YlanTakeawayApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(YlanTakeawayApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  ylanTakeaway启动成功   ლ(´ڡ`ლ)ﾞ  \n" +
                "   _  _     _                    \n" +
                "  | || |   | |    __ _    _ _    \n" +
                "   \\_, |   | |   / _` |  | ' \\   \n" +
                "  _|__/   _|_|_  \\__,_|  |_||_|  \n");
    }
}