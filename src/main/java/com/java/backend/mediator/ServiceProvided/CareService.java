package com.java.backend.mediator.ServiceProvided;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CareService extends ServiceProvided {
    
	@JsonProperty("caredAge")
	private BigDecimal caredAge;

	@JsonFormat(shape = JsonFormat.Shape.STRING)
	@JsonProperty("caredType")
	public CaredType caredType;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	@JsonProperty("caredPlace")
	public CaredPlace caredPlace;
	
	public enum CaredType {
		NOT_AVAILABLE,
		INFANT,
		ADULT,
		OLD
	}
	   	
	public enum CaredPlace {
		NOT_AVAILABLE,
		NURSING_HOME,
		DESCENDANT_HOUSE,
		OWN_HOUSE
	}

    public CareService() {
    	super(ServiceType.CARE_SERVICE);
        this.caredAge = BigDecimal.ZERO;
        this.caredPlace = CaredPlace.NOT_AVAILABLE;
        this.caredType = CaredType.NOT_AVAILABLE;
    }
    
	public BigDecimal getCaredAge() {
		return caredAge;
	}

	public void setCaredAge(BigDecimal caredAge) {
		this.caredAge = caredAge;
	}

	public CaredType getCaredType() {
		return caredType;
	}

	public void setCaredType(CaredType caredType) {
		this.caredType = caredType;
	}

	public CaredPlace getCaredPlace() {
		return caredPlace;
	}

	public void setCaredPlace(CaredPlace caredPlace) {
		this.caredPlace = caredPlace;
	}

}
