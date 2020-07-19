package com.gao.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class KafkaTestController {

	@Autowired
	private KfkaProducer producer;
	
	@RequestMapping("/testSendMsg")
	@ResponseBody
	public String testSendMsg(){
		producer.send();
		return "success";
	}
	
}
