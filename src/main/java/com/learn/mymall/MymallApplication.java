package com.learn.mymall;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@SpringBootApplication
@EnableElasticsearchRepositories(basePackages = "com.learn.mymall.elasticsearch.repository")
public class MymallApplication {

    public static void main(String[] args) {
        SpringApplication.run(MymallApplication.class, args);
    }

}
