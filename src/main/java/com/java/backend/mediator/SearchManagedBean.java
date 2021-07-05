package com.java.backend.mediator;

import java.math.BigDecimal;
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
import com.java.backend.mediator.ServiceProvided.*;
import com.java.backend.mediator.ServiceProvided.CareService.CaredPlace;
import com.java.backend.mediator.ServiceProvided.CareService.CaredType;
import com.java.backend.mediator.ServiceProvided.DogWalkerService.DogActivityLevel;
import com.java.backend.mediator.ServiceProvided.DogWalkerService.DogBreed;
import com.java.backend.mediator.ServiceProvided.DogWalkerService.DogSize;
import com.java.backend.mediator.ServiceProvided.DogWalkerService.DogTrainability;
import com.java.backend.mediator.Provider.Provider;
import com.java.backend.mediator.Provider.ProviderController;
import com.java.backend.mediator.Provider.ProviderService;
import com.java.backend.mediator.ServiceProvided.ServiceProvided;
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
	
	private String caredPlace;
	private String caredType;
	private BigDecimal caredAge;
	private String dogBreed;
	private String dogActivityLevel;
	private String dogSize;
	private String dogTrainability;	
	private BigDecimal totalArea;
	private BigDecimal maxRoomNumber;
	private BigDecimal maxWindowNumber;
	private boolean containsPet;
	private String district;

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
			if(p.getServicesProvided().get(0).getServiceType().name().equals(service)) {
				ServiceProvided provided = p.getServicesProvided().get(0);	
				if(service.equals("HOUSE_CLEANING_SERVICE")
					 && (totalArea == null || ((HouseCleaningService) provided).getTotalAreaM2().compareTo(totalArea) == 0)
					 && (maxRoomNumber == null || ((HouseCleaningService) provided).getMaxRoomNumber().compareTo(maxRoomNumber) == 0)
					 && (maxWindowNumber == null || ((HouseCleaningService) provided).getMaxWindowNumber().compareTo(maxWindowNumber) == 0)
					 && (((HouseCleaningService) provided).isContainsPet() == containsPet)
					 && (district == null || ((HouseCleaningService) provided).getDistrict().equals(district))){		
					userList.add(userService.getUser(p.getId()));
				}else if(service.equals("DOG_WALKER_SERVICE")
					 && (dogBreed == null || dogBreed == "" || ((DogWalkerService) provided).getDogBreed().equals(DogBreed.valueOf(dogBreed)))
					 && (dogActivityLevel == null || dogActivityLevel == "" || ((DogWalkerService) provided).getDogActivityLevel().equals(DogActivityLevel.valueOf(dogActivityLevel)))
					 && (dogSize == null || dogSize == "" || ((DogWalkerService) provided).getDogSize().equals(DogSize.valueOf(dogSize)))
					 && (dogTrainability == null || dogTrainability == "" || ((DogWalkerService) provided).getDogTrainability().equals(DogTrainability.valueOf(dogTrainability)))) {
					userList.add(userService.getUser(p.getId()));
				}else if(service.equals("CARE_SERVICE") 
					 && (caredPlace == null || ((CareService) provided).getCaredPlace().equals(CaredPlace.valueOf(caredPlace)))
					 && (caredType == null || ((CareService) provided).getCaredType().equals(CaredType.valueOf(caredType)))
					 && (caredAge == null || ((CareService) provided).getCaredAge().compareTo(caredAge) == 0)){
					userList.add(userService.getUser(p.getId()));
				}
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
	
	public String getCaredPlace() {
		return caredPlace;
	}

	public void setCaredPlace(String caredPlace) {
		this.caredPlace = caredPlace;
	}

	public String getCaredType() {
		return caredType;
	}

	public void setCaredType(String caredType) {
		this.caredType = caredType;
	}

	public BigDecimal getCaredAge() {
		return caredAge;
	}

	public void setCaredAge(BigDecimal caredAge) {
		this.caredAge = caredAge;
	}

	public String getDogBreed() {
		return dogBreed;
	}

	public void setDogBreed(String dogBreed) {
		this.dogBreed = dogBreed;
	}

	public String getDogActivityLevel() {
		return dogActivityLevel;
	}

	public void setDogActivityLevel(String dogActivityLevel) {
		this.dogActivityLevel = dogActivityLevel;
	}

	public String getDogSize() {
		return dogSize;
	}

	public void setDogSize(String dogSize) {
		this.dogSize = dogSize;
	}

	public String getDogTrainability() {
		return dogTrainability;
	}

	public void setDogTrainability(String dogTrainability) {
		this.dogTrainability = dogTrainability;
	}

	public BigDecimal getMaxRoomNumber() {
		return maxRoomNumber;
	}

	public void setMaxRoomNumber(BigDecimal maxRoomNumber) {
		this.maxRoomNumber = maxRoomNumber;
	}

	public BigDecimal getMaxWindowNumber() {
		return maxWindowNumber;
	}

	public void setMaxWindowNumber(BigDecimal maxWindowNumber) {
		this.maxWindowNumber = maxWindowNumber;
	}

	public boolean isContainsPet() {
		return containsPet;
	}

	public void setContainsPet(boolean containsPet) {
		this.containsPet = containsPet;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}
	
	public BigDecimal getTotalArea() {
		return totalArea;
	}

	public void setTotalArea(BigDecimal totalArea) {
		this.totalArea = totalArea;
	}
}
