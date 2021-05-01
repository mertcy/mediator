package com.java.backend.mediator.ContactInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContactInfoService {

	private final ContactInfoRepository contactInfoRepository;
	
    @Autowired
    ContactInfoService(ContactInfoRepository contactInfoRepository) {
        this.contactInfoRepository = contactInfoRepository;
    } 
    
    public ContactInfo findContactInfoByUserId(String id) {
        return contactInfoRepository.findContactInfoById(id);
    }

    public ContactInfo saveContactInfo(ContactInfo contactInfo) {
        return contactInfoRepository.save(contactInfo);
    }
    
}
