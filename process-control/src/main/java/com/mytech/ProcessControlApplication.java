package com.mytech;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author `<a href="mailto:qiang.wang@1020@gmail.com">qiang</a>`
 * @date 2021-08-08
 * @description :
 */
@SpringBootApplication
@Configuration
//@EnableAutoConfiguration
//@ComponentScan
public class ProcessControlApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProcessControlApplication.class, args);
        System.out.println("Process Control Module start completed");
    }
}

