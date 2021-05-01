package com.java.backend.mediator.ContactInfo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactInfoRepository extends MongoRepository<ContactInfo, String> {
	ContactInfo findContactInfoById(String id);
}
