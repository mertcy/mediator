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
        return contactInfoService.saveContactInfo(contactInfo);
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public ContactInfo getContactInfo(@PathVariable String id) {
        return contactInfoService.findContactInfoByUserId(id);
    }
    
    @GetMapping("/")
    public String home() {
        return "Hello from Mediator App Service Contact Endpoint!";
    }
}