package com.java.backend.mediator.Document;

import org.springframework.data.annotation.Transient;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.java.backend.mediator.Model.Model;

public class Document extends Model {

	@Transient
	public static final String DISCRIMINATOR = "Document";
	
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	@JsonProperty("documentType")
	public DocumentType documentType;
	
	@JsonProperty("documentTitle")
	public String documentTitle;
	
	@JsonProperty("documentDescription")
	public String documentDescription;

	@JsonProperty("documentLink")
	public String documentLink;
    
    public Document() {
    	super();
    }
    
	public enum DocumentType {
		DOG_TRAINING_CERTIFICATE,
		DIPLOMA,
		JUDICIAL_RECORD,
		WORK_PERMIT_CERTIFICATE
	}

	public DocumentType getDocumentType() {
		return documentType;
	}

	public void setDocumentType(DocumentType documentType) {
		this.documentType = documentType;
	}

	public String getDocumentTitle() {
		return documentTitle;
	}

	public void setDocumentTitle(String documentTitle) {
		this.documentTitle = documentTitle;
	}

	public String getDocumentDescription() {
		return documentDescription;
	}

	public void setDocumentDescription(String documentDescription) {
		this.documentDescription = documentDescription;
	}

	public String getDocumentLink() {
		return documentLink;
	}

	public void setDocumentLink(String documentLink) {
		this.documentLink = documentLink;
	}

}
