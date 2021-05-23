package com.java.backend.mediator.Consumer;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsumerRepository extends MongoRepository<Consumer, String> {
	Consumer findConsumerById(String id);
}
