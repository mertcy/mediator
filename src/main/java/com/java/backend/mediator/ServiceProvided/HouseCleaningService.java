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
	
	@JsonProperty("totalRoomAmount")
    private BigDecimal totalRoomAmount;
	
	@JsonProperty("totalWindowAmount")
    private BigDecimal totalWindowAmount;
	
	@JsonProperty("totalWindowedRoomAmount")
    private BigDecimal totalWindowedRoomAmount;
	
	@JsonProperty("roomHeightCm")
    private BigDecimal roomHeightCm;

	@JsonProperty("containsPet")
	private boolean containsPet;
	
	@JsonProperty("district")
	private String district;	
    
    public HouseCleaningService() {
    	super(ServiceType.HOUSE_CLEANING_SERVICE);
    	totalAreaM2 = BigDecimal.ONE;
    	totalRoomAmount = BigDecimal.ZERO;
    	totalWindowAmount = BigDecimal.ZERO;
    	totalWindowedRoomAmount = BigDecimal.ZERO;
    	roomHeightCm = BigDecimal.ONE; 
    	containsPet = false;
    	district = MediatorMessage.STATUS_NOTAVAILABLE;
    }

    public BigDecimal getTotalAreaM2() {
		return totalAreaM2;
	}

	public void setTotalAreaM2(BigDecimal totalAreaM2) {
		this.totalAreaM2 = totalAreaM2;
	}

	public BigDecimal getTotalRoomAmount() {
		return totalRoomAmount;
	}

	public void setTotalRoomAmount(BigDecimal totalRoomAmount) {
		this.totalRoomAmount = totalRoomAmount;
	}

	public BigDecimal getTotalWindowAmount() {
		return totalWindowAmount;
	}

	public void setTotalWindowAmount(BigDecimal totalWindowAmount) {
		this.totalWindowAmount = totalWindowAmount;
	}

	public BigDecimal getTotalWindowedRoomAmount() {
		return totalWindowedRoomAmount;
	}

	public void setTotalWindowedRoomAmount(BigDecimal totalWindowedRoomAmount) {
		this.totalWindowedRoomAmount = totalWindowedRoomAmount;
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
