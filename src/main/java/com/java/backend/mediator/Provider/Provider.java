package com.java.backend.mediator.Provider;

import org.springframework.data.annotation.Transient;

import com.java.backend.mediator.Model.Model;

public class Provider extends Model {

	@Transient
	public static final String DISCRIMINATOR = "Provider";
	
	public Provider() {}
	
	public Provider(String userId) {
		super.setId(userId);
	}
	
}
