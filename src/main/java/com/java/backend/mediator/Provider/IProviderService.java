package com.java.backend.mediator.Provider;

import java.math.BigDecimal;
import java.util.List;

import com.java.backend.mediator.ServiceProvided.ServiceProvided;

public interface IProviderService {
	Provider findProviderByUserId(String id);
    Provider createProvider(Provider provider);
    Provider saveProvider(Provider provider);
    List<Provider> findProviders(List<String> ids);
    Provider rateProvider(String providerId, String consumerId, Integer rating);
    Integer getTotalOfRatersForProvider(String providerId);
    BigDecimal getTotalRatingForProvider(String providerId);
    Integer getConsumerRatingForProvider(String providerId, String consumerId);
    ServiceProvided getProvidedServiceByProviderId(String providerId);
    Provider saveServiceProvided(String id, ServiceProvided serviceProvided);
}
