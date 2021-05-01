package com.java.backend.mediator.ContactInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RequestMapping("/api/contact")
@RestController


public class ContactInfoController {

    private final ContactInfoService contactInfoService;
	
    @Autowired
    public ContactInfoController(ContactInfoService contactInfoService) {
        this.contactInfoService = contactInfoService;
    }
    
    @PutMapping(value = "/save", produces = "application/json")
    public ContactInfo saveContactInfo(@RequestBody ContactInfo contactInfo) {
    	ContactInfo tempContactInfo = contactInfoService.findContactInfoByUserId(contactInfo.getId());
    	if (tempContactInfo != null) {
    		tempContactInfo = contactInfoService.saveContactInfo(contactInfo);
    	}

        return tempContactInfo;
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public ContactInfo getContactInfo(@PathVariable String id) {
    	ContactInfo contactInfo = contactInfoService.findContactInfoByUserId(id);
        return contactInfo;
    }
    
}