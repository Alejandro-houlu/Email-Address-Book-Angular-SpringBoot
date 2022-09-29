package com.myEmailAddressBook.emailAddressBook_backend.Repositories;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.myEmailAddressBook.emailAddressBook_backend.Models.Contact;

@Repository
public class contactRepository {

    @Autowired
    JdbcTemplate jTemplate;

    private static final String SQL_GET_ALL_CONTACT = "select * from contact_info";
    private static final String SQL_SAVE_CONTACT = "insert into contact_info(name,email,mobile,profile_pic_url,hobbies) values (?,?,?,?,?)";
    private static final String SQL_GET_CONTACT_BY_EMAIL = "select * from contact_info where email=?";
    private static final String SQL_GET_CONTACT_BY_USERID = "select * from contact_info where userId=?";
    private static final String SQL_DELETE_CONTACT_BY_USERID ="delete from contact_info where userId=?";

    public List<Contact> getAllContact(){

        List<Contact> allContact = new ArrayList<>();

        jTemplate.query(SQL_GET_ALL_CONTACT, (ResultSet rs) ->{
            allContact.add(Contact.createContact(rs));
        });

        return allContact;
    }

    @Transactional
    public Contact saveContact(Contact contact)throws SQLException{

        jTemplate.update(SQL_SAVE_CONTACT, contact.getName(),contact.getEmail(),contact.getMobile(),contact.getProfile_pic_url(),contact.getHobbies());

        return getContactFromEmail(contact.getEmail()).get();

    }

    public Optional<Contact> getContactFromEmail(String email){

        return jTemplate.query(SQL_GET_CONTACT_BY_EMAIL, (ResultSet rs)->{
            if(!rs.next())
                return Optional.empty();
            return Optional.of(Contact.createContact(rs));
        }, email);


    }

    public Optional<Contact> getContactByUserId(String userId){

        return jTemplate.query(SQL_GET_CONTACT_BY_USERID, (ResultSet rs)->{
            if(!rs.next())
                return Optional.empty();
            return Optional.of(Contact.createContact(rs));
            
        }, userId);

    }

    public String deleteContactByUserId(String userId){
        Optional opt = getContactByUserId(userId);
        if(opt.isEmpty()){return "User not found";}

        return jTemplate.update(SQL_DELETE_CONTACT_BY_USERID, userId) == 0 ?  "Delete unsuccessful" : "One record deleted, userId= %s".formatted(userId);

    }

    
}
