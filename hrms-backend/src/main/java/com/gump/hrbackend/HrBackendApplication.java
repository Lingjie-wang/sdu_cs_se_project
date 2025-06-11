package com.gump.hrbackend;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.gump.hrbackend.mapper")
public class HrBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(HrBackendApplication.class, args);
    }

}
