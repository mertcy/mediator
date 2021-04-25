package com.java.backend.mediator.User;

import com.java.backend.mediator.MediatorMessage.MediatorMessage;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/users")
public class UserController {

    private UserService userService;
	
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/create", produces = "application/json")
    public User createUser(@RequestBody User user) {
    	User tempUser = null;
    	String userId =  user.getUserId();
    	
    	if((userService.findUserByUserId(userId) != null)) {     		
    		tempUser = userService.findUserByUserId(userId);
    		
        	if(tempUser.getStatus() == 1) { // user already exists
        		tempUser = new User();
        		tempUser.setMessage(User.DISCRIMINATOR +  MediatorMessage.CRUD_FAILURE + MediatorMessage.CRUD_CREATE + MediatorMessage.END_MESSAGE);       	
        	} else if(tempUser.getStatus() == 0) { // user has been deleted before, so valid operation
        		tempUser = user; 
        		saveUser(tempUser);
        	}  		
    	} else {
    		user.setMessage(User.DISCRIMINATOR +  MediatorMessage.CRUD_SUCCESS + MediatorMessage.CRUD_CREATE + MediatorMessage.END_MESSAGE);
        	tempUser = userService.saveUser(user);        
    	}        
       
        return tempUser;		
    }
    
    @PutMapping(value = "/save", produces = "application/json")
    public User saveUser(@RequestBody User user) {
        User tempUser = userService.findUserByUserId(user.getUserId());
        if (tempUser != null) {
        	tempUser.status = 1;
    		user.setMessage(User.DISCRIMINATOR +  MediatorMessage.CRUD_SUCCESS + MediatorMessage.CRUD_UPDATE + MediatorMessage.END_MESSAGE);
        	tempUser = userService.saveUser(user);
        }
        return tempUser;
    }
    
    /**
     * sets the status of the specified user as 0
     */
    @PutMapping(value = "/delete/{id}", produces = "application/json")
    public User deleteUser(@PathVariable String id) {
        User user = userService.findUserByUserId(id);
        if (user != null) {
        	if(user.getStatus() == 1) { // user can be deleted if it has not been deleted before
        		user.status = 0;
        		user.setMessage(User.DISCRIMINATOR +  MediatorMessage.CRUD_SUCCESS + MediatorMessage.CRUD_DELETE + MediatorMessage.END_MESSAGE);
            	userService.saveUser(user);
        	} else {
        		user = new User();
        		user.setMessage(User.DISCRIMINATOR +  MediatorMessage.CRUD_FAILURE + MediatorMessage.CRUD_DELETE + MediatorMessage.END_MESSAGE);
        	}
        } else {
        	user = new User();
        	user.setMessage(User.DISCRIMINATOR +  MediatorMessage.CRUD_SUCCESS + MediatorMessage.CRUD_DELETE + MediatorMessage.END_MESSAGE);
        }
        
        return user;
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public User getUser(@PathVariable String id) {
    	User user = null;
        
        try {
        	user = userService.findUserByUserId(id);
        	
    		if(user.getStatus() == 1) {
            	user.setMessage(User.DISCRIMINATOR +  MediatorMessage.CRUD_SUCCESS + MediatorMessage.CRUD_READ + MediatorMessage.END_MESSAGE);       	
    		} else if(user.getStatus() == 0)  {
    			user = new User();
    			user.setStatus(0);
            	user.setMessage(User.DISCRIMINATOR +  MediatorMessage.CRUD_FAILURE + MediatorMessage.CRUD_READ + MediatorMessage.END_MESSAGE);       	
    		}        	
        } catch (Exception e) {
        	user = new User();
        	user.setMessage(User.DISCRIMINATOR +  MediatorMessage.CRUD_FAILURE + MediatorMessage.CRUD_READ + MediatorMessage.END_MESSAGE);
        	user.setStatus(-1);
        	
            return user;
        }
       
        return user;
    }
    
    /**
     * return the users which have not been deleted (ones with status=1)
     */
    @GetMapping(value = "/all", produces = "application/json")
    public List<User> getAllUsers() {
    	List<User> allUsers = userService.getAllUsers();  	
    	List<User> activeUsers = new ArrayList<User>();
    	
    	for(User user: allUsers) {
    		if(user != null) {
        		if(user.getStatus() == 1) {
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
