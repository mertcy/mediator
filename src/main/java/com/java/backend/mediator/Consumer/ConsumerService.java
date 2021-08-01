package com.java.backend.mediator.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java.backend.mediator.MediatorMessage.MediatorMessage;
import com.java.backend.mediator.Model.Model.Status;

@Service
public class ConsumerService implements IConsumerService {
	
	private final ConsumerRepository consumerRepository;
	
    @Autowired
    ConsumerService(ConsumerRepository consumerRepository) {
        this.consumerRepository = consumerRepository;
    }
		
    @Override
    public Consumer findConsumerByUserId(String id) {
        return consumerRepository.findConsumerById(id);
    }

    @Override
    public Consumer createConsumer(Consumer consumer) {
    	Consumer tempConsumer = null;
    	String userId =  consumer.getId();
    	   	
    	if((findConsumerByUserId(userId) != null)) {     		
    		tempConsumer = findConsumerByUserId(userId);
    		
        	if(tempConsumer.getStatus() == Status.ACTIVE) { // consumer already exists, so cannot be created
        		tempConsumer.setMessage(Consumer.DISCRIMINATOR + MediatorMessage.STATUS_ACTIVE + MediatorMessage.SO_MESSAGE + MediatorMessage.CRUD_FAILURE + MediatorMessage.CRUD_CREATE + MediatorMessage.END_MESSAGE);       	
        	} else if(tempConsumer.getStatus() == Status.INACTIVE) { // consumer is inactive, but same id cannot be used for saving
        		tempConsumer.setMessage(Consumer.DISCRIMINATOR + MediatorMessage.STATUS_INACTIVE + MediatorMessage.SO_MESSAGE + MediatorMessage.CRUD_FAILURE + MediatorMessage.CRUD_CREATE + MediatorMessage.END_MESSAGE);       	     
        	}  		
    	} else {
    		consumer.setMessage(Consumer.DISCRIMINATOR +  MediatorMessage.CRUD_SUCCESS + MediatorMessage.CRUD_CREATE + MediatorMessage.END_MESSAGE);        	    		    		
    		tempConsumer = saveConsumer(consumer);     
    	}        
       
        return tempConsumer;		
    }
    
    @Override
    public Consumer saveConsumer(Consumer consumer) {
    	Consumer tempConsumer = findConsumerByUserId(consumer.getId());
        if (tempConsumer != null) {     	
        	if(tempConsumer.getStatus() == Status.ACTIVE) { // consumer can be saved if it is active
        		consumer.setMessage(Consumer.DISCRIMINATOR +  MediatorMessage.CRUD_SUCCESS + MediatorMessage.CRUD_UPDATE + MediatorMessage.END_MESSAGE);
        		tempConsumer = consumerRepository.save(consumer);
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

}
