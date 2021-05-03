package com.java.backend.mediator.Provider;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProviderRepository extends MongoRepository<Provider, String> {
	Provider findProviderById(String id);
}
