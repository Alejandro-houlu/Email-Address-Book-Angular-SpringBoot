package com.myEmailAddressBook.emailAddressBook_backend.Services;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.myEmailAddressBook.emailAddressBook_backend.Models.Contact;
import com.myEmailAddressBook.emailAddressBook_backend.Repositories.contactRepository;

@Service
public class contactImplementation implements contactInterface {

    @Autowired
    contactRepository contactRepo;

    @Autowired
    AmazonS3 s3;

    @Override
    public List<Contact> getAllContacts() {
        
        return contactRepo.getAllContact();
    }


    @Override
    public String uploadImageToS3(Contact contact, MultipartFile profilePic) {

        String objId = UUID.randomUUID().toString().substring(0,8);
        String bucketName = "alejandrobucket";
        Map<String, String> userCustomMetadata = new HashMap<>();
        userCustomMetadata.put("uploader",contact.getName());

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(profilePic.getContentType());
        metadata.setContentLength(profilePic.getSize());
        metadata.setUserMetadata(userCustomMetadata);


        try {
            PutObjectRequest putReq = new PutObjectRequest(bucketName,"AddressBookApp/%s/images/%s".formatted("AddressBookApp"+contact.getName(), objId), 
            profilePic.getInputStream(), metadata);
            putReq.setCannedAcl(CannedAccessControlList.PublicRead);
            s3.putObject(putReq);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String url = s3.getUrl(bucketName, "AddressBookApp/%s/images/%s".formatted("AddressBookApp"+contact.getName(), objId)).toString();
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + url);

        return url;

    }


    @Override
    public Contact saveContact(Contact contact) throws SQLException {
       
        return contactRepo.saveContact(contact);

        
    }


    @Override
    public Optional<Contact> getContactByUserId(String userId) {

        Optional<Contact> opt = contactRepo.getContactByUserId(userId);
        return opt;

    }


    @Override
    public String deleteContactByUserid(String userId) {
        
        return contactRepo.deleteContactByUserId(userId);
    }
    
}
