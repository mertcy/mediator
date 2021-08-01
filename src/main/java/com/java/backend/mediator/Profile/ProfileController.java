package com.java.backend.mediator.Profile;

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
        return profileService.saveProfile(profile);
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public Profile getProfile(@PathVariable String id) {
        return profileService.findProfileByUserId(id);
    }
    
    @PostMapping(value = "/{id}/save-document", produces = "application/json")
    public Profile saveDocument(@PathVariable String id, @RequestBody Document document) {  	   	
    	return profileService.saveDocument(id, document);
    }
    
    @GetMapping("/")
    public String home() {
        return "Hello from Mediator App Service Profile Endpoint!";
    }
}
