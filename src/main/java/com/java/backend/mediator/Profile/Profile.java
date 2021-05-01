package com.java.backend.mediator.Profile;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.java.backend.mediator.MediatorMessage.MediatorMessage;
import com.java.backend.mediator.Model.Model;

public class Profile extends Model {	

	@JsonProperty("userId")
    String userId;

	@JsonProperty("profileBio")
    String profileBio;
    
    public Profile() {}
    
    public Profile(String userId) {
        super.setId(userId);
        profileBio = MediatorMessage.STATUS_NOTAVAILABLE;
    }
    
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
    
    public String getProfileBio() {
		return profileBio;
	}

	public void setProfileBio(String profileBio) {
		this.profileBio = profileBio;
	}
    
}
