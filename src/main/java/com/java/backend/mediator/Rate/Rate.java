package com.java.backend.mediator.Rate;

import org.springframework.data.annotation.Transient;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.java.backend.mediator.Model.Model;

public class Rate extends Model {

	@Transient
	public static final String DISCRIMINATOR = "Rate";

	@JsonProperty("providerId") 
    public String providerId;

    @JsonProperty("consumerId") 
    public String consumerId;

	@JsonProperty("rating") 
    public Integer rating;
	
    public Rate() {
    	super();
    }

    public Rate(String providerId, String consumerId, Integer rating) {
        super();
        this.providerId = providerId;
        this.consumerId = consumerId;
        this.rating = rating;
    }

	public String getProviderId() {
		return providerId;
	}

	public void setProviderId(String providerId) {
		this.providerId = providerId;
	}

	public String getConsumerId() {
		return consumerId;
	}

	public void setConsumerId(String consumerId) {
		this.consumerId = consumerId;
	}

	public Integer getRating() {
		return rating;
	}

	public void setRating(Integer rating) {
		this.rating = rating;
	}

}