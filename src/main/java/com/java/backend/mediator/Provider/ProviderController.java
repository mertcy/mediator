package com.java.backend.mediator.Provider;

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

@RequestMapping("/api/provider")
@RestController
public class ProviderController {
	
	private final ProviderService providerService;
	
    @Autowired
    public ProviderController(ProviderService providerService) {
        this.providerService = providerService;
    }  
    
    @PostMapping(value = "/create", produces = "application/json")
    public Provider createProvider(@RequestBody Provider provider) {
    	Provider tempProvider = null;
    	String userId =  provider.getId();
    	   	
    	if((providerService.findProviderByUserId(userId) != null)) {     		
    		tempProvider = providerService.findProviderByUserId(userId);
    		
        	if(tempProvider.getStatus() == Status.ACTIVE) { // provider already exists, so cannot be created
        		tempProvider.setMessage(Provider.DISCRIMINATOR + MediatorMessage.STATUS_ACTIVE + MediatorMessage.SO_MESSAGE + MediatorMessage.CRUD_FAILURE + MediatorMessage.CRUD_CREATE + MediatorMessage.END_MESSAGE);       	
        	} else if(tempProvider.getStatus() == Status.INACTIVE) { // provider is inactive, but same id cannot be used for saving
        		tempProvider.setMessage(Provider.DISCRIMINATOR + MediatorMessage.STATUS_INACTIVE + MediatorMessage.SO_MESSAGE + MediatorMessage.CRUD_FAILURE + MediatorMessage.CRUD_CREATE + MediatorMessage.END_MESSAGE);       	     
        	}  		
    	} else {
    		provider.setMessage(Provider.DISCRIMINATOR +  MediatorMessage.CRUD_SUCCESS + MediatorMessage.CRUD_CREATE + MediatorMessage.END_MESSAGE);        	    		    		
    		tempProvider = providerService.saveProvider(provider);     
    	}        
       
        return tempProvider;		
    }
    
    @PutMapping(value = "/save", produces = "application/json")
    public Provider saveProvider(@RequestBody Provider provider) {
    	Provider tempProvider = providerService.findProviderByUserId(provider.getId());
        if (tempProvider != null) {     	
        	if(tempProvider.getStatus() == Status.ACTIVE) { // provider can be saved if it is active
        		provider.setMessage(Provider.DISCRIMINATOR +  MediatorMessage.CRUD_SUCCESS + MediatorMessage.CRUD_UPDATE + MediatorMessage.END_MESSAGE);
        		tempProvider = providerService.saveProvider(provider);
        	} else {
        		tempProvider = new Provider();
        		tempProvider.clearModel();
        		tempProvider.setMessage(Provider.DISCRIMINATOR + MediatorMessage.STATUS_INACTIVE + MediatorMessage.SO_MESSAGE + MediatorMessage.CRUD_FAILURE + MediatorMessage.CRUD_UPDATE + MediatorMessage.END_MESSAGE);       	     
        	}
        } else {
        	tempProvider = new Provider();
        	tempProvider.setMessage(Provider.DISCRIMINATOR + MediatorMessage.STATUS_NOTAVAILABLE + MediatorMessage.SO_MESSAGE + MediatorMessage.CRUD_FAILURE + MediatorMessage.CRUD_UPDATE + MediatorMessage.END_MESSAGE);       	     
        }
        return tempProvider;
    }
    

    @GetMapping(value = "/{id}", produces = "application/json")
    public Provider getProvider(@PathVariable String id) {
    	Provider provider = providerService.findProviderByUserId(id);
        return provider;
    }
    
    @PostMapping(value = "/{id}/save-service", produces = "application/json")
    public Provider saveServiceProvided(@PathVariable String id, @RequestBody ServiceProvided serviceProvided) {  	   	
    	Provider provider = providerService.findProviderByUserId(id);
    	ArrayList<ServiceProvided> servicesProvided = provider.getServicesProvided();
        if (servicesProvided == null) {
        	servicesProvided = new ArrayList<ServiceProvided>();
        }
    	servicesProvided.add(serviceProvided);
    	provider.setServicesProvided(servicesProvided);

    	return providerService.saveProvider(provider);
    }
}
