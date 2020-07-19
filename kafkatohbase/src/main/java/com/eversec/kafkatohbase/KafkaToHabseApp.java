package com.eversec.kafkatohbase;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class KafkaToHabseApp 
{
    public static void main( String[] args )
    {
        SpringApplication.run(KafkaToHabseApp.class, args);
    }
}
