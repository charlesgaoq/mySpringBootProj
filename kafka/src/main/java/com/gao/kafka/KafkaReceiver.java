package com.gao.kafka;

import java.util.Optional;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaReceiver {
	
	private static Logger logger = LoggerFactory.getLogger(KafkaReceiver.class);
	
	@KafkaListener(topics = {"hello2"},group = "KafkaReceiver")
    public void listen(ConsumerRecord<?, ?> record) {
        Optional<?> kafkaMessage = Optional.ofNullable(record.value());
        if (kafkaMessage.isPresent()) {
            Object message = kafkaMessage.get();
            System.out.println("KafkaReceiver 消费");
            logger.info("----------------- record =" + record);
            logger.info("------------------ message =" + message);
        }

    }

	
}

