package com.java.backend.mediator;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.RowEditEvent;
import org.primefaces.model.file.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.java.backend.mediator.Consumer.IConsumerService;
import com.java.backend.mediator.ContactInfo.ContactInfo;
import com.java.backend.mediator.ContactInfo.IContactInfoService;
import com.java.backend.mediator.Document.Document;
import com.java.backend.mediator.Document.Document.DocumentType;
import com.java.backend.mediator.Profile.IProfileService;
import com.java.backend.mediator.Profile.Profile;
import com.java.backend.mediator.Provider.IProviderService;
import com.java.backend.mediator.Provider.Provider;
import com.java.backend.mediator.ServiceProvided.DogWalkerService;
import com.java.backend.mediator.ServiceProvided.DogWalkerService.DogActivityLevel;
import com.java.backend.mediator.ServiceProvided.DogWalkerService.DogBarkingLevel;
import com.java.backend.mediator.ServiceProvided.DogWalkerService.DogBreed;
import com.java.backend.mediator.ServiceProvided.DogWalkerService.DogCoatType;
import com.java.backend.mediator.ServiceProvided.DogWalkerService.DogShedding;
import com.java.backend.mediator.ServiceProvided.DogWalkerService.DogSize;
import com.java.backend.mediator.ServiceProvided.DogWalkerService.DogTrainability;
import com.java.backend.mediator.ServiceProvided.CareService;
import com.java.backend.mediator.ServiceProvided.CareService.CaredPlace;
import com.java.backend.mediator.ServiceProvided.CareService.CaredType;
import com.java.backend.mediator.ServiceProvided.HouseCleaningService;
import com.java.backend.mediator.ServiceProvided.ServiceProvided;
import com.java.backend.mediator.ServiceProvided.ServiceProvided.ServiceType;
import com.java.backend.mediator.User.IUserService;
import com.java.backend.mediator.User.User;
import com.java.backend.mediator.User.User.UserType;

