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

import com.java.backend.mediator.Consumer.ConsumerController;
import com.java.backend.mediator.ContactInfo.ContactInfo;
import com.java.backend.mediator.ContactInfo.ContactInfoController;
import com.java.backend.mediator.Document.Document;
import com.java.backend.mediator.Document.Document.DocumentType;
import com.java.backend.mediator.Profile.Profile;
import com.java.backend.mediator.Profile.ProfileController;
import com.java.backend.mediator.Profile.ProfileService;
import com.java.backend.mediator.Provider.Provider;
import com.java.backend.mediator.Provider.ProviderController;
import com.java.backend.mediator.ServiceProvided.DogWalkerService;
import com.java.backend.mediator.ServiceProvided.CareService;
import com.java.backend.mediator.ServiceProvided.HouseCleaningService;
import com.java.backend.mediator.ServiceProvided.ServiceProvided;
import com.java.backend.mediator.User.User;
import com.java.backend.mediator.User.User.UserType;
import com.java.backend.mediator.User.UserController;

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
	UserController userService;
	
	@Autowired
	ContactInfoController contactService;

	@Autowired
	ProfileService profileService;
	
	@Autowired
	ProfileController profileController;
	
	@Autowired
	ProviderController providerService;
	
	@Autowired
	ConsumerController consumerService;
	
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

	public String login() {
		try {
			List<User> users = userService.getAllUsers();
			Optional<User> user = users.stream().filter(Objects::nonNull).filter(u->u.getUserName().equals(username) && u.getPassword().equals(password)).findFirst();
				if (user.isPresent()) {
		
					// set current user session
					searchManagedBean.setCurrentUser(userService.getUser(user.get().getId()));
					
		 			if(user.get().getUserType().equals(User.UserType.CONSUMER)) {
		 				searchManagedBean.setConsumer(consumerService.getConsumer(searchManagedBean.getCurrentUser().getId()));
		 				
		 				return "consumer.xhtml";
		 			} else if(user.get().getUserType().equals(User.UserType.PROVIDER)) {				
		 				searchManagedBean.setProvider(providerService.getProvider(searchManagedBean.getCurrentUser().getId()));
		 				
		 				return "provider.xhtml";
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
			ContactInfo contact = new ContactInfo();
			contact.setName(name);
			contact.setLastName(surname);
			contact.setAddress(address);
			contact.setBirthDate(birthDate.toLocaleString());
			contact.setGender(gender);
			contact.setNationality(nationality);
			contact.setTelephoneNumber(phoneNumber);
			contact.setId(signupUser.getId());	
			signupUser.setContactInfo(contact);
			signupUser = userService.saveUser(signupUser);
			
			// set current user session
			searchManagedBean.setCurrentUser(signupUser);

 			if(signupUser.getUserType().equals(User.UserType.CONSUMER)) {				
 				searchManagedBean.setConsumer(consumerService.getConsumer(searchManagedBean.getCurrentUser().getId()));
 				
 				return "consumer.xhtml";
 			} else if(signupUser.getUserType().equals(User.UserType.PROVIDER)) {
 				
 				ServiceProvided serviceProvided = new ServiceProvided(); 				
 				if(service.equals(ServiceProvided.ServiceType.CARE_SERVICE.toString())) {
 					serviceProvided = new CareService();
 				} else if(service.equals(ServiceProvided.ServiceType.DOG_WALKER_SERVICE.toString())) {
 					serviceProvided = new DogWalkerService();
 				} else if(service.equals(ServiceProvided.ServiceType.HOUSE_CLEANING_SERVICE.toString())) {
 					serviceProvided = new HouseCleaningService();
 				}
 				
 				Provider provider = providerService.getProvider(searchManagedBean.getCurrentUser().getId());				
 				providerService.saveServiceProvided(provider.getId(), serviceProvided); 				
 				
 				searchManagedBean.setProvider(providerService.getProvider(searchManagedBean.getCurrentUser().getId()));
 				
 				clearall();
 				
 				return "provider.xhtml";
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

	    profileController.saveDocument(searchManagedBean.getCurrentUser().getId(), documentObj);
	    
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
        user.setProfile(profileController.saveProfile(profile));
        userService.saveUser(user);
    }
    
    public void onRowEditProvidedService(RowEditEvent<ServiceProvided> event) {
    	ServiceProvided tempServiceProvided = event.getObject();
    	Provider provider = providerService.getProvider(searchManagedBean.getCurrentUser().getId());
    	ArrayList<ServiceProvided> servicesProvided = provider.getServicesProvided();
    	
    	for(int i = 0; i < servicesProvided.size(); i++) {  		
    		if(servicesProvided.get(i).getId().equals(tempServiceProvided.getId())) {
    			servicesProvided.set(i, tempServiceProvided);
    			provider.setServicesProvided(servicesProvided);
    			providerService.saveProvider(provider);
    		}
    	}        
    }
	
	public ButtonManagedBean() {
		services  = new HashMap<String, String>();
        services.put("Health Care", ServiceProvided.ServiceType.CARE_SERVICE.toString());
        services.put("House Cleaning", ServiceProvided.ServiceType.HOUSE_CLEANING_SERVICE.toString());
        services.put("Dog Walker", ServiceProvided.ServiceType.DOG_WALKER_SERVICE.toString());
        
        caredPlaces = new HashMap<String, String>();
        caredPlaces.put("Not Available", CareService.CaredPlace.NOT_AVAILABLE.toString());
        caredPlaces.put("Nursing Home", CareService.CaredPlace.NURSING_HOME.toString());
        caredPlaces.put("Descendant House", CareService.CaredPlace.DESCENDANT_HOUSE.toString());
        caredPlaces.put("Own House", CareService.CaredPlace.OWN_HOUSE.toString());
        
        caredTypes = new HashMap<String, String>();
        caredTypes.put("Not Available", CareService.CaredType.NOT_AVAILABLE.toString());
        caredTypes.put("Nursing Home", CareService.CaredType.INFANT.toString());
        caredTypes.put("Descendant House", CareService.CaredType.ADULT.toString());
        caredTypes.put("Own House", CareService.CaredType.OLD.toString());
        
        documents = new HashMap<String, String>();
        documents.put(Document.DocumentType.DIPLOMA.toString(), Document.DocumentType.DIPLOMA.toString());
        documents.put(Document.DocumentType.DOG_TRAINING_CERTIFICATE.toString(), Document.DocumentType.DOG_TRAINING_CERTIFICATE.toString());
        documents.put(Document.DocumentType.JUDICIAL_RECORD.toString(), Document.DocumentType.JUDICIAL_RECORD.toString());
        documents.put(Document.DocumentType.WORK_PERMIT_CERTIFICATE.toString(), Document.DocumentType.WORK_PERMIT_CERTIFICATE.toString());
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

}
