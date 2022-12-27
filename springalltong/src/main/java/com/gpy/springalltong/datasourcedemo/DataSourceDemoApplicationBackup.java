package com.gpy.springalltong.datasourcedemo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @ClassName DataSourceDemoApplicationBackup
 * @Description
 * @Author guopy
 * @Date 2022/12/27 16:25
 */
@SpringBootApplication
@Slf4j
public class DataSourceDemoApplicationBackup implements CommandLineRunner {
    @Autowired
    private DataSource dataSource;

    @Override
    public void run(String... args) throws Exception {
        showConnection();
    }

    private void showConnection() throws SQLException {
        log.info("初始化dataSource-->{}",dataSource.toString());
        Connection connection = dataSource.getConnection();
        log.info("数据库连接信息-->{}",connection.toString());
        //初始化dataSource-->HikariDataSource (HikariPool-1)
        //数据库连接信息-->HikariProxyConnection@94467365 wrapping conn0: url=jdbc:h2:mem:ab7aab97-dff0-43b6-8f6f-01baac145cad user=SA
        connection.close();
    }
}
