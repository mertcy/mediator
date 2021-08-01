package com.java.backend.mediator.User;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.java.backend.mediator.MediatorMessage.MediatorMessage;
import com.java.backend.mediator.Model.Model.Status;

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
    	return userService.createUser(user);
    }
    
    @PutMapping(value = "/save", produces = "application/json")
    public User saveUser(@RequestBody User user) {
        return userService.saveUser(user);
    }
    
    @PutMapping(value = "/delete/{id}", produces = "application/json")
    public User deleteUser(@PathVariable String id) {    	
    	return userService.deleteUser(id);
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
        return userService.getAllUsers();  	
    }
    
    @GetMapping("/")
    public String home() {
        return "Hello from Mediator App Service User Endpoint!";
    }
    
}
