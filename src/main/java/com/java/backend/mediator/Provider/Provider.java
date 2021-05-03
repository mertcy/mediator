package com.java.backend.mediator.Provider;

import java.util.ArrayList;

import org.springframework.data.annotation.Transient;

import com.java.backend.mediator.Model.Model;
import com.java.backend.mediator.ServiceProvided.ServiceProvided;

public class Provider extends Model {

	@Transient
	public static final String DISCRIMINATOR = "Provider";
	
	public ArrayList<ServiceProvided> servicesProvided;

	public Provider() {}
	
	public Provider(String userId) {
		super.setId(userId);
		setServicesProvided(new ArrayList<ServiceProvided>());
	}
	
	public ArrayList<ServiceProvided> getServicesProvided() {
		return servicesProvided;
	}

	public void setServicesProvided(ArrayList<ServiceProvided> servicesProvided) {
		this.servicesProvided = servicesProvided;
	}
	
}
