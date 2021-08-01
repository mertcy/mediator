package com.java.backend.mediator.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.java.backend.mediator.Consumer.Consumer;
import com.java.backend.mediator.Consumer.ConsumerService;
import com.java.backend.mediator.ContactInfo.ContactInfo;
import com.java.backend.mediator.ContactInfo.ContactInfoService;
import com.java.backend.mediator.MediatorMessage.MediatorMessage;
import com.java.backend.mediator.Model.Model.Status;
import com.java.backend.mediator.Profile.Profile;
import com.java.backend.mediator.Profile.ProfileService;
import com.java.backend.mediator.Provider.Provider;
import com.java.backend.mediator.Provider.ProviderService;
import com.java.backend.mediator.Utility.Utility;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService implements IUserService {

    private final UserRepository userRepository;
	private final ProfileService profileService;
	private final ContactInfoService contactInfoService;
	private final ProviderService providerService;
	private final ConsumerService consumerService;
	
    @Autowired
    UserService(UserRepository userRepository, ProfileService profileService, ContactInfoService contactInfoService, ProviderService providerService, ConsumerService consumerService) {
        this.userRepository = userRepository;
        this.profileService = profileService;
        this.contactInfoService = contactInfoService;
        this.providerService = providerService;
        this.consumerService = consumerService;    
    }

    @Override
    public User findUserByUserId(String id) {    	
        User user = userRepository.findUserById(id);
        
        return ((user != null) && (user.getStatus() == Status.ACTIVE)) ? user : null;    	
    }

    @Override
    public List<User> getAllUsers() {  	  	
    	List<User> allUsers = userRepository.findAll();  	
    	List<User> activeUsers = new ArrayList<User>();
    	
    	for(User user: allUsers) {
    		if(user != null) {
        		if(user.getStatus() == Status.ACTIVE) {
                	user.setMessage(User.DISCRIMINATOR +  MediatorMessage.CRUD_SUCCESS + MediatorMessage.CRUD_READ + MediatorMessage.END_MESSAGE);       	
        			activeUsers.add(user);
        		}
    		}
    	}
    	
        return activeUsers;
    }

    @Override
    public User saveUser(User user) {
    	
        User tempUser = findUserByUserId(user.getId());
        if (tempUser != null) {     	
        	if(tempUser.getStatus() == Status.ACTIVE) { // user can be saved if it is active
        		user.setMessage(User.DISCRIMINATOR +  MediatorMessage.CRUD_SUCCESS + MediatorMessage.CRUD_UPDATE + MediatorMessage.END_MESSAGE);
            	tempUser = userRepository.save(user);
        	} else {
        		tempUser = new User();
        		tempUser.clearModel();
        		tempUser.setMessage(User.DISCRIMINATOR + MediatorMessage.STATUS_INACTIVE + MediatorMessage.SO_MESSAGE + MediatorMessage.CRUD_FAILURE + MediatorMessage.CRUD_UPDATE + MediatorMessage.END_MESSAGE);       	     
        	}
        } else {
        	tempUser = new User();
    		tempUser.setMessage(User.DISCRIMINATOR + MediatorMessage.STATUS_NOTAVAILABLE + MediatorMessage.SO_MESSAGE + MediatorMessage.CRUD_FAILURE + MediatorMessage.CRUD_UPDATE + MediatorMessage.END_MESSAGE);       	     
        }
        return tempUser;    	
    }
    
    @Override
    public User createUser(User user) {
    	User tempUser = null;
    	String userId =  user.getId();
    	   	
    	if((findUserByUserId(userId) != null)) {     		
    		tempUser = findUserByUserId(userId);
    		
        	if(tempUser.getStatus() == Status.ACTIVE) { // user already exists, so cannot be created
        		tempUser.setMessage(User.DISCRIMINATOR + MediatorMessage.STATUS_ACTIVE + MediatorMessage.SO_MESSAGE + MediatorMessage.CRUD_FAILURE + MediatorMessage.CRUD_CREATE + MediatorMessage.END_MESSAGE);       	
        	} else if(tempUser.getStatus() == Status.INACTIVE) { // user is inactive, but same id cannot be used for saving
        		tempUser.setMessage(User.DISCRIMINATOR + MediatorMessage.STATUS_INACTIVE + MediatorMessage.SO_MESSAGE + MediatorMessage.CRUD_FAILURE + MediatorMessage.CRUD_CREATE + MediatorMessage.END_MESSAGE);       	     
        	}  		
    	} else {    		
    		// whenever a user is being created its profile is also automatically being created 
    		Profile profile = new Profile(user.getId());
    		user.setProfile(profileService.saveProfile(profile));
    		
    		// whenever a user is being created its contact info is also automatically being created 
    		ContactInfo contactInfo = new ContactInfo(user.getId());
    		user.setContactInfo(contactInfoService.saveContactInfo(contactInfo));
    		  
    		// whenever a user of type provider is being created its provider is also automatically being created 
    		if(user.getUserType().equals(User.UserType.PROVIDER)) {
    			user.setProvider(providerService.saveProvider(new Provider(user.getId())));
    		} else if(user.getUserType().equals(User.UserType.CONSUMER)) {
    			// whenever a user of type consumer is being created its consumer is also automatically being created 
    			user.setConsumer(consumerService.saveConsumer(new Consumer(user.getId())));
    		}  
    		
    		user.setMessage(User.DISCRIMINATOR +  MediatorMessage.CRUD_SUCCESS + MediatorMessage.CRUD_CREATE + MediatorMessage.END_MESSAGE);        	    		
    		tempUser = userRepository.save(user);  
    	}        
       
        return tempUser;
    }
    
    @Override
    public User deleteUser(String id) {
    	
    	User user = new User();
    	user.clearModel();    	
    	
    	if(Utility.isBlank(id)) {
    		user.setMessage(MediatorMessage.ERROR_ISBLANK);
    	} else if(Utility.isEmpty(id)) {
    		user.setMessage(MediatorMessage.ERROR_ISEMPTY);
    	} else {
            user = findUserByUserId(id);
            if (user != null) {
            	if(user.getStatus() == Status.ACTIVE) { // user can be deleted if it has not been deleted before
            		user.setStatus(Status.INACTIVE);
            		user.setMessage(User.DISCRIMINATOR +  MediatorMessage.CRUD_SUCCESS + MediatorMessage.CRUD_DELETE + MediatorMessage.END_MESSAGE);
                	user = saveUser(user);
                	
            		// whenever a user is being made inactive its profile is also automatically being made inactive 
                	Profile profile = profileService.findProfileByUserId(id);
                	profile.setStatus(Status.INACTIVE);
                	profileService.saveProfile(profile);
                	
                	// whenever a user is being made inactive its contact info is also automatically being made inactive
                	ContactInfo contactInfo = contactInfoService.findContactInfoByUserId(id);
                	contactInfo.setStatus(Status.INACTIVE);
                	contactInfoService.saveContactInfo(contactInfo);
                	
            	} else if(user.getStatus() == Status.INACTIVE) {
            		user = new User();
            		user.clearModel(); 
            		user.setMessage(User.DISCRIMINATOR + MediatorMessage.STATUS_INACTIVE + MediatorMessage.SO_MESSAGE + MediatorMessage.CRUD_FAILURE + MediatorMessage.CRUD_DELETE + MediatorMessage.END_MESSAGE);       	     
            	}
            } else {
            	user = new User();
            	user.setMessage(User.DISCRIMINATOR +  MediatorMessage.CRUD_SUCCESS + MediatorMessage.CRUD_DELETE + MediatorMessage.END_MESSAGE);
            }
    	}
    	
        return user;    	
    }
    
    @Override
 	public BCryptPasswordEncoder encoder() {
 	    return new BCryptPasswordEncoder();
 	}
}
