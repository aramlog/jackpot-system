package com.sportygroup.jackpotsystem.reward.infrastructure;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients(basePackages = "com.sportygroup.jackpotsystem.core.infrastructure.store.client")
@SpringBootApplication(
        scanBasePackages = "com.sportygroup.jackpotsystem",
        exclude = {KafkaAutoConfiguration.class}
)
public class TestApplication {
    public static void main(String[] args) {
        SpringApplication.run(TestApplication.class, args);
    }
}

