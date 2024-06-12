package org.gyh.forestry;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@MapperScan(basePackages = "org.gyh.forestry.mapper", sqlSessionTemplateRef = "sqlSessionTemplate")
@EnableCaching
@SpringBootApplication
public class ForestryApplication {

    public static void main(String[] args) {
        SpringApplication.run(ForestryApplication.class, args);
    }

}
