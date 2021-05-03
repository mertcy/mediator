package com.java.backend.mediator;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.primefaces.model.file.UploadedFile;
import org.primefaces.model.file.UploadedFiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.java.backend.mediator.ContactInfo.ContactInfo;
import com.java.backend.mediator.ContactInfo.ContactInfoController;
import com.java.backend.mediator.User.User;
import com.java.backend.mediator.User.User.UserType;
import com.java.backend.mediator.User.UserController;


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
    private UploadedFiles files;
    private String userType;
    //private Map<String,Map<String,String>> data = new HashMap<String, Map<String,String>>();
    private String service;

	private Map<String,String> services;


	public String login() {
		try {
			List<User> users = userService.getAllUsers();
			Optional<User> user = users.stream().filter(u->u.getUserName().equals(username) && u.getPassword().equals(password)).findFirst();
				if (user.isPresent()) {
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
			User signupUser = userService.saveUser(user);
			ContactInfo contact = new ContactInfo();
			contact.setName(name);
			contact.setLastName(surname);
			contact.setAddress(address);
			contact.setBirthDate(birthDate.toLocaleString());
			contact.setGender(gender);
			contact.setNationality(nationality);
			contact.setId(signupUser.getId());
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

	}

	public String logout() {
		return "login.xhtml";
	}

	public String redirect() {
		return "menu.xhtml";
	}
	
	public void onServiceChange() {
		//TODO document upload change
	}

	public ButtonManagedBean() {
		services  = new HashMap<String, String>();
        services.put("Health Care", "Health_Care");
        services.put("House Cleaning", "House_Cleaning");
        services.put("Dog Walker", "Dog_Walker");
	}
	public String getService() {
		return service;
	}
	
	public void setService(String service) {
		this.service = service;
	}
	
	public Map<String, String> getServices() {
		return services;
	}
	
	public void setServices(Map<String, String> services) {
		this.services = services;
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

	public UploadedFiles getFiles() {
		return files;
	}

	public void setFiles(UploadedFiles files) {
		this.files = files;
	}

}
