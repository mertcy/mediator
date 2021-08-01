package com.java.backend.mediator.ContactInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContactInfoService implements IContactInfoService {

	private final ContactInfoRepository contactInfoRepository;
	
    @Autowired
    ContactInfoService(ContactInfoRepository contactInfoRepository) {
        this.contactInfoRepository = contactInfoRepository;
    } 
    
    @Override
    public ContactInfo findContactInfoByUserId(String id) {
        return contactInfoRepository.findContactInfoById(id);
    }
    
    @Override
    public ContactInfo saveContactInfo(ContactInfo contactInfo) {
    	if (findContactInfoByUserId(contactInfo.getId()) != null) {
    		return contactInfoRepository.save(contactInfo);
    	}
        return null;
    }
    
}
