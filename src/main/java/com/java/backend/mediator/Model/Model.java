package com.java.backend.mediator.Model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.java.backend.mediator.Utility.Utility;

import org.springframework.data.annotation.Transient;
import org.springframework.data.annotation.Id;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@JsonPropertyOrder({ "message", "id", "status", "lastUpdater", "lastUpdated" })
public class Model {
	
	@Transient
	private String message;
	
	@Id
    @JsonProperty("id")
	private String id;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonProperty("status")
	private Status status;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonProperty("lastUpdater")
	private Updater lastUpdater;
	
	@Temporal (TemporalType.TIMESTAMP)
    @JsonProperty("lastUpdated")
	private Date lastUpdated;

	public enum Status {
		ACTIVE,
		INACTIVE,
		NOTAVAILABLE;
	}
	
	public enum Updater {
		ADMIN,
		OTHER,
		NOTAVAILABLE;
	}
	
	public Model() {
		if(this.isEmptyId()) {
			this.setId(Utility.randomString(11));
		}
		status = Status.ACTIVE;
		lastUpdater = Updater.ADMIN;
		lastUpdated = new Date();
	}
	
    public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public String getId() {		
		return id;
	}
	
	@JsonIgnore
	private boolean isEmptyId() {		
    	if(this.getId() == null || this.getId().length() == 0) {
    		return true;
    	} else {
    		return false;
    	}		
	}
	
	@JsonIgnore
	public void clearModel() {
		this.setId(null);
		this.setStatus(null);
		this.setLastUpdater(null);
		this.setLastUpdated(null);
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public Status getStatus() {
		return status;
	}
	
	public void setStatus(Status status) {
		this.status = status;
	}
	
	public Updater getLastUpdater() {
		return lastUpdater;
	}

	public void setLastUpdater(Updater lastUpdater) {
		this.lastUpdater = lastUpdater;
	}
	
	public Date getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}
	
}
