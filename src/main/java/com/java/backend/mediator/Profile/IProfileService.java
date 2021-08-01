package com.java.backend.mediator.Profile;

import com.java.backend.mediator.Document.Document;

public interface IProfileService {
    Profile findProfileByUserId(String id);
    Profile saveProfile(Profile profile);
    Profile saveDocument(String id, Document document);  
}
