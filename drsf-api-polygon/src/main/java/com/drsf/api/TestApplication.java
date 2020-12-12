package com.drsf.api;

import com.drsf.api.repository.DividendRepositoryTest;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
//@EnableJpaRepositories("org.springframework.data.jpa.repository.support")
public class TestApplication {


    public static void main(String[] args) {
        SpringApplication.run(TestApplication.class,args);
    }

    @Bean
    public String demo(DividendRepositoryTest repository) {
            return null;
    }


}
