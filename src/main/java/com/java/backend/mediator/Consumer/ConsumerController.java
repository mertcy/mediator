package com.java.backend.mediator.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/consumer")
@RestController
public class ConsumerController {
	
	private final ConsumerService consumerService;
	
    @Autowired
    public ConsumerController(ConsumerService consumerService) {
        this.consumerService = consumerService;
    }  
    
    @PostMapping(value = "/create", produces = "application/json")
    public Consumer createConsumer(@RequestBody Consumer consumer) {
        return consumerService.createConsumer(consumer);	
    }
    
    @PutMapping(value = "/save", produces = "application/json")
    public Consumer saveConsumer(@RequestBody Consumer consumer) {
        return consumerService.saveConsumer(consumer);
    }
    
    @GetMapping(value = "/{id}", produces = "application/json")
    public Consumer getConsumer(@PathVariable String id) {
    	Consumer consumer = consumerService.findConsumerByUserId(id);
        return consumer;
    }
    
    @GetMapping("/")
    public String home() {
        return "Hello from Mediator App Service Consumer Endpoint!";
    }
}
