package com.metacoders.e_proshashonadmin.Models;

public class UserModel {
    String user_pic , name , number  , user_id , not_id , gender ,user_district ,user_division ,user_union_porusova , user_thana_upzila ;

    public UserModel() {
    }

    public UserModel(String user_pic, String name, String number, String user_id, String not_id, String gender, String user_district, String user_division, String user_union_porusova, String user_thana_upzila) {
        this.user_pic = user_pic;
        this.name = name;
        this.number = number;
        this.user_id = user_id;
        this.not_id = not_id;
        this.gender = gender;
        this.user_district = user_district;
        this.user_division = user_division;
        this.user_union_porusova = user_union_porusova;
        this.user_thana_upzila = user_thana_upzila;
    }

    public String getUser_district() {
        return user_district;
    }

    public void setUser_district(String user_district) {
        this.user_district = user_district;
    }

    public String getUser_division() {
        return user_division;
    }

    public void setUser_division(String user_division) {
        this.user_division = user_division;
    }

    public String getUser_union_porusova() {
        return user_union_porusova;
    }

    public void setUser_union_porusova(String user_union_porusova) {
        this.user_union_porusova = user_union_porusova;
    }

    public String getUser_thana_upzila() {
        return user_thana_upzila;
    }

    public void setUser_thana_upzila(String user_thana_upzila) {
        this.user_thana_upzila = user_thana_upzila;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getNot_id() {
        return not_id;
    }

    public void setNot_id(String not_id) {
        this.not_id = not_id;
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
