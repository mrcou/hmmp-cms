package com.hmmp;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.jdbc.autoconfigure.DataSourceAutoConfiguration;

/**
 * 启动程序
 */
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class HmmpApplication
{
    public static void main(String[] args)
    {
        // banner.txt 中的 ${application.start-time} 需在 Environment 中提前注入
        System.setProperty("application.start-time",
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        // System.setProperty("spring.devtools.restart.enabled", "false");
        SpringApplication.run(HmmpApplication.class, args);
    }
}
