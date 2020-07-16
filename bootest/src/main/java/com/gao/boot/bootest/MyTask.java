package com.gao.boot.bootest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MyTask implements Runnable {
	
	private String name;
	
	

	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	@Autowired
	private UserMapper userMapper;
	

	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println(name);
		User user = userMapper.selectByPrimaryKey(1);
		System.out.println(user.getUseName());
	}

}
