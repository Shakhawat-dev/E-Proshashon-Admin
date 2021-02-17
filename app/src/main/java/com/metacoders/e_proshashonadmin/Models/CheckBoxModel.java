package com.metacoders.e_proshashonadmin.Models;

public class CheckBoxModel {
    String role  ;
    boolean isCheck ;
    int pos = -1 ;

    public CheckBoxModel() {
    }

    public CheckBoxModel(String role, boolean isCheck, int pos) {
        this.role = role;
        this.isCheck = isCheck;
        this.pos = pos;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }
}
