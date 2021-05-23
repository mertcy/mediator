package com.java.backend.mediator.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConsumerService {
	
	private final ConsumerRepository consumerRepository;
	
    @Autowired
    ConsumerService(ConsumerRepository consumerRepository) {
        this.consumerRepository = consumerRepository;
    }
		
    public Consumer findConsumerByUserId(String id) {
        return consumerRepository.findConsumerById(id);
    }

    public Consumer saveConsumer(Consumer consumer) {
        return consumerRepository.save(consumer);
    }

}
