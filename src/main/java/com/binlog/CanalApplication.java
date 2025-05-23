package com.binlog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author: si
 * @create: 2025-05-22 17:10:39
 * @version: 1
 **/
@EnableAsync
@SpringBootApplication(scanBasePackages = {"com.binlog"})
public class CanalApplication {
    public static void main(String[] args) {
        SpringApplication.run(CanalApplication.class, args);
    }
}