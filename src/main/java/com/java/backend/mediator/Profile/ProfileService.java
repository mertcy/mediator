package com.java.backend.mediator.Profile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java.backend.mediator.User.User;

@Service
public class ProfileService {

	private final ProfileRepository profileRepository;
	
    @Autowired
    ProfileService(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }
		
    public Profile findProfileByUserId(String id) {
        return profileRepository.findProfileById(id);
    }

    public Profile saveProfile(Profile profile) {
        return profileRepository.save(profile);
    }
    
}
