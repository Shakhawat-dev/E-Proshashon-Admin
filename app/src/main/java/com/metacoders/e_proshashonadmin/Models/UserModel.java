package com.metacoders.e_proshashonadmin.Models;

public class UserModel {
    String user_pic , name , number  , user_id ;

    public UserModel() {
    }

    public UserModel(String user_pic, String name, String number, String user_id) {
        this.user_pic = user_pic;
        this.name = name;
        this.number = number;
        this.user_id = user_id;
    }


    public String getUser_pic() {
        return user_pic;
    }

    public void setUser_pic(String user_pic) {
        this.user_pic = user_pic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
