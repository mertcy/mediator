package com.java.backend.mediator.ServiceProvided;

import org.springframework.data.annotation.Transient;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

public class DogWalkerService extends ServiceProvided {

	@Transient
	public static final String DISCRIMINATOR = "DogWalkerService";
	
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	@JsonProperty("dogBreed")
	public DogBreed dogBreed;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	@JsonProperty("dogActivityLevel")
	public DogActivityLevel dogActivityLevel;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	@JsonProperty("dogBarkingLevel")
	public DogBarkingLevel dogBarkingLevel;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	@JsonProperty("dogCoatType")
	public DogCoatType dogCoatType;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	@JsonProperty("dogShedding")
	public DogShedding dogShedding;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	@JsonProperty("dogSize")
	public DogSize dogSize;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	@JsonProperty("dogTrainability")
	public DogTrainability dogTrainability;
	
	public enum DogBreed {
		NOT_AVAILABLE,
		CHIHUAHUA,
		LABRADOR_RETRIEVER,
		GOLDEN_RETRIEVER,
		BULLDOG,
		PITBULL,
		MIX
	}

	public enum DogActivityLevel {
		NOT_AVAILABLE,
		NEEDS_LOTS_OF_ACTIVITY,
		REGULAR_EXERCISE,
		ENERGETIC,
		CALM
	} 

	public enum DogBarkingLevel {
		NOT_AVAILABLE,
		WHEN_NECESSARY,
		INFREQUENT,
		MEDIUM,
		FREQUENT,
		LIKES_TO_BE_VOCAL
	}   	
	
	public enum DogCoatType {
		NOT_AVAILABLE,
		HAIRLESS,
		SHORT,
		MEDIUM,
		LONG,
		SMOOTH,
		WIRE
	}
	
	public enum DogShedding {
		NOT_AVAILABLE,
		INFREQUENT,
		SEASONAL,
		FREQUENT,
		OCCASIONAL,
		REGULARLY
	}
	
	public enum DogSize {
		NOT_AVAILABLE,
		XSMALL,
		SMALL,
		MEDIUM,
		LARGE,
		XLARGE
	}
	
	public enum DogTrainability {
		NOT_AVAILABLE,
		MAY_BE_STUBBORN,
		AGREEABLE,
		EAGER_TO_PLEASE,
		INDEPENDENT,
		EASY_TRAINING
	}
	
	public DogWalkerService() {
    	super(ServiceType.DOG_WALKER_SERVICE);
        this.dogBreed = DogBreed.NOT_AVAILABLE;
        this.dogActivityLevel = DogActivityLevel.NOT_AVAILABLE;
        this.dogBarkingLevel = DogBarkingLevel.NOT_AVAILABLE;
        this.dogCoatType = DogCoatType.NOT_AVAILABLE;
        this.dogShedding = DogShedding.NOT_AVAILABLE;
        this.dogSize = DogSize.NOT_AVAILABLE;
        this.dogTrainability = DogTrainability.NOT_AVAILABLE;
    }
	
    public DogBreed getDogBreed() {
		return dogBreed;
	}

	public void setDogBreed(DogBreed dogBreed) {
		this.dogBreed = dogBreed;
	}

	public DogActivityLevel getDogActivityLevel() {
		return dogActivityLevel;
	}

	public void setDogActivityLevel(DogActivityLevel dogActivityLevel) {
		this.dogActivityLevel = dogActivityLevel;
	}

	public DogBarkingLevel getDogBarkingLevel() {
		return dogBarkingLevel;
	}

	public void setDogBarkingLevel(DogBarkingLevel dogBarkingLevel) {
		this.dogBarkingLevel = dogBarkingLevel;
	}

	public DogCoatType getDogCoatType() {
		return dogCoatType;
	}

	public void setDogCoatType(DogCoatType dogCoatType) {
		this.dogCoatType = dogCoatType;
	}

	public DogShedding getDogShedding() {
		return dogShedding;
	}

	public void setDogShedding(DogShedding dogShedding) {
		this.dogShedding = dogShedding;
	}

	public DogSize getDogSize() {
		return dogSize;
	}

	public void setDogSize(DogSize dogSize) {
		this.dogSize = dogSize;
	}

	public DogTrainability getDogTrainability() {
		return dogTrainability;
	}

	public void setDogTrainability(DogTrainability dogTrainability) {
		this.dogTrainability = dogTrainability;
	}

}
