package com.java.backend.mediator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.java.backend.mediator.User.User;
import com.java.backend.mediator.User.User.UserType;
import com.java.backend.mediator.User.UserController;

@ManagedBean
@RequestScoped
@Component
public class SearchManagedBean {

	private List<User> userList = new ArrayList<>();
	private String username;
	private int email;

	@Autowired
	UserController userService;
	
	@PostConstruct
	public void init() {
		userList = userService.getAllUsers().stream().filter(u->u.userType.equals(UserType.PROVIDER)).collect(Collectors.toList());
	}

	public List<User> getUserList() {
		return userList;
	}

	public void setUserList(List<User> userList) {
		this.userList = userList;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getEmail() {
		return email;
	}

	public void setEmail(int email) {
		this.email = email;
	}

}
