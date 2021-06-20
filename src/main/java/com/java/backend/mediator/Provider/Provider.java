package com.java.backend.mediator.Provider;

import java.util.ArrayList;

import org.springframework.data.annotation.Transient;

import com.java.backend.mediator.Model.Model;
import com.java.backend.mediator.Rate.Rate;
import com.java.backend.mediator.ServiceProvided.ServiceProvided;

public class Provider extends Model {

	@Transient
	public static final String DISCRIMINATOR = "Provider";
	
	public ArrayList<ServiceProvided> servicesProvided;

	public ArrayList<Rate> ratings;
	
	public Provider() {}
	
	public Provider(String userId) {
		super.setId(userId);
		setServicesProvided(new ArrayList<ServiceProvided>());
		setRatings(new ArrayList<Rate>());
	}
	
	public ArrayList<ServiceProvided> getServicesProvided() {
		return servicesProvided;
	}

	public void setServicesProvided(ArrayList<ServiceProvided> servicesProvided) {
		this.servicesProvided = servicesProvided;
	}

	public ArrayList<Rate> getRatings() {
		return ratings;
	}

	public void setRatings(ArrayList<Rate> ratings) {
		this.ratings = ratings;
	}
	
}
