## 数据库配置
- 新增用户（canal/123456）：create user 'canal'@'%' identified by '123456';
- 赋予用户（canal）权限：GRANT SELECT, REPLICATION SLAVE, REPLICATION CLIENT ON *.* TO 'canal'@'%';
  FLUSH PRIVILEGES;
- 开启binlog配置:
  ```my.ini 
  [mysqld]
  # 打开binlog
  log-bin=mysql-bin
  # 选择ROW(行)模式
  binlog-format=ROW
  # 配置MySQL replaction需要定义，不要和canal的slaveId重复
  server_id=1
  ```
 - 重启后查看是否打开binlog,并查看当前binlog的position
   ```sql
   SHOW VARIABLES LIKE 'log_bin';
   SHOW MASTER STATUS;
   ```
  

## 下载安装canal
- 下载网址：https://github.com/alibaba/canal/releases （canal版本最好和pom依赖版本一致）
- 修改conf\example下的instance.properties配置文件
    ```properties
    canal.instance.master.position={SHOW MASTER STATUS查询到的position}
    canal.instance.master.address={127.0.0.1:3306数据库的ip和端口}
    canal.instance.dbUsername=canal
    canal.instance.dbPassword=123456
    ```
  
## 启动canal
- windows环境：启动bin/startup.bat
- 可能有报错，根据日志删除 -Dlogback.configurationFile="" -XX:PermSize=128m

## 启动java监听程序


