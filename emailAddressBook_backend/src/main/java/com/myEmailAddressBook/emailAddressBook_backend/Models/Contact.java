package com.myEmailAddressBook.emailAddressBook_backend.Models;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.boot.autoconfigure.info.ProjectInfoProperties.Build;

import jakarta.json.Json;
import jakarta.json.JsonObject;

public class Contact {

    private String userId;
    private String name;
    private String email;
    private Integer mobile;
    private String profile_pic_url;
    private String hobbies;

    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public Integer getMobile() {
        return mobile;
    }
    public void setMobile(Integer mobile) {
        this.mobile = mobile;
    }
    public String getProfile_pic_url() {
        return profile_pic_url;
    }
    public void setProfile_pic_url(String profile_pic_url) {
        this.profile_pic_url = profile_pic_url;
    }
    public String getHobbies() {
        return hobbies;
    }
    public void setHobbies(String hobbies) {
        this.hobbies = hobbies;
    }
    @Override
    public String toString() {
        return "Contact [email=" + email + ", mobile=" + mobile + ", name=" + name + ", profile_pic_url="
                + profile_pic_url + ", userId=" + userId + "]";
    }

    public static Contact createContact(ResultSet rs ) throws SQLException {
        Contact contact = new Contact();

        contact.setUserId(rs.getString("userId"));
        contact.setName(rs.getString("name"));
        contact.setEmail(rs.getString("email"));
        contact.setMobile(Integer.parseInt(rs.getString("mobile")));
        contact.setProfile_pic_url(rs.getString("profile_pic_url"));
        contact.setHobbies(rs.getString("hobbies"));

        return contact;
    }

    public JsonObject toJson(){
        return Json.createObjectBuilder()
            .add("userId", userId)
            .add("name",name)
            .add("email", email)
            .add("mobile", mobile)
            .add("profile_pic_url", profile_pic_url)
            .add("hobbies", hobbies)
            .build();
    }

    

    
    
}
