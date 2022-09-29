package com.myEmailAddressBook.emailAddressBook_backend.Services;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import com.myEmailAddressBook.emailAddressBook_backend.Models.Contact;

public interface contactInterface {
    
    public List<Contact> getAllContacts();
    public String uploadImageToS3(Contact contact, MultipartFile profilePic);
    public Contact saveContact(Contact contact)throws SQLException;
    public Optional<Contact> getContactByUserId(String userId);
    public String deleteContactByUserid(String userId);
}
