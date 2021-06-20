package com.java.backend.mediator;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.java.backend.mediator.Consumer.Consumer;
import com.java.backend.mediator.MediatorMessage.MediatorMessage;
import com.java.backend.mediator.Profile.ProfileService;
import com.java.backend.mediator.Provider.Provider;
import com.java.backend.mediator.Provider.ProviderController;
import com.java.backend.mediator.Provider.ProviderService;
import com.java.backend.mediator.ServiceProvided.ServiceProvided.ServiceType;
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
	
	private User currentUser; // current user session
	
	private Provider provider;
	private Consumer consumer;

	private User selectedProvider;

	@Autowired
	UserController userService;
	
	@Autowired
	ProfileService profileService;
	
	@Autowired
	ProviderService providerService;
	
	@Autowired
	ProviderController providerController;
	
	@PostConstruct
	public void init() {
		userList = userService.getAllUsers().stream().filter(u->u.getUserType().equals(UserType.PROVIDER)).collect(Collectors.toList());
		emptyCurrentUserSession();
	}
	
	public void emptyCurrentUserSession() {
		User user = new User();
		user.setUserName(MediatorMessage.STATUS_NOTAVAILABLE);
		user.setEmail(MediatorMessage.STATUS_NOTAVAILABLE);
		currentUser = user;
	}

	public String getFilteredUserList(String service) {
		userList = userService.getAllUsers().stream().filter(u->u.getUserType().equals(UserType.PROVIDER)).collect(Collectors.toList());
		List<Provider> providerList = providerService.findProviders(userList.stream().map(u->u.getId()).collect(Collectors.toList()));
		userList.clear();
		for(Provider p : providerList) {
			if(p.getServicesProvided().stream().anyMatch(s->s.getServiceType().name().equals(service))) {
				userList.add(userService.getUser(p.getId()));
			}
		}
		return "consumer.xhtml";
	}
	
	public String getProviderProfile(String id) {
		/*
		Provider selectedProvider = providerController.getProvider(id);
		
		userList = userService.getAllUsers().stream().filter(u->u.getUserType().equals(UserType.PROVIDER)).collect(Collectors.toList());
		List<Provider> providerList = providerService.findProviders(userList.stream().map(u->u.getId()).collect(Collectors.toList()));
		userList.clear();
		for(Provider p : providerList) {
			if(p.getServicesProvided().stream().anyMatch(s->s.getServiceType().name().equals(service))) {
				userList.add(userService.getUser(p.getId()));
			}
		}*/
		return "provider_profile.xhtml";
	}
	
	public String clearFilter() {
		userList = userService.getAllUsers().stream().filter(u->u.getUserType().equals(UserType.PROVIDER)).collect(Collectors.toList());
		return "consumer.xhtml";
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

	public User getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(User currentUser) {
		this.currentUser = currentUser;
	}
	
	public Provider getProvider() {
		return provider;
	}

	public void setProvider(Provider provider) {
		this.provider = provider;
	}
	
	public Consumer getConsumer() {
		return consumer;
	}

	public void setConsumer(Consumer consumer) {
		this.consumer = consumer;
	}
	
	public User getSelectedProvider() {
		return selectedProvider;
	}

	public void setSelectedProvider(User selectedProvider) {
		this.selectedProvider = selectedProvider;
	}
}
