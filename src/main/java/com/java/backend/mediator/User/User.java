package com.java.backend.mediator.User;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.springframework.data.annotation.Transient;
import org.springframework.data.annotation.Id;

public class User {
	
    @Id
    @JsonProperty("id")
    public String userId;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
	@JsonProperty("userType")
    public UserType userType;

	@JsonProperty("userName") 
    public String userName;

    @JsonProperty("email") 
    public String email;

	@JsonProperty("password") 
    public String password;

	@JsonProperty("status") 
    public int status;
	
	@Transient
	public static final String DISCRIMINATOR = "User";
	
	@Transient
	public String message;

	public User() {}

    public User(String userId, UserType userType, String userName, String email, String password, int status) {
        this.userId = userId;
        this.userType = userType;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.status = status;
    }
    
	public enum UserType {
		CONSUMER,
		PROVIDER
	}
    
    public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
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
	
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
    public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
    
}
