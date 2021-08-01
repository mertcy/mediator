package com.java.backend.mediator.ContactInfo;

public interface IContactInfoService {
	ContactInfo findContactInfoByUserId(String id);
    ContactInfo saveContactInfo(ContactInfo contactInfo);
}
