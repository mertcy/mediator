package com.java.backend.mediator.User;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.java.backend.mediator.Model.Model;

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
    
}
