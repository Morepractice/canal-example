package withub.binlog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @file_name: ${NAME}
 * @description: ${description}
 * @author: siHua
 * @create: 2025-05-22 17:10:39
 * @version: ${VERSION}
 **/
@EnableAsync
@SpringBootApplication(scanBasePackages = {"withub.binlog"})
public class CanalApplication {
    public static void main(String[] args) {
        SpringApplication.run(CanalApplication.class, args);
    }
}