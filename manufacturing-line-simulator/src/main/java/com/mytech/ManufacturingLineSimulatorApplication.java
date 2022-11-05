package com.mytech;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author `<a href="mailto:qiang.wang@1020@gmail.com">qiang</a>`
 * @date 2021-08-11
 * @description :
 */
@SpringBootApplication
public class ManufacturingLineSimulatorApplication {
    public static void main(String[] args) {
        SpringApplication.run(com.mytech.ProcessControlApplication.class, args);
        System.out.println("Manufacturing Line SimulatorApplication Module start completed");
    }//main
}//class

