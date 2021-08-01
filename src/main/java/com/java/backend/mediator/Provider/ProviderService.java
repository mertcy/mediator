package com.java.backend.mediator.Provider;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java.backend.mediator.MediatorMessage.MediatorMessage;
import com.java.backend.mediator.Model.Model.Status;
import com.java.backend.mediator.Rate.Rate;
import com.java.backend.mediator.ServiceProvided.CareService;
import com.java.backend.mediator.ServiceProvided.DogWalkerService;
import com.java.backend.mediator.ServiceProvided.ServiceProvided;
import com.java.backend.mediator.ServiceProvided.ServiceProvided.ServiceType;

@Service
public class ProviderService implements IProviderService{
	
	private final ProviderRepository providerRepository;
	
    @Autowired
    ProviderService(ProviderRepository providerRepository) {
        this.providerRepository = providerRepository;
    }
		
    @Override
    public Provider findProviderByUserId(String id) {
        return providerRepository.findProviderById(id);
    }

    @Override
    public Provider createProvider(Provider provider) {
    	Provider tempProvider = null;
    	String userId =  provider.getId();
    	   	
    	if((findProviderByUserId(userId) != null)) {     		
    		tempProvider = findProviderByUserId(userId);
    		
        	if(tempProvider.getStatus() == Status.ACTIVE) { // provider already exists, so cannot be created
        		tempProvider.setMessage(Provider.DISCRIMINATOR + MediatorMessage.STATUS_ACTIVE + MediatorMessage.SO_MESSAGE + MediatorMessage.CRUD_FAILURE + MediatorMessage.CRUD_CREATE + MediatorMessage.END_MESSAGE);       	
        	} else if(tempProvider.getStatus() == Status.INACTIVE) { // provider is inactive, but same id cannot be used for saving
        		tempProvider.setMessage(Provider.DISCRIMINATOR + MediatorMessage.STATUS_INACTIVE + MediatorMessage.SO_MESSAGE + MediatorMessage.CRUD_FAILURE + MediatorMessage.CRUD_CREATE + MediatorMessage.END_MESSAGE);       	     
        	}  		
    	} else {
    		provider.setMessage(Provider.DISCRIMINATOR +  MediatorMessage.CRUD_SUCCESS + MediatorMessage.CRUD_CREATE + MediatorMessage.END_MESSAGE);        	    		    		
    		tempProvider = saveProvider(provider);     
    	}        
       
        return tempProvider;		
    }
    
    @Override
    public Provider saveProvider(Provider provider) {
    	
    	Provider tempProvider = findProviderByUserId(provider.getId());
        if (tempProvider != null) {     	
        	if(tempProvider.getStatus() == Status.ACTIVE) { // provider can be saved if it is active
        		provider.setMessage(Provider.DISCRIMINATOR +  MediatorMessage.CRUD_SUCCESS + MediatorMessage.CRUD_UPDATE + MediatorMessage.END_MESSAGE);
        		tempProvider = providerRepository.save(provider);
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
    
    @Override
    public List<Provider> findProviders(List<String> ids){
    	return providerRepository.findByIdIn(ids);
    }
    
    @Override
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
        
        if(rate.getRating() == 0) {
        	rate.setStatus(Status.INACTIVE);
        }
        
		ratings.add(rate);       				
    	provider.setRatings(ratings);

    	return this.saveProvider(provider);
    }
    
    @Override
    public Integer getTotalOfRatersForProvider(String providerId) {   	
    	ArrayList<Rate> ratings = findProviderByUserId(providerId).getRatings();
    	   	
    	Integer counter = 0;
    	
        if(ratings != null) {
        	for(Rate r : ratings) {        		
        		if(r.getProviderId().equals(providerId)) {
        			if(r.getStatus().equals(Status.ACTIVE)) {        				
            			counter++;       				
        			}
        		}      		
        	}   
        }  
        
        return counter;    	
    }
    
    @Override
    public BigDecimal getTotalRatingForProvider(String providerId) {   	
    	ArrayList<Rate> ratings = findProviderByUserId(providerId).getRatings();
    	
    	Integer totalRatingSum = 0; 
    	
        if(ratings != null) {
        	for(Rate r : ratings) {        		
        		if(r.getProviderId().equals(providerId)) {
        			if(r.getStatus().equals(Status.ACTIVE)) {        				
        				totalRatingSum += r.getRating();		
        			}
        		}      		
        	}  
        } 
        
        return (getTotalOfRatersForProvider(providerId) == 0) ? BigDecimal.ZERO : 
        														BigDecimal.valueOf(1.0 * totalRatingSum / 
        																			getTotalOfRatersForProvider(providerId));    	    	
    }
    
    @Override
    public Integer getConsumerRatingForProvider(String providerId, String consumerId) {   	
    	ArrayList<Rate> ratings = findProviderByUserId(providerId).getRatings();
    	
    	Integer rate = 0;
    	
        if(ratings != null) {
        	for(Rate r : ratings) {        		
    			if(r.getStatus().equals(Status.ACTIVE) && r.getProviderId().equals(providerId) && r.getConsumerId().equals(consumerId)) {        				
    				rate = r.getRating();
    				break;
    			}    		
        	}  
        }
        
        return rate;
    }
   
    @Override
    public ServiceProvided getProvidedServiceByProviderId(String providerId) {   	
    	   	
    	ArrayList<ServiceProvided> servicesProvided = this.findProviderByUserId(providerId).getServicesProvided();
    	
    	ServiceProvided serviceProvided = new ServiceProvided();
    	
    	if(servicesProvided.size() == 1) {

			ServiceType serviceType = servicesProvided.get(0).getServiceType();  			
			switch(serviceType) {
				case CARE_SERVICE:
					serviceProvided = (CareService) servicesProvided.get(0);
					break;
				case DOG_WALKER_SERVICE:
					serviceProvided = (DogWalkerService) servicesProvided.get(0);
					break;
				case HOUSE_CLEANING_SERVICE:
					serviceProvided = (DogWalkerService) servicesProvided.get(0);
					break;
			}
	    				    		
    	}

        return serviceProvided;
    }
    
    @Override
    public Provider saveServiceProvided(String id, ServiceProvided serviceProvided) {
    	Provider provider = findProviderByUserId(id);
    	ArrayList<ServiceProvided> servicesProvided = provider.getServicesProvided();
        if (servicesProvided == null) {
        	servicesProvided = new ArrayList<ServiceProvided>();
        }
    	servicesProvided.add(serviceProvided);
    	provider.setServicesProvided(servicesProvided);

    	return saveProvider(provider);	
    }

}
