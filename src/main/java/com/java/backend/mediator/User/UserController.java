package com.java.backend.mediator.User;

import com.java.backend.mediator.MediatorMessage.MediatorMessage;
import com.java.backend.mediator.Model.Model.Status;
import com.java.backend.mediator.Utility.Utility;

import com.java.backend.mediator.Profile.Profile;
import com.java.backend.mediator.Profile.ProfileService;
import com.java.backend.mediator.Provider.Provider;
import com.java.backend.mediator.Provider.ProviderService;
import com.java.backend.mediator.Consumer.Consumer;
import com.java.backend.mediator.Consumer.ConsumerService;
import com.java.backend.mediator.ContactInfo.ContactInfo;
import com.java.backend.mediator.ContactInfo.ContactInfoService;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/users")
public class UserController {

    private UserService userService;
	private ProfileService profileService;
	private ContactInfoService contactInfoService;
	private ProviderService providerService;
	private ConsumerService consumerService;
	
    @Autowired
    public UserController(UserService userService, ProfileService profileService, ContactInfoService contactInfoService, ProviderService providerService, ConsumerService consumerService) {
        this.userService = userService;
        this.profileService = profileService;
        this.contactInfoService = contactInfoService;
        this.providerService = providerService;
        this.consumerService = consumerService;
    }

    @PostMapping(value = "/create", produces = "application/json")
    public User createUser(@RequestBody User user) {
    	User tempUser = null;
    	String userId =  user.getId();
    	   	
    	if((userService.findUserByUserId(userId) != null)) {     		
    		tempUser = userService.findUserByUserId(userId);
    		
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
    			providerService.saveProvider(new Provider(user.getId()));
    		} else if(user.getUserType().equals(User.UserType.CONSUMER)) {
    			// whenever a user of type consumer is being created its consumer is also automatically being created 
    			consumerService.saveConsumer(new Consumer(user.getId()));
    		}  
    		
    		user.setMessage(User.DISCRIMINATOR +  MediatorMessage.CRUD_SUCCESS + MediatorMessage.CRUD_CREATE + MediatorMessage.END_MESSAGE);        	    		
    		tempUser = userService.saveUser(user);  
    	}        
       
        return tempUser;		
    }
    
    @PutMapping(value = "/save", produces = "application/json")
    public User saveUser(@RequestBody User user) {
        User tempUser = userService.findUserByUserId(user.getId());
        if (tempUser != null) {     	
        	if(tempUser.getStatus() == Status.ACTIVE) { // user can be saved if it is active
        		user.setMessage(User.DISCRIMINATOR +  MediatorMessage.CRUD_SUCCESS + MediatorMessage.CRUD_UPDATE + MediatorMessage.END_MESSAGE);
            	tempUser = userService.saveUser(user);
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
    
    @PutMapping(value = "/delete/{id}", produces = "application/json")
    public User deleteUser(@PathVariable String id) {
    	User user = new User();
    	user.clearModel();    	
    	
    	if(Utility.isBlank(id)) {
    		user.setMessage(MediatorMessage.ERROR_ISBLANK);
    	} else if(Utility.isEmpty(id)) {
    		user.setMessage(MediatorMessage.ERROR_ISEMPTY);
    	} else {
            user = userService.findUserByUserId(id);
            if (user != null) {
            	if(user.getStatus() == Status.ACTIVE) { // user can be deleted if it has not been deleted before
            		user.setStatus(Status.INACTIVE);
            		user.setMessage(User.DISCRIMINATOR +  MediatorMessage.CRUD_SUCCESS + MediatorMessage.CRUD_DELETE + MediatorMessage.END_MESSAGE);
                	user = userService.saveUser(user);
                	
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

    @GetMapping(value = "/{id}", produces = "application/json")
    public User getUser(@PathVariable String id) {
    	User user = null;
        
        try {
        	user = userService.findUserByUserId(id);
        	
    		if(user.getStatus() == Status.ACTIVE) {
            	user.setMessage(User.DISCRIMINATOR +  MediatorMessage.CRUD_SUCCESS + MediatorMessage.CRUD_READ + MediatorMessage.END_MESSAGE);       	
    		} else if(user.getStatus() == Status.INACTIVE)  {
    			user = new User();
    			user.setStatus(Status.INACTIVE);
            	user.setMessage(User.DISCRIMINATOR +  MediatorMessage.CRUD_FAILURE + MediatorMessage.CRUD_READ + MediatorMessage.END_MESSAGE);       	
    		}        	
        } catch (Exception e) {
        	user = new User();
        	user.setMessage(User.DISCRIMINATOR +  MediatorMessage.CRUD_FAILURE + MediatorMessage.CRUD_READ + MediatorMessage.END_MESSAGE);
        	user.setStatus(Status.NOTAVAILABLE);
        	
            return user;
        }
       
        return user;
    }
    

    @GetMapping(value = "/all", produces = "application/json")
    public List<User> getAllUsers() {
    	List<User> allUsers = userService.getAllUsers();  	
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
    
    @GetMapping("/")
    public String home() {
        return "Hello from Mediator App Service User Endpoint!";
    }
    
}
