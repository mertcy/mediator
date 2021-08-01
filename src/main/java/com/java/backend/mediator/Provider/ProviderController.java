package com.java.backend.mediator.Provider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        return providerService.createProvider(provider);		
    }
    
    @PutMapping(value = "/save", produces = "application/json")
    public Provider saveProvider(@RequestBody Provider provider) {
        return providerService.saveProvider(provider);
    }   

    @GetMapping(value = "/{id}", produces = "application/json")
    public Provider getProvider(@PathVariable String id) {
    	return providerService.findProviderByUserId(id);
    }
    
    @PostMapping(value = "/{id}/save-service", produces = "application/json")
    public Provider saveServiceProvided(@PathVariable String id, @RequestBody ServiceProvided serviceProvided) {  	   	
    	return providerService.saveServiceProvided(id, serviceProvided);
    }
    
    @GetMapping("/")
    public String home() {
        return "Hello from Mediator App Service Provider Endpoint!";
    }
}
