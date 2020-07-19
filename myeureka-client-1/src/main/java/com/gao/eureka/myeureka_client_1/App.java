package com.gao.eureka.myeureka_client_1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;

/**
 * Hello world!
 *
 */
@Configuration
@ComponentScan
@EnableAutoConfiguration
@RestController
public class App 
{
    public static void main( String[] args )
    {
        SpringApplication.run(App.class,args);
    	System.out.println( "Hello World!" );
    }
}
