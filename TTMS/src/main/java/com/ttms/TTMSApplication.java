package com.ttms;



import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan("com.ttms.Mapper")
public class TTMSApplication {
    public static void main(String[] args) {
        SpringApplication.run(TTMSApplication.class,args);
    }
}
