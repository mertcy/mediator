package com.java.backend.mediator.Profile;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java.backend.mediator.Document.Document;

@Service
public class ProfileService implements IProfileService {

	private final ProfileRepository profileRepository;
	
    @Autowired
    ProfileService(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    @Override
    public Profile findProfileByUserId(String id) {
        return profileRepository.findProfileById(id);
    }

    @Override    
    public Profile saveProfile(Profile profile) {        
    	Profile tempProfile = findProfileByUserId(profile.getId());
        if (tempProfile != null) {
        	profile.setDocuments(new ArrayList<Document>());
        	return profileRepository.save(profile);
        }
        
        return null;
    }
    
    @Override
    public Profile saveDocument(String id, Document document) {  	   	
    	Profile profile = findProfileByUserId(id);
    	ArrayList<Document> documents = profile.getDocuments(); 
        if (documents == null) {
        	documents = new ArrayList<Document>();
        }
    	documents.add(document);
    	profile.setDocuments(documents);

    	return saveProfile(profile);
    }
    
}
