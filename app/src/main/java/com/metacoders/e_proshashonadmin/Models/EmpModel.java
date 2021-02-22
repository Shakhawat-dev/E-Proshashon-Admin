package com.metacoders.e_proshashonadmin.Models;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class EmpModel implements Serializable {

    String emp_name = "NULL", emp_role = "NULL", emp_pp = "NULL", emp_ph = "NULL", emp_mail = "NULL", emp_uid = "", emp_not_uid = "NULL",
            emp_password = "NULL", department = "NULL", upzila = "NULL", department_name = "NULL", role_list = "NULL";

    public EmpModel() {
    }

    public EmpModel(String emp_name, String emp_role, String emp_pp, String emp_ph, String emp_mail, String emp_uid,
                    String emp_not_uid, String emp_password, String department, String upzila, String department_name,
                    String role_list) {
        this.emp_name = emp_name;
        this.emp_role = emp_role;
        this.emp_pp = emp_pp;
        this.emp_ph = emp_ph;
        this.emp_mail = emp_mail;
        this.emp_uid = emp_uid;
        this.emp_not_uid = emp_not_uid;
        this.emp_password = emp_password;
        this.department = department;
        this.upzila = upzila;
        this.department_name = department_name;
        this.role_list = role_list;
    }

    public String getRole_list() {
        return role_list;
    }

    public void setRole_list(String role_list) {
        this.role_list = role_list;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getUpzila() {
        return upzila;
    }

    public void setUpzila(String upzila) {
        this.upzila = upzila;
    }

    public String getEmp_uid() {
        return emp_uid;
    }

    public void setEmp_uid(String emp_uid) {
        this.emp_uid = emp_uid;
    }

    public String getEmp_not_uid() {
        return emp_not_uid;
    }

    public void setEmp_not_uid(String emp_not_uid) {
        this.emp_not_uid = emp_not_uid;
    }

    public String getEmp_password() {
        return emp_password;
    }

    public void setEmp_password(String emp_password) {
        this.emp_password = emp_password;
    }

    public String getEmp_name() {
        return emp_name;
    }

    public void setEmp_name(String emp_name) {
        this.emp_name = emp_name;
    }

    public String getEmp_role() {
        return emp_role;
    }

    public void setEmp_role(String emp_role) {
        this.emp_role = emp_role;
    }

    public String getEmp_pp() {
        return emp_pp;
    }

    public void setEmp_pp(String emp_pp) {
        this.emp_pp = emp_pp;
    }

    public String getEmp_ph() {
        return emp_ph;
    }

    public void setEmp_ph(String emp_ph) {
        this.emp_ph = emp_ph;
    }

    public String getEmp_mail() {
        return emp_mail;
    }

    public void setEmp_mail(String emp_mail) {
        this.emp_mail = emp_mail;
    }

    public String getDepartment_name() {
        return department_name;
    }

    public void setDepartment_name(String department_name) {
        this.department_name = department_name;
    }

    @NonNull
    @Override
    public String toString() {
        return emp_name.toString();
    }
}
