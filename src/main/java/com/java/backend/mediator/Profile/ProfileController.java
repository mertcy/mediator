package com.java.backend.mediator.Profile;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.java.backend.mediator.Document.Document;


@RequestMapping("/api/profile")
@RestController
public class ProfileController {

	private final ProfileService profileService;
	
    @Autowired
    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }
    
    @PutMapping(value = "/save", produces = "application/json")
    public Profile saveProfile(@RequestBody Profile profile) {
    	Profile tempProfile = profileService.findProfileByUserId(profile.getId());
        if (tempProfile != null) {
        	profile.setDocuments(new ArrayList<Document>());
        	tempProfile = profileService.saveProfile(profile);
        }
 
        return tempProfile;
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public Profile getProfile(@PathVariable String id) {
    	Profile profile = profileService.findProfileByUserId(id);
        return profile;
    }
    
    @PostMapping(value = "/{id}/save-document", produces = "application/json")
    public Profile saveDocument(@PathVariable String id, @RequestBody Document document) {  	
    	Document tempDocument = new Document();
    	tempDocument.setDocumentType(document.getDocumentType());
    	tempDocument.setDocumentTitle(document.getDocumentTitle());
    	tempDocument.setDocumentDescription(document.getDocumentDescription());
    	tempDocument.setDocumentLink(document.getDocumentLink());
    	
    	Profile profile = profileService.findProfileByUserId(id);
    	ArrayList<Document> documents = profile.getDocuments(); 
        if (documents == null) {
        	documents = new ArrayList<Document>();
        }
    	documents.add(tempDocument);
    	profile.setDocuments(documents);

    	return profileService.saveProfile(profile);
    }
    
}
