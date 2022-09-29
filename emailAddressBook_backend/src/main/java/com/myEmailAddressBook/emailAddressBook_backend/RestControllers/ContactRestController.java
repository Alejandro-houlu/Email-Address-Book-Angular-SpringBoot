package com.myEmailAddressBook.emailAddressBook_backend.RestControllers;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.myEmailAddressBook.emailAddressBook_backend.Models.Contact;
import com.myEmailAddressBook.emailAddressBook_backend.Services.contactInterface;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;

@RestController
@RequestMapping(path="/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class ContactRestController {

    @Autowired
    contactInterface contactSvc;

    Logger logger = LoggerFactory.getLogger(ContactRestController.class); 

    @GetMapping("/contacts")
    public ResponseEntity<String> getAllContacts(){

        List<Contact> allContacts = contactSvc.getAllContacts();

        JsonArrayBuilder arrBuilder = Json.createArrayBuilder();
        for(Contact contact : allContacts){
            arrBuilder.add(contact.toJson());
        }

        return ResponseEntity.status(HttpStatus.OK).body(arrBuilder.build().toString());
        
        //.ok(arrBuilder.build().toString());

    }

    @PostMapping("/contact/save")
    public ResponseEntity<String> saveContact(@RequestPart String name, @RequestPart String email,
        @RequestPart String mobile, @RequestPart MultipartFile profilePic, @RequestPart String hobbies){

        logger.info(name);
        logger.info(email);
        logger.info(mobile);
        logger.info(profilePic.getOriginalFilename());
        logger.info(hobbies);

        Contact contact = new Contact();
        contact.setName(name);
        contact.setEmail(email);
        contact.setHobbies(hobbies);
        contact.setMobile(Integer.valueOf(mobile));

        if(profilePic.getOriginalFilename().isEmpty()){
            contact.setProfile_pic_url("/assets/images/default-avatar.jpg");
        }
        else{
            String imgUrl = contactSvc.uploadImageToS3(contact, profilePic);
            contact.setProfile_pic_url(imgUrl);
        }

        Contact newContact = new Contact();

        try {
            newContact = contactSvc.saveContact(contact);
        } catch (SQLException e) {
            
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Add contact unsuccessful");
        }


        // JsonObject data = Json.createObjectBuilder()
        
        //     .add("userId", newContact.getUserId())
        //     .add("name", newContact.getName())
        //     .add("email", newContact.getEmail())
        //     .add("mobile", newContact.getMobile())
        //     .add("profile_pic_url", newContact.getProfile_pic_url())
        //     .add("hobbies", newContact.getHobbies())
        //     .build();
        
        return ResponseEntity.status(HttpStatus.OK).body(newContact.toJson().toString());
    }

    @GetMapping("/contact/{userId}")
    public ResponseEntity<String> getContactByUserId(@PathVariable String userId){

        Optional<Contact> opt = contactSvc.getContactByUserId(userId);

        if(opt.isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Contact does not exist");
        }

        return ResponseEntity.status(HttpStatus.OK).body(opt.get().toJson().toString());
    }

    @PostMapping("/contact/delete")
    public ResponseEntity<String> deleteContact(@RequestBody String userId){

        String message = contactSvc.deleteContactByUserid(userId);

        logger.info(message);

        JsonObject responseMsg = Json.createObjectBuilder()
            .add("message", message)
            .build();


        return ResponseEntity.status(HttpStatus.OK).body(responseMsg.toString());
    }
    
}
