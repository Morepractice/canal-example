package com.binlog;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @file_name: ListenRunner
 * @description: 启动binlog
 * @author: siHua
 * @create: 2025-05-23 14:04:38
 * @version: 1
 **/
@Component
public class ListenRunner implements CommandLineRunner {
    private final CanalListener listener;

    public ListenRunner(CanalListener listener) {
        this.listener = listener;
    }

    @Override
    public void run(String... args) {
        listener.listen();
    }
}
