package com.java.backend.mediator.ServiceProvided;

import org.springframework.data.annotation.Transient;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.java.backend.mediator.Model.Model;

public class ServiceProvided extends Model {

	@Transient
	public static final String DISCRIMINATOR = "ServiceProvided";

	@JsonFormat(shape = JsonFormat.Shape.STRING)
	@JsonProperty("serviceType")
	public ServiceType serviceType;
	
	@JsonProperty("serviceTitle")
	public String serviceTitle;
	
	@JsonProperty("serviceDescription")
	public String serviceDescription;
    
    public ServiceProvided() {
    	super();
    }
    
	enum ServiceType {
		CARE_SERVICE,
		HOUSE_CLEANING_SERVICE,
		DOG_WALKER_SERVICE
	}
	
	public ServiceType getServiceType() {
		return serviceType;
	}

	public void setServiceType(ServiceType serviceType) {
		this.serviceType = serviceType;
	}

	public String getServiceTitle() {
		return serviceTitle;
	}

	public void setServiceTitle(String serviceTitle) {
		this.serviceTitle = serviceTitle;
	}

	public String getServiceDescription() {
		return serviceDescription;
	}

	public void setServiceDescription(String serviceDescription) {
		this.serviceDescription = serviceDescription;
	}
	
}
