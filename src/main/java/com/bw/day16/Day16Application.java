package com.bw.day16;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan("com.bw.day16.mapper")
public class Day16Application {
    public static void main(String[] args) {

        SpringApplication.run(Day16Application.class, args);
    }
}

