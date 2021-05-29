package com.java.backend.mediator.User;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.java.backend.mediator.Consumer.Consumer;
import com.java.backend.mediator.ContactInfo.ContactInfo;
import com.java.backend.mediator.Model.Model;
import com.java.backend.mediator.Profile.Profile;
import com.java.backend.mediator.Provider.Provider;

import org.springframework.data.annotation.Transient;

public class User extends Model {

	@Transient
	public static final String DISCRIMINATOR = "User";
	
    @JsonFormat(shape = JsonFormat.Shape.STRING)
	@JsonProperty("userType")
    public UserType userType;

	@JsonProperty("userName") 
    public String userName;

    @JsonProperty("email") 
    public String email;

	@JsonProperty("password") 
    public String password;

	public ContactInfo contactInfo;
	
	public Profile profile;
	
	public Consumer consumer;
	
	public Provider provider;

	public User() {
		super();
	}

    public User(UserType userType, String userName, String email, String password) {
        super();
        this.userType = userType;
        this.userName = userName;
        this.email = email;
        this.password = password;
    }
    
	public enum UserType {
		CONSUMER,
		PROVIDER
	}
	
    public UserType getUserType() {
		return userType;
	}

	public void setUserType(UserType userType) {
		this.userType = userType;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
    public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public ContactInfo getContactInfo() {
		return contactInfo;
	}

	public void setContactInfo(ContactInfo contactInfo) {
		this.contactInfo = contactInfo;
	}
	
	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}

	public Consumer getConsumer() {
		return consumer;
	}

	public void setConsumer(Consumer consumer) {
		this.consumer = consumer;
	}

	public Provider getProvider() {
		return provider;
	}

	public void setProvider(Provider provider) {
		this.provider = provider;
	}
    
}
