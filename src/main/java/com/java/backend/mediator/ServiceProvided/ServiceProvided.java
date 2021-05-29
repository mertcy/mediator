package com.java.backend.mediator.ServiceProvided;

import org.springframework.data.annotation.Transient;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.java.backend.mediator.MediatorMessage.MediatorMessage;
import com.java.backend.mediator.Model.Model;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({ 
	@Type(value = CareService.class, name = CareService.DISCRIMINATOR), 
	@Type(value = DogWalkerService.class, name = DogWalkerService.DISCRIMINATOR),
	@Type(value = HouseCleaningService.class, name = HouseCleaningService.DISCRIMINATOR),
})
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
    
    public ServiceProvided(ServiceType serviceType) {
    	super();
    	this.serviceType = serviceType;
        this.serviceTitle = MediatorMessage.STATUS_NOTAVAILABLE;
        this.serviceDescription = MediatorMessage.STATUS_NOTAVAILABLE;
    }
    
	public enum ServiceType {
		CARE_SERVICE,
		DOG_WALKER_SERVICE,
		HOUSE_CLEANING_SERVICE		
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
