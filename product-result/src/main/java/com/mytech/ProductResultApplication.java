package com.mytech;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author `<a href="mailto:qiang.wang@1020@gmail.com">qiang</a>`
 * @date 2021-08-08
 * @description :
 */
@SpringBootApplication
public class ProductResultApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProductResultApplication.class, args);
        System.out.println("Product Result Module start completed");
    }
}
