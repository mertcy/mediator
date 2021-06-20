package com.java.backend.mediator.Provider;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java.backend.mediator.Model.Model.Status;
import com.java.backend.mediator.Rate.Rate;

@Service
public class ProviderService {
	
	private final ProviderRepository providerRepository;
	
    @Autowired
    ProviderService(ProviderRepository providerRepository) {
        this.providerRepository = providerRepository;
    }
		
    public Provider findProviderByUserId(String id) {
        return providerRepository.findProviderById(id);
    }

    public Provider saveProvider(Provider provider) {
        return providerRepository.save(provider);
    }
    
    public List<Provider> findProviders(List<String> ids){
    	return providerRepository.findByIdIn(ids);
    }
    
    public Provider rateProvider(String providerId, String consumerId, Integer rating) {  
    	   	
    	Rate rate = new Rate(providerId, consumerId, rating);
    	
    	Provider provider = this.findProviderByUserId(providerId);

    	ArrayList<Rate> ratings = provider.getRatings();
        if(ratings == null) {
        	ratings = new ArrayList<Rate>();
        } else {
        	for(Rate r : ratings) {        		
        		if(r.getConsumerId().equals(consumerId)) {
        			if(r.getStatus().equals(Status.ACTIVE)) {        				
            			r.setStatus(Status.INACTIVE);        				
        			}
        		}      		
        	}
        }        
		ratings.add(rate);       				
    	provider.setRatings(ratings);

    	return this.saveProvider(provider);
    }

}
