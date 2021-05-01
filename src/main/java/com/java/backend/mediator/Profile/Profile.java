package com.java.backend.mediator.Profile;

import java.util.ArrayList;

import org.springframework.data.annotation.Transient;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.java.backend.mediator.Document.Document;
import com.java.backend.mediator.MediatorMessage.MediatorMessage;
import com.java.backend.mediator.Model.Model;

public class Profile extends Model {	

	@Transient
	public static final String DISCRIMINATOR = "Profile";
	
	@JsonProperty("profileBio")
    String profileBio;
	
    public ArrayList<Document> documents;

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
    
    public ArrayList<Document> getDocuments() {
		return documents;
	}

	public void setDocuments(ArrayList<Document> list) {
		this.documents = list;
	}
	
}
