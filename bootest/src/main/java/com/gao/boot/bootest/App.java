package com.gao.boot.bootest;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Hello world!
 *
 */
@MapperScan(basePackages = "com.gao.boot.bootest")
@SpringBootApplication
public class App implements CommandLineRunner
{
	@Autowired
	private MyTask myTask;
	
    public static void main( String[] args )
    {
        SpringApplication.run(App.class,args);
    }

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		myTask.setName("gaoqiang");
		Thread thread = new Thread(myTask);
		thread.start();
	}
    
    
    
    
}
