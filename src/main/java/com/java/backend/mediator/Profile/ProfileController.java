package com.java.backend.mediator.Profile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


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
    	Profile tempProfile = profileService.findProfileByUserId(profile.getUserId());
        if (tempProfile != null) {
        	tempProfile = profileService.saveProfile(profile);
        }
        return tempProfile;
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public Profile getProfile(@PathVariable String id) {
    	Profile profile = profileService.findProfileByUserId(id);
        return profile;
    }
    
}
