package com.metacoders.e_proshashonadmin.Models;

import java.io.Serializable;

public class ComplainModel implements Serializable {
    String name;
    String complain_status;
    String user_mobile;
    String user_gender;
    String user_division;
    String user_district;
    String user_thana_upzila;
    String user_union_porusova;
    String user_id;
    String post_id;
    String complain_division;
    String complain_district;
    String complain_thana_upzilla;
    String complain_officer_type;
    String complain_officer_name;
    String complain_officer_mobile;
    String complain_type;
    String complain_desc;
    String complain_ref_link;
    Attachments attachments;
    String comment;
    String department;
    String emp_uid;
    String emp_role;
    String complain_officer_department_name = "NULL" ;
    String complain_date = "";
    String assignedTo = "" ;
    String assignedBy = "" ;

    public ComplainModel() {
    }

    public ComplainModel(String name, String complain_status, String user_mobile, String user_gender, String user_division, String user_district, String user_thana_upzila, String user_union_porusova, String user_id, String post_id, String complain_division, String complain_district, String complain_thana_upzilla, String complain_officer_type, String complain_officer_name, String complain_officer_mobile, String complain_type, String complain_desc, String complain_ref_link, Attachments attachments, String comment, String department, String emp_uid, String emp_role, String complain_officer_department_name, String complain_date, String assignedTo, String assignedBy) {
        this.name = name;
        this.complain_status = complain_status;
        this.user_mobile = user_mobile;
        this.user_gender = user_gender;
        this.user_division = user_division;
        this.user_district = user_district;
        this.user_thana_upzila = user_thana_upzila;
        this.user_union_porusova = user_union_porusova;
        this.user_id = user_id;
        this.post_id = post_id;
        this.complain_division = complain_division;
        this.complain_district = complain_district;
        this.complain_thana_upzilla = complain_thana_upzilla;
        this.complain_officer_type = complain_officer_type;
        this.complain_officer_name = complain_officer_name;
        this.complain_officer_mobile = complain_officer_mobile;
        this.complain_type = complain_type;
        this.complain_desc = complain_desc;
        this.complain_ref_link = complain_ref_link;
        this.attachments = attachments;
        this.comment = comment;
        this.department = department;
        this.emp_uid = emp_uid;
        this.emp_role = emp_role;
        this.complain_officer_department_name = complain_officer_department_name;
        this.complain_date = complain_date;
        this.assignedTo = assignedTo;
        this.assignedBy = assignedBy;
    }

    public String getComplain_date() {
        return complain_date;
    }

    public String getAssignedBy() {
        return assignedBy;
    }

    public void setAssignedBy(String assignedBy) {
        this.assignedBy = assignedBy;
    }

    public String getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(String assignedTo) {
        this.assignedTo = assignedTo;
    }

    public void setComplain_date(String complain_date) {
        this.complain_date = complain_date;
    }

    public String getComplain_officer_department_name() {
        return complain_officer_department_name;
    }

    public void setComplain_officer_department_name(String complain_officer_department_name) {
        this.complain_officer_department_name = complain_officer_department_name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComplain_status() {
        return complain_status;
    }

    public void setComplain_status(String complain_status) {
        this.complain_status = complain_status;
    }

    public String getUser_mobile() {
        return user_mobile;
    }

    public void setUser_mobile(String user_mobile) {
        this.user_mobile = user_mobile;
    }

    public String getUser_gender() {
        return user_gender;
    }

    public void setUser_gender(String user_gender) {
        this.user_gender = user_gender;
    }

    public String getUser_division() {
        return user_division;
    }

    public void setUser_division(String user_division) {
        this.user_division = user_division;
    }

    public String getUser_district() {
        return user_district;
    }

    public void setUser_district(String user_district) {
        this.user_district = user_district;
    }

    public String getUser_thana_upzila() {
        return user_thana_upzila;
    }

    public void setUser_thana_upzila(String user_thana_upzila) {
        this.user_thana_upzila = user_thana_upzila;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getEmp_uid() {
        return emp_uid;
    }

    public void setEmp_uid(String emp_uid) {
        this.emp_uid = emp_uid;
    }

    public String getEmp_role() {
        return emp_role;
    }

    public void setEmp_role(String emp_role) {
        this.emp_role = emp_role;
    }

    public String getUser_union_porusova() {
        return user_union_porusova;
    }

    public void setUser_union_porusova(String user_union_porusova) {
        this.user_union_porusova = user_union_porusova;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getPost_id() {
        return post_id;
    }

    public void setPost_id(String post_id) {
        this.post_id = post_id;
    }

    public String getComplain_division() {
        return complain_division;
    }

    public void setComplain_division(String complain_division) {
        this.complain_division = complain_division;
    }

    public String getComplain_district() {
        return complain_district;
    }

    public void setComplain_district(String complain_district) {
        this.complain_district = complain_district;
    }

    public String getComplain_thana_upzilla() {
        return complain_thana_upzilla;
    }

    public void setComplain_thana_upzilla(String complain_thana_upzilla) {
        this.complain_thana_upzilla = complain_thana_upzilla;
    }

    public String getComplain_officer_type() {
        return complain_officer_type;
    }

    public void setComplain_officer_type(String complain_officer_type) {
        this.complain_officer_type = complain_officer_type;
    }

    public String getComplain_officer_name() {
        return complain_officer_name;
    }

    public void setComplain_officer_name(String complain_officer_name) {
        this.complain_officer_name = complain_officer_name;
    }

    public String getComplain_officer_mobile() {
        return complain_officer_mobile;
    }

    public void setComplain_officer_mobile(String complain_officer_mobile) {
        this.complain_officer_mobile = complain_officer_mobile;
    }

    public String getComplain_type() {
        return complain_type;
    }

    public void setComplain_type(String complain_type) {
        this.complain_type = complain_type;
    }

    public String getComplain_desc() {
        return complain_desc;
    }

    public void setComplain_desc(String complain_desc) {
        this.complain_desc = complain_desc;
    }

    public String getComplain_ref_link() {
        return complain_ref_link;
    }

    public void setComplain_ref_link(String complain_ref_link) {
        this.complain_ref_link = complain_ref_link;
    }

    public Attachments getAttachments() {
        return attachments;
    }

    public void setAttachments(Attachments attachments) {
        this.attachments = attachments;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
