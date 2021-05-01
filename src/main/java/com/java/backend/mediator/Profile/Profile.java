package com.java.backend.mediator.Profile;

import org.springframework.data.annotation.Transient;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.java.backend.mediator.MediatorMessage.MediatorMessage;
import com.java.backend.mediator.Model.Model;

public class Profile extends Model {	

	@Transient
	public static final String DISCRIMINATOR = "Profile";
	
	@JsonProperty("profileBio")
    String profileBio;
    
    public Profile() {}
    
    public Profile(String userId) {
        super.setId(userId);
        profileBio = MediatorMessage.STATUS_NOTAVAILABLE;
    }
    
    public String getProfileBio() {
		return profileBio;
	}

	public void setProfileBio(String profileBio) {
		this.profileBio = profileBio;
	}
    
}
