package com.gao.boot.bootest;

import java.util.Enumeration;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Hello world!
 *
 */
@MapperScan(basePackages = "com.gao.boot.bootest")
@SpringBootApplication
@RestController
@RequestMapping(value = "test")
public class App implements CommandLineRunner
{
	@Autowired
	private MyTask myTask;
	
    public static void main( String[] args )
    {
        SpringApplication.run(App.class,args);
    }

    @RequestMapping(value = "req",method = RequestMethod.GET)
    public Object requestTest(HttpServletRequest request) {
    	if(!ObjectUtils.isEmpty(request)) {
    		System.out.println(request.getHeaderNames());
    		Enumeration<String> ems = request.getHeaderNames();
    		while(ems.hasMoreElements()) {
    			String name = ems.nextElement();
    			String value = request.getHeader(name);
    			System.out.println(name + "<>" + value);
    		}
    		
    		Cookie[] cookies = request.getCookies();
    		if (!ObjectUtils.isEmpty(cookies)) {
    			for (Cookie cookie : cookies) {
    				String cookieValue = cookie.getValue();
    				System.out.println(cookieValue);
    			}
    		}
    		
    	}
    	return "compleleted";
    }
    
    
    
	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		myTask.setName("gaoqiang");
		Thread thread = new Thread(myTask);
		thread.start();
	}
    
    
    
    
}
