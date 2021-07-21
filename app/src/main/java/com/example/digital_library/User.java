package com.example.digital_library;


import java.io.Serializable;

//user class

public class User implements Serializable{

    private String userID;
    private String email;
    private String firstname;
    private String lastname;
    private String password;
    private String gender;
    private String bDate;
    private String phoneNo;
    private byte[] photo;

    public User() {
    }

    public User(String userID, String email, String firstname,String lastname, String password, String gender, String bDate, String phoneNo,byte[] photo) {
        this.userID = userID;
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        this.password = password;
        this.gender = gender;
        this.bDate = bDate;
        this.phoneNo = phoneNo;
        this.photo=photo;
    }
    public String getuserID() {
        return userID ;
    }

    public void  setuserID(String  userID) {
        this. userID =  userID;
    }

    public String getEmail() {
        return email;
    }

    public void  setEmail(String  email) {
        this. email =  email;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBdate() {
        return bDate;
    }

    public void setBdate(String bDate) {
        this.bDate = bDate;
    }


    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }
}
