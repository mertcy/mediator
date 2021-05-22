package com.java.backend.mediator;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.RowEditEvent;
import org.primefaces.model.file.UploadedFile;
import org.primefaces.model.file.UploadedFiles;
import org.primefaces.shaded.json.JSONObject;
import org.primefaces.shaded.json.JSONString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.TreeNode;
import com.java.backend.mediator.ContactInfo.ContactInfo;
import com.java.backend.mediator.ContactInfo.ContactInfoController;
import com.java.backend.mediator.Document.Document;
import com.java.backend.mediator.Document.Document.DocumentType;
import com.java.backend.mediator.Profile.Profile;
import com.java.backend.mediator.Profile.ProfileController;
import com.java.backend.mediator.Profile.ProfileService;
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
    private String document;

	private Map<String,String> services;
	private Map<String,String> documents;
	
	public String login() {
		try {
			List<User> users = userService.getAllUsers();
			Optional<User> user = users.stream().filter(Objects::nonNull).filter(u->u.getUserName().equals(username) && u.getPassword().equals(password)).findFirst();
				if (user.isPresent()) {
		
					// set current user session
					searchManagedBean.setCurrentUser(userService.getUser(user.get().getId()));
					
					if(user.get().getUserType().equals(UserType.CONSUMER)) {
						return "consumer.xhtml";
					}else {
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
			
 			clearall();
			return "consumer.xhtml";
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
	
	public ButtonManagedBean() {
		services  = new HashMap<String, String>();
        services.put("Health Care", "Health_Care");
        services.put("House Cleaning", "House_Cleaning");
        services.put("Dog Walker", "Dog_Walker");
        
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
	
	public Map<String, String> getDocuments() {
		return documents;
	}

	public void setDocuments(Map<String, String> documents) {
		this.documents = documents;
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
