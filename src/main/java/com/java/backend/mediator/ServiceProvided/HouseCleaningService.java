package com.java.backend.mediator.ServiceProvided;

import java.math.BigDecimal;

import org.springframework.data.annotation.Transient;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.java.backend.mediator.MediatorMessage.MediatorMessage;

public class HouseCleaningService extends ServiceProvided {
	
	@Transient
	public static final String DISCRIMINATOR = "HouseCleaningService";
	
	@JsonProperty("totalAreaM2")
    private BigDecimal totalAreaM2;
	
	@JsonProperty("maxRoomNumber")
    private BigDecimal maxRoomNumber;
	
	@JsonProperty("maxWindowNumber")
    private BigDecimal maxWindowNumber;
	
	@JsonProperty("roomHeightCm")
    private BigDecimal roomHeightCm;

	@JsonProperty("containsPet")
	private boolean containsPet;
	
	@JsonProperty("district")
	private String district;	
    
    public HouseCleaningService() {
    	super(ServiceType.HOUSE_CLEANING_SERVICE);
    	totalAreaM2 = BigDecimal.ONE;
    	maxRoomNumber = BigDecimal.ZERO;
    	maxWindowNumber = BigDecimal.ZERO;
    	roomHeightCm = BigDecimal.ONE; 
    	containsPet = false;
    	district = MediatorMessage.STATUS_NOTAVAILABLE;
    }
    
    public HouseCleaningService(BigDecimal totalAreaM2, BigDecimal maxRoomNumber, BigDecimal maxWindowNumber, 
    									BigDecimal roomHeightCm, boolean containsPet, String district ) {
    	super(ServiceType.HOUSE_CLEANING_SERVICE);
    	this.totalAreaM2 = totalAreaM2;
    	this.maxRoomNumber = maxRoomNumber;
    	this.maxWindowNumber = maxWindowNumber;
    	this.roomHeightCm = roomHeightCm; 
    	this.containsPet = containsPet;
    	this.district = district;
    }

    public BigDecimal getTotalAreaM2() {
		return totalAreaM2;
	}

	public void setTotalAreaM2(BigDecimal totalAreaM2) {
		this.totalAreaM2 = totalAreaM2;
	}

	public BigDecimal getMaxRoomNumber() {
		return maxRoomNumber;
	}

	public void setMaxRoomNumber(BigDecimal maxRoomNumber) {
		this.maxRoomNumber = maxRoomNumber;
	}

	public BigDecimal getMaxWindowNumber() {
		return maxWindowNumber;
	}

	public void setMaxWindowNumber(BigDecimal maxWindowNumber) {
		this.maxWindowNumber = maxWindowNumber;
	}

	public BigDecimal getRoomHeightCm() {
		return roomHeightCm;
	}

	public void setRoomHeightCm(BigDecimal roomHeightCm) {
		this.roomHeightCm = roomHeightCm;
	}
	
	public boolean isContainsPet() {
		return containsPet;
	}

	public void setContainsPet(boolean containsPet) {
		this.containsPet = containsPet;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

}
