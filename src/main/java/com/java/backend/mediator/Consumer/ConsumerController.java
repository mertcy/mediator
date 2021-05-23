package com.java.backend.mediator.Consumer;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.java.backend.mediator.MediatorMessage.MediatorMessage;
import com.java.backend.mediator.Model.Model.Status;
import com.java.backend.mediator.ServiceProvided.ServiceProvided;

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
    	Consumer tempConsumer = null;
    	String userId =  consumer.getId();
    	   	
    	if((consumerService.findConsumerByUserId(userId) != null)) {     		
    		tempConsumer = consumerService.findConsumerByUserId(userId);
    		
        	if(tempConsumer.getStatus() == Status.ACTIVE) { // consumer already exists, so cannot be created
        		tempConsumer.setMessage(Consumer.DISCRIMINATOR + MediatorMessage.STATUS_ACTIVE + MediatorMessage.SO_MESSAGE + MediatorMessage.CRUD_FAILURE + MediatorMessage.CRUD_CREATE + MediatorMessage.END_MESSAGE);       	
        	} else if(tempConsumer.getStatus() == Status.INACTIVE) { // consumer is inactive, but same id cannot be used for saving
        		tempConsumer.setMessage(Consumer.DISCRIMINATOR + MediatorMessage.STATUS_INACTIVE + MediatorMessage.SO_MESSAGE + MediatorMessage.CRUD_FAILURE + MediatorMessage.CRUD_CREATE + MediatorMessage.END_MESSAGE);       	     
        	}  		
    	} else {
    		consumer.setMessage(Consumer.DISCRIMINATOR +  MediatorMessage.CRUD_SUCCESS + MediatorMessage.CRUD_CREATE + MediatorMessage.END_MESSAGE);        	    		    		
    		tempConsumer = consumerService.saveConsumer(consumer);     
    	}        
       
        return tempConsumer;		
    }
    
    @PutMapping(value = "/save", produces = "application/json")
    public Consumer saveConsumer(@RequestBody Consumer consumer) {
    	Consumer tempConsumer = consumerService.findConsumerByUserId(consumer.getId());
        if (tempConsumer != null) {     	
        	if(tempConsumer.getStatus() == Status.ACTIVE) { // consumer can be saved if it is active
        		consumer.setMessage(Consumer.DISCRIMINATOR +  MediatorMessage.CRUD_SUCCESS + MediatorMessage.CRUD_UPDATE + MediatorMessage.END_MESSAGE);
        		tempConsumer = consumerService.saveConsumer(consumer);
        	} else {
        		tempConsumer = new Consumer();
        		tempConsumer.clearModel();
        		tempConsumer.setMessage(Consumer.DISCRIMINATOR + MediatorMessage.STATUS_INACTIVE + MediatorMessage.SO_MESSAGE + MediatorMessage.CRUD_FAILURE + MediatorMessage.CRUD_UPDATE + MediatorMessage.END_MESSAGE);       	     
        	}
        } else {
        	tempConsumer = new Consumer();
        	tempConsumer.setMessage(Consumer.DISCRIMINATOR + MediatorMessage.STATUS_NOTAVAILABLE + MediatorMessage.SO_MESSAGE + MediatorMessage.CRUD_FAILURE + MediatorMessage.CRUD_UPDATE + MediatorMessage.END_MESSAGE);       	     
        }
        return tempConsumer;
    }
    

    @GetMapping(value = "/{id}", produces = "application/json")
    public Consumer getConsumer(@PathVariable String id) {
    	Consumer consumer = consumerService.findConsumerByUserId(id);
        return consumer;
    }
    
}
