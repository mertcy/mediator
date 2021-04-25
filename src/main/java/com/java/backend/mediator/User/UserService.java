package com.java.backend.mediator.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findUserByUserId(String id) {
    	return userRepository.findUserByUserId(id);	
    }
    
    List<User> getAllUsers() {  	
        return userRepository.findAll();
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }
    
    public User deleteUser(User user) {
        return userRepository.save(user);
    }
 
}
