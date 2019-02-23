package com.ljc.maitu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan("com.ljc.maitu.mapper")
public class MaituApplication {

    public static void main(String[] args) {
        SpringApplication.run(MaituApplication.class, args);
    }

}

