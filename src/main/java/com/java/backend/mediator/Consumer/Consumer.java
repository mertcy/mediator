package com.java.backend.mediator.Consumer;

import org.springframework.data.annotation.Transient;

import com.java.backend.mediator.Model.Model;

public class Consumer extends Model {

	@Transient
	public static final String DISCRIMINATOR = "Consumer";
	
	public Consumer() {}
	
	public Consumer(String userId) {
		super.setId(userId);
	}
	
}
