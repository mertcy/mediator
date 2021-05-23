package com.java.backend.mediator.Provider;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
