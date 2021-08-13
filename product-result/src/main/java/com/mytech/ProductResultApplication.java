package com.mytech;

import com.mytech.config.DataSourceHealthConfig;
import org.apache.shardingsphere.shardingjdbc.spring.boot.SpringBootConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @author `<a href="mailto:qiang.wang@1020@gmail.com">qiang</a>`
 * @date 2021-08-08
 * @description :
 */
@SpringBootApplication(exclude = {

//        DataSourceAutoConfiguration.class,
//        SpringBootConfiguration.class, // This class file belongs to shardingsphere, in new versions it is renamed to ShardingSphereDataSourceAutoConfiguration

})
public class ProductResultApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProductResultApplication.class, args);
        System.out.println("Product Result Module start completed");
    }
}
