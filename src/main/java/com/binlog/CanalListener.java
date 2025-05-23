package com.binlog;

import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.client.CanalConnectors;
import com.alibaba.otter.canal.protocol.CanalEntry;
import com.alibaba.otter.canal.protocol.Message;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author si
 */
@Component
public class CanalListener implements DisposableBean {
    private final CanalConnector connector;
    private BufferedWriter writer;
    private String currentFileName;

    public CanalListener() {
        connector = CanalConnectors.newSingleConnector(new InetSocketAddress("127.0.0.1", 11111), "example", "", "");
        connector.connect();
        connector.subscribe(".*\\..*");
        connector.rollback();
    }

    @Async
    public void listen() {
        System.out.println("开始启动binlog-监听");
        while (true) {
            Message message = connector.getWithoutAck(500);
            List<CanalEntry.Entry> entries = message.getEntries();
            if (entries.isEmpty()) {
                continue;
            }

            try{
                BufferedWriter writer = getWriter();
                for (CanalEntry.Entry entry : entries) {
                    writer.write(entry.toString());
                    writer.newLine();
                }
                writer.flush();
            } catch (IOException e) {
                System.err.println("写入binglog异常,binlog信息:\n");
                for (CanalEntry.Entry entry : entries) {
                    System.err.println(entry.toString() + "\n");
                }
                e.printStackTrace();
            }

            connector.ack(message.getId());
        }
    }

    private BufferedWriter getWriter() throws IOException {
        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String fileName = "binlog-" + date + ".sql";

        if (writer == null || !fileName.equals(currentFileName)) {
            if (writer != null) {
                writer.close();
            }
            writer = new BufferedWriter(new FileWriter(fileName, true));
            currentFileName = fileName;
        }
        return writer;
    }

    @Override
    public void destroy() {
        try {
            if (writer != null) {
                writer.close();
            }
        } catch (IOException e) {
            System.err.println("关闭writer异常\n");
            e.printStackTrace();
        }
    }
}
