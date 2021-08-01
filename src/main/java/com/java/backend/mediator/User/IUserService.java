package com.java.backend.mediator.User;

import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public interface IUserService {
	User findUserByUserId(String id);
	List<User> getAllUsers();
	User saveUser(User user);
	User createUser(User user);
	User deleteUser(String id);
	BCryptPasswordEncoder encoder();
}
