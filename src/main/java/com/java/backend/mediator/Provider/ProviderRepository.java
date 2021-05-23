package com.java.backend.mediator.Provider;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProviderRepository extends MongoRepository<Provider, String> {
	Provider findProviderById(String id);
	List<Provider> findByIdIn(List<String> ids);
}