@SessionScoped
@ManagedBean
@RequestScoped
@Component
public class ButtonManagedBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -9069971252976959506L;

	@Autowired
	IUserService userService;
	
	@Autowired
	IContactInfoService contactService;

	@Autowired
	IProfileService profileService;
	
	@Autowired
	IProviderService providerService;
	
	@Autowired
	IConsumerService consumerService;
	
	@Autowired
	SearchManagedBean searchManagedBean;
	
	public String username;
	public String password;
	public String email;
	public String name;
	public String surname;
	public String address;

	public Date birthDate;
	private String gender;
	private boolean accept;
	private String phoneNumber;
	private String nationality;
	private UploadedFile file;
    private String userType;
    //private Map<String,Map<String,String>> data = new HashMap<String, Map<String,String>>();
    private String service;
    private String caredPlace;
	private String document;
	private String caredType;
	private BigDecimal caredAge;
	private Map<String,String> services;
	private Map<String,String> documents;
	private Map<String,String> caredPlaces;
	private Map<String, String> caredTypes;
	private Map<String,Integer> ratings;
	
	private String dogBreed;
	private String dogActivityLevel;
	private String dogBarkingLevel;
	private String dogCoatType;
	private String dogShedding;
	private String dogSize;
	private String dogTrainability;
	private Map<String, String> dogBreeds;

	private Map<String, String> dogActivityLevels;

	private Map<String, String> dogBarkingLevels;

	private Map<String, String> dogCoatTypes;

	private Map<String, String> dogSheddings;

	private Map<String, String> dogSizes;

	private Map<String, String> dogTrainabilities;
	
	private BigDecimal totalAreaM2;
	private BigDecimal maxRoomNumber;
	private BigDecimal maxWindowNumber;
	private BigDecimal roomHeightCm;
	private boolean containsPet;
	private String district;
	
	private Integer rating;
	private String selectedProviderId;
	
	public String login() {
		try {
			List<User> users = userService.getAllUsers();
			Optional<User> user = users.stream().filter(Objects::nonNull).filter(u->u.getUserName().equals(username) && u.getPassword().equals(password)).findFirst();
				if (user.isPresent()) {
		
					// set current user session
					searchManagedBean.setCurrentUser(userService.findUserByUserId(user.get().getId()));
					
		 			if(user.get().getUserType().equals(User.UserType.CONSUMER)) {
		 				searchManagedBean.setConsumer(consumerService.findConsumerByUserId(searchManagedBean.getCurrentUser().getId()));
		 				
		 				return "consumer.xhtml";
		 			} else if(user.get().getUserType().equals(User.UserType.PROVIDER)) {				
		 				searchManagedBean.setProvider(providerService.findProviderByUserId(searchManagedBean.getCurrentUser().getId()));

		 		    	String pageToBeReturned = "profile.xhtml";
		 		   		ArrayList<ServiceProvided> servicesProvided = searchManagedBean.getProvider().getServicesProvided();
		 	    		if(servicesProvided.size() == 1) {    			
		 	    			ServiceType serviceType = servicesProvided.get(0).getServiceType();  			
		 	    			switch(serviceType) {
		 	    				case CARE_SERVICE:
		 	    					pageToBeReturned = "provider-care-service.xhtml";
		 	    					break;
		 	    				case DOG_WALKER_SERVICE:
		 	    					pageToBeReturned = "provider-dog-walker-service.xhtml";
		 	    					break;
		 	    				case HOUSE_CLEANING_SERVICE:
		 	    					pageToBeReturned = "provider-house-cleaning-service.xhtml";
		 	    					break;
		 	    					
		 	    			}
		 	    		}
		 				
		 				return pageToBeReturned;
		 			}
		 			
				}		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "login.xhtml";

	}

	public String signUp() {
	//TODO provider document
		//TODO where to set service type
		User user = new User();
	    user.setPassword(password);
		user.setEmail(email);
		user.setUserType(userType.equals("Consumer")?UserType.CONSUMER:UserType.PROVIDER);
		user.setUserName(username);
		try {
			User signupUser = userService.createUser(user);
			ContactInfo contact = new ContactInfo(signupUser.getId(), name, surname, address, 
													birthDate.toString(), gender, nationality, phoneNumber);
			signupUser.setContactInfo(contact);
			signupUser = userService.saveUser(signupUser);
			
			// set current user session
			searchManagedBean.setCurrentUser(signupUser);

 			if(signupUser.getUserType().equals(User.UserType.CONSUMER)) {				
 				searchManagedBean.setConsumer(consumerService.findConsumerByUserId(searchManagedBean.getCurrentUser().getId()));
 				
 				return "consumer.xhtml";
 			} else if(signupUser.getUserType().equals(User.UserType.PROVIDER)) {
 				
 				ServiceProvided serviceProvided = new ServiceProvided(); 				
 				if(service.equals(ServiceProvided.ServiceType.CARE_SERVICE.toString())) {
 					serviceProvided = new CareService(caredAge, CaredType.valueOf(caredType), CaredPlace.valueOf(caredPlace));
 				} else if(service.equals(ServiceProvided.ServiceType.DOG_WALKER_SERVICE.toString())) {
 					serviceProvided = new DogWalkerService(DogBreed.valueOf(dogBreed),
						 							DogActivityLevel.valueOf(dogActivityLevel),
						 							DogBarkingLevel.valueOf(dogBarkingLevel),
						 							DogCoatType.valueOf(dogCoatType),
						 							DogShedding.valueOf(dogShedding),
						 							DogSize.valueOf(dogSize),
						 							DogTrainability.valueOf(dogTrainability));
 				} else if(service.equals(ServiceProvided.ServiceType.HOUSE_CLEANING_SERVICE.toString())) {
 					serviceProvided = new HouseCleaningService(totalAreaM2, maxRoomNumber, maxWindowNumber, roomHeightCm, containsPet, district);
 				}
 				
 				Provider provider = providerService.findProviderByUserId(searchManagedBean.getCurrentUser().getId());				
 				signupUser.setProvider(providerService.saveServiceProvided(provider.getId(), serviceProvided)); 
 				userService.saveUser(signupUser);
 				
 				searchManagedBean.setProvider(providerService.findProviderByUserId(searchManagedBean.getCurrentUser().getId()));
 				
 				clearall();
 				
 		    	String pageToBeReturned = "profile.xhtml";
 		   		ArrayList<ServiceProvided> servicesProvided = signupUser.getProvider().getServicesProvided();
 	    		if(servicesProvided.size() == 1) {    			
 	    			ServiceType serviceType = servicesProvided.get(0).getServiceType();  			
 	    			switch(serviceType) {
 	    				case CARE_SERVICE:
 	    					pageToBeReturned = "provider-care-service.xhtml";
 	    					break;
 	    				case DOG_WALKER_SERVICE:
 	    					pageToBeReturned = "provider-dog-walker-service.xhtml";
 	    					break;
 	    				case HOUSE_CLEANING_SERVICE:
 	    					pageToBeReturned = "provider-house-cleaning-service.xhtml";
 	    					break;
 	    					
 	    			}
 	    		}
 				
 				return pageToBeReturned; 				
 			}
 			
 			return "login.xhtml";

		} catch (Exception e) {
			return e.getMessage();
		}
	}
	
	public String toSignUp() {
		clearall();
		return "signup.xhtml";
	}
	
	public String toProfile() {
		return "profile.xhtml";
	}
	
	public String toProfileProvider(String id) {

		String profilePageToBeReturned = "profile-provider.xhtml";
		
		searchManagedBean.setSelectedProvider(userService.findUserByUserId(id));
		
		ArrayList<ServiceProvided> servicesProvided = searchManagedBean.getSelectedProvider().getProvider().getServicesProvided();
		
		if((servicesProvided != null) && (servicesProvided.size() == 1)) {
			
			String providedServiceType = servicesProvided.get(0).serviceType.toString();
			
			if(providedServiceType.equals(ServiceProvided.ServiceType.CARE_SERVICE.toString())) {
				profilePageToBeReturned = "profile-provider-care-service.xhtml";
			} else if(providedServiceType.equals(ServiceProvided.ServiceType.DOG_WALKER_SERVICE.toString())) {
				profilePageToBeReturned = "profile-provider-dog-walker-service.xhtml";
			} else if(providedServiceType.equals(ServiceProvided.ServiceType.HOUSE_CLEANING_SERVICE.toString())) {
				profilePageToBeReturned = "profile-provider-house-cleaning-service.xhtml";
			}			
		}

		return profilePageToBeReturned;
	}
	
    public void rateProvider() {   	    	
    	User providerUser = userService.findUserByUserId(selectedProviderId);  	
    	providerUser.setProvider(providerService.rateProvider(selectedProviderId, searchManagedBean.getCurrentUser().getId(), rating));
        userService.saveUser(providerUser);
    }
    
    public String toUserHomePage() {   
    	
    	String pageToBeReturned = "profile.xhtml";
    	
    	String id = searchManagedBean.getCurrentUser().getId();
    	
    	Provider provider = providerService.findProviderByUserId(id);
    	
    	if(provider != null) {
    		ArrayList<ServiceProvided> servicesProvided = provider.getServicesProvided();
    		if(servicesProvided.size() == 1) {    			
    			ServiceType serviceType = servicesProvided.get(0).getServiceType();  			
    			switch(serviceType) {
    				case CARE_SERVICE:
    					pageToBeReturned = "provider-care-service.xhtml";
    					break;
    				case DOG_WALKER_SERVICE:
    					pageToBeReturned = "provider-dog-walker-service.xhtml";
    					break;
    				case HOUSE_CLEANING_SERVICE:
    					pageToBeReturned = "provider-house-cleaning-service.xhtml";
    					break;
    			}
    		}  		
    	} else if(consumerService.findConsumerByUserId(id) != null) {
    		pageToBeReturned = "consumer.xhtml";
    	}
    	
    	return pageToBeReturned;
    }	 

	public void clearall() {
		// TODO Auto-generated method stub
		username = null;
		password = null;
		email = null;
		name = null;
		surname = null;
		birthDate = null;
		gender = null;
		accept = false;
		phoneNumber = null;
		userType = null;
		service = null;
		document = null;
	}

	public String logout() {
		searchManagedBean.emptyCurrentUserSession();
		
		return "login.xhtml";
	}

	public String redirect() {
		return "menu.xhtml";
	}
	
	public void onServiceChange() {
		//TODO document upload change
		System.out.println("New value: " + service);
	}

	public void uploadDocument(FileUploadEvent event) {
		// Get uploaded file from the FileUploadEvent
		this.file = event.getFile();
		// Print out the information of the file
		System.out.println("Uploaded File Name Is :: "+file.getFileName()+" :: Uploaded File Size :: "+file.getSize());
		
	    Document documentObj = new Document();
	    documentObj.setDocumentType(DocumentType.valueOf(document));
	    documentObj.setDocumentTitle(file.getFileName());
	    documentObj.setDocumentDescription(file.getContent().toString());

	    profileService.saveDocument(searchManagedBean.getCurrentUser().getId(), documentObj);
	    
	}
	
    public void onRowEditContactInfo(RowEditEvent<ContactInfo> event) {
    	User user = searchManagedBean.getCurrentUser();
    	ContactInfo contactInfo = user.getContactInfo();
        contactInfo.setTelephoneNumber(event.getObject().getTelephoneNumber());
        contactInfo.setAddress(event.getObject().getAddress());
        user.setContactInfo(contactService.saveContactInfo(contactInfo));
        userService.saveUser(user);
    }
    
    public void onRowEditProfileInfo(RowEditEvent<Profile> event) {
    	User user = searchManagedBean.getCurrentUser();
    	Profile profile = user.getProfile();
        profile.setProfileBio(event.getObject().getProfileBio());
        user.setProfile(profileService.saveProfile(profile));
        userService.saveUser(user);
    }
    
    public void onRowEditProvidedService(RowEditEvent<ServiceProvided> event) {
    	User user = searchManagedBean.getCurrentUser();;
    	ServiceProvided tempServiceProvided = event.getObject();
    	Provider provider = providerService.findProviderByUserId(searchManagedBean.getCurrentUser().getId());
    	ArrayList<ServiceProvided> servicesProvided = provider.getServicesProvided();
    	
    	for(int i = 0; i < servicesProvided.size(); i++) {  		
    		if(servicesProvided.get(i).getId().equals(tempServiceProvided.getId())) {
    			servicesProvided.set(i, tempServiceProvided);
    			provider.setServicesProvided(servicesProvided);
    	        user.setProvider(providerService.saveProvider(provider));
    	        userService.saveUser(user);
    		}
    	}        
    }
    
    public String getTotalOfRatersForProvider(String providerId) {    	
    	return providerService.getTotalOfRatersForProvider(providerId).toString();   	
    }
    
    public String getTotalRatingForProvider(String providerId) {
    	return providerService.getTotalRatingForProvider(providerId).toString();   	
    }
    
    public String getConsumerRatingForProvider(String providerId) {    	
    	return providerService.getConsumerRatingForProvider(providerId, searchManagedBean.getCurrentUser().getId()).toString();   	
    }
	
	public ButtonManagedBean() {
        documents = new HashMap<String, String>();
        documents.put(Document.DocumentType.DIPLOMA.toString(), Document.DocumentType.DIPLOMA.toString());
        documents.put(Document.DocumentType.DOG_TRAINING_CERTIFICATE.toString(), Document.DocumentType.DOG_TRAINING_CERTIFICATE.toString());
        documents.put(Document.DocumentType.JUDICIAL_RECORD.toString(), Document.DocumentType.JUDICIAL_RECORD.toString());
        documents.put(Document.DocumentType.WORK_PERMIT_CERTIFICATE.toString(), Document.DocumentType.WORK_PERMIT_CERTIFICATE.toString());
        initServiceSection();
        initCareSection();
        initDogWalkerSection();
        initRatingSection();
	}
	
	private void initServiceSection() {
		
		services  = new HashMap<String, String>();
		services.put("Health Care", ServiceProvided.ServiceType.CARE_SERVICE.toString());
		services.put("House Cleaning", ServiceProvided.ServiceType.HOUSE_CLEANING_SERVICE.toString());
		services.put("Dog Walker", ServiceProvided.ServiceType.DOG_WALKER_SERVICE.toString());	
		
	}

	private void initCareSection() {
		
		caredPlaces = new HashMap<String, String>();
		caredPlaces.put("Not Available", CareService.CaredPlace.NOT_AVAILABLE.toString());
		caredPlaces.put("Nursing Home", CareService.CaredPlace.NURSING_HOME.toString());
		caredPlaces.put("Descendant House", CareService.CaredPlace.DESCENDANT_HOUSE.toString());
		caredPlaces.put("Own House", CareService.CaredPlace.OWN_HOUSE.toString());
		
		caredTypes = new HashMap<String, String>();
		caredTypes.put("Not Available", CareService.CaredType.NOT_AVAILABLE.toString());
		caredTypes.put("Infant", CareService.CaredType.INFANT.toString());
		caredTypes.put("Adult", CareService.CaredType.ADULT.toString());
		caredTypes.put("Old", CareService.CaredType.OLD.toString());
		
	}
	private void initDogWalkerSection() {
		
		dogBreeds = new HashMap<String, String>();
		dogBreeds.put("Bulldog", DogWalkerService.DogBreed.BULLDOG.toString());
		dogBreeds.put("Chihuava", DogWalkerService.DogBreed.CHIHUAHUA.toString());
		dogBreeds.put("Golden Retriever", DogWalkerService.DogBreed.GOLDEN_RETRIEVER.toString());
		dogBreeds.put("Labrador Retriever", DogWalkerService.DogBreed.LABRADOR_RETRIEVER.toString());
		dogBreeds.put("Pitbull", DogWalkerService.DogBreed.PITBULL.toString());
		dogBreeds.put("Mix", DogWalkerService.DogBreed.MIX.toString());
		dogBreeds.put("Not Available", DogWalkerService.DogBreed.NOT_AVAILABLE.toString());
		
		dogActivityLevels = new HashMap<String, String>();
		dogActivityLevels.put("Calm", DogWalkerService.DogActivityLevel.CALM.toString());
		dogActivityLevels.put("Energetic", DogWalkerService.DogActivityLevel.ENERGETIC.toString());
		dogActivityLevels.put("Needs Lots of Activity", DogWalkerService.DogActivityLevel.NEEDS_LOTS_OF_ACTIVITY.toString());
		dogActivityLevels.put("Regular Exersice", DogWalkerService.DogActivityLevel.REGULAR_EXERCISE.toString());
		dogActivityLevels.put("Not Available", DogWalkerService.DogActivityLevel.NOT_AVAILABLE.toString());
		
		dogBarkingLevels = new HashMap<String, String>();
		dogBarkingLevels.put("Frequent", DogWalkerService.DogBarkingLevel.FREQUENT.toString());
		dogBarkingLevels.put("InFrequent", DogWalkerService.DogBarkingLevel.INFREQUENT.toString());
		dogBarkingLevels.put("Likes to be Vocal", DogWalkerService.DogBarkingLevel.LIKES_TO_BE_VOCAL.toString());
		dogBarkingLevels.put("Medium", DogWalkerService.DogBarkingLevel.MEDIUM.toString());
		dogBarkingLevels.put("When Necessary", DogWalkerService.DogBarkingLevel.WHEN_NECESSARY.toString());
		dogBarkingLevels.put("Not Available", DogWalkerService.DogBarkingLevel.NOT_AVAILABLE.toString());
		
		dogCoatTypes = new HashMap<String, String>();
		dogCoatTypes.put("Hairless", DogWalkerService.DogCoatType.HAIRLESS.toString());
		dogCoatTypes.put("Long", DogWalkerService.DogCoatType.LONG.toString());
		dogCoatTypes.put("Medium", DogWalkerService.DogCoatType.MEDIUM.toString());
		dogCoatTypes.put("Short", DogWalkerService.DogCoatType.SHORT.toString());
		dogCoatTypes.put("Smooth", DogWalkerService.DogCoatType.SMOOTH.toString());
		dogCoatTypes.put("Wire", DogWalkerService.DogCoatType.WIRE.toString());
		dogCoatTypes.put("Not Available", DogWalkerService.DogCoatType.NOT_AVAILABLE.toString());
		
		dogSheddings = new HashMap<String, String>();
		dogSheddings.put("Frequent", DogWalkerService.DogShedding.FREQUENT.toString());
		dogSheddings.put("Infrequent", DogWalkerService.DogShedding.INFREQUENT.toString());
		dogSheddings.put("Occasional", DogWalkerService.DogShedding.OCCASIONAL.toString());
		dogSheddings.put("Regularly", DogWalkerService.DogShedding.REGULARLY.toString());
		dogSheddings.put("Seasonal", DogWalkerService.DogShedding.SEASONAL.toString());
		dogSheddings.put("Not Available", DogWalkerService.DogShedding.NOT_AVAILABLE.toString());
		
		dogSizes = new HashMap<String, String>();
		dogSizes.put("Large", DogWalkerService.DogSize.LARGE.toString());
		dogSizes.put("Medium", DogWalkerService.DogSize.MEDIUM.toString());
		dogSizes.put("Small", DogWalkerService.DogSize.SMALL.toString());
		dogSizes.put("XLarge", DogWalkerService.DogSize.XLARGE.toString());
		dogSizes.put("XSmall", DogWalkerService.DogSize.XSMALL.toString());
		dogSizes.put("Not Available", DogWalkerService.DogSize.NOT_AVAILABLE.toString());
		
		dogTrainabilities = new HashMap<String, String>();
		dogTrainabilities.put("Agreeable", DogWalkerService.DogTrainability.AGREEABLE.toString());
		dogTrainabilities.put("Easy to Please", DogWalkerService.DogTrainability.EAGER_TO_PLEASE.toString());
		dogTrainabilities.put("Easy Training", DogWalkerService.DogTrainability.EASY_TRAINING.toString());
		dogTrainabilities.put("Independent", DogWalkerService.DogTrainability.INDEPENDENT.toString());
		dogTrainabilities.put("May be Stubborn", DogWalkerService.DogTrainability.MAY_BE_STUBBORN.toString());
		dogTrainabilities.put("Not Available", DogWalkerService.DogTrainability.NOT_AVAILABLE.toString());	
		
	}
	
	private void initRatingSection() {	
		ratings = new HashMap<String, Integer>();
		ratings.put("1", 1);
		ratings.put("2", 2);
		ratings.put("3", 3);
		ratings.put("4", 4);
		ratings.put("5", 5);	
	}
	
	public String getService() {
		return service;
	}
	
	public void setService(String service) {
		this.service = service;
	}
	
	public String getCaredPlace() {
		return caredPlace;
	}

	public void setCaredPlace(String caredPlace) {
		this.caredPlace = caredPlace;
	}

	public BigDecimal getCaredAge() {
		return caredAge;
	}

	public void setCaredAge(BigDecimal caredAge) {
		this.caredAge = caredAge;
	}
		
	public String getDocument() {
		return document;
	}

	public void setDocument(String document) {
		this.document = document;
	}
	
	public Map<String, String> getServices() {
		return services;
	}
	
	public void setServices(Map<String, String> services) {
		this.services = services;
	}
	
	public Map<String, String> getCaredPlaces() {
		return caredPlaces;
	}

	public void setCaredPlaces(Map<String, String> caredPlaces) {
		this.caredPlaces = caredPlaces;
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

	public String getDogBarkingLevel() {
		return dogBarkingLevel;
	}

	public void setDogBarkingLevel(String dogBarkingLevel) {
		this.dogBarkingLevel = dogBarkingLevel;
	}

	public String getDogCoatType() {
		return dogCoatType;
	}

	public void setDogCoatType(String dogCoatType) {
		this.dogCoatType = dogCoatType;
	}

	public String getDogShedding() {
		return dogShedding;
	}

	public void setDogShedding(String dogShedding) {
		this.dogShedding = dogShedding;
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

	public Map<String, String> getDogBreeds() {
		return dogBreeds;
	}

	public void setDogBreeds(Map<String, String> dogBreeds) {
		this.dogBreeds = dogBreeds;
	}

	public Map<String, String> getDogActivityLevels() {
		return dogActivityLevels;
	}

	public void setDogActivityLevels(Map<String, String> dogActivityLevels) {
		this.dogActivityLevels = dogActivityLevels;
	}

	public Map<String, String> getDogBarkingLevels() {
		return dogBarkingLevels;
	}

	public void setDogBarkingLevels(Map<String, String> dogBarkingLevels) {
		this.dogBarkingLevels = dogBarkingLevels;
	}

	public Map<String, String> getDogCoatTypes() {
		return dogCoatTypes;
	}

	public void setDogCoatTypes(Map<String, String> dogCoatTypes) {
		this.dogCoatTypes = dogCoatTypes;
	}

	public Map<String, String> getDogSheddings() {
		return dogSheddings;
	}

	public void setDogSheddings(Map<String, String> dogSheddings) {
		this.dogSheddings = dogSheddings;
	}

	public Map<String, String> getDogSizes() {
		return dogSizes;
	}

	public void setDogSizes(Map<String, String> dogSizes) {
		this.dogSizes = dogSizes;
	}

	public Map<String, String> getDogTrainabilities() {
		return dogTrainabilities;
	}

	public void setDogTrainabilities(Map<String, String> dogTrainabilities) {
		this.dogTrainabilities = dogTrainabilities;
	}
	
	public Map<String, String> getDocuments() {
		return documents;
	}

	public void setDocuments(Map<String, String> documents) {
		this.documents = documents;
	}
	
	public String getCaredType() {
		return caredType;
	}
	
	public void setCaredType(String caredType) {
		this.caredType = caredType;
	}
	public Map<String, String> getCaredTypes() {
		return caredTypes;
	}

	public void setCaredTypes(Map<String, String> caredTypes) {
		this.caredTypes = caredTypes;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getNationality() {
		return nationality;
	}
	
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	public String getUserType() {
		return userType;
	}
	
	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public boolean getAccept() {
		return accept;
	}

	public void setAccept(boolean accept) {
		this.accept = accept;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}
	
	public BigDecimal getTotalAreaM2() {
		return totalAreaM2;
	}

	public void setTotalAreaM2(BigDecimal totalAreaM2) {
		this.totalAreaM2 = totalAreaM2;
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

	public BigDecimal getRoomHeightCm() {
		return roomHeightCm;
	}

	public void setRoomHeightCm(BigDecimal roomHeightCm) {
		this.roomHeightCm = roomHeightCm;
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

	public Map<String, Integer> getRatings() {
		return ratings;
	}

	public void setRatings(Map<String, Integer> ratings) {
		this.ratings = ratings;
	}

	public Integer getRating() {
		return rating;
	}

	public void setRating(Integer rating) {
		this.rating = rating;
	}

	public String getSelectedProviderId() {
		return selectedProviderId;
	}

	public void setSelectedProviderId(String selectedProviderId) {
		this.selectedProviderId = selectedProviderId;
	}


}
