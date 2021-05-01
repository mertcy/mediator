package com.java.backend.mediator.ContactInfo;

import org.springframework.data.annotation.Transient;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.java.backend.mediator.MediatorMessage.MediatorMessage;
import com.java.backend.mediator.Model.Model;

public class ContactInfo extends Model {
 
	@Transient
	public static final String DISCRIMINATOR = "ContactInfo";
	
	@JsonProperty("name") 
    public String name;
    
    @JsonProperty("lastName") 
    public String lastName;

    @JsonProperty("birthDate")
    public String birthDate;
    
    @JsonProperty("gender") 
    public String gender;

    @JsonProperty("nationality") 
    public String nationality;

    @JsonProperty("telephoneNumber")
    public String telephoneNumber;
    
    @JsonProperty("address")
    public String address;      
    
    public ContactInfo() {}

    public ContactInfo(String userId) {     
        super.setId(userId);
        name = MediatorMessage.STATUS_NOTAVAILABLE;
        lastName = MediatorMessage.STATUS_NOTAVAILABLE;
        birthDate = MediatorMessage.STATUS_NOTAVAILABLE;
        gender = MediatorMessage.STATUS_NOTAVAILABLE;
        nationality = MediatorMessage.STATUS_NOTAVAILABLE;
        telephoneNumber = MediatorMessage.STATUS_NOTAVAILABLE;
        address = MediatorMessage.STATUS_NOTAVAILABLE;
    }

    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public String getTelephoneNumber() {
		return telephoneNumber;
	}

	public void setTelephoneNumber(String telephoneNumber) {
		this.telephoneNumber = telephoneNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

}