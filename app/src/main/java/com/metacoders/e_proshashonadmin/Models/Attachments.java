package com.metacoders.e_proshashonadmin.Models;

import java.io.Serializable;

public class Attachments  implements Serializable {
    String image_1 , image_2 , image_3 , file_1 ;

    public Attachments() {
    }

    public Attachments(String image_1, String image_2, String image_3, String file_1) {
        this.image_1 = image_1;
        this.image_2 = image_2;
        this.image_3 = image_3;
        this.file_1 = file_1;
    }

    public String getImage_1() {
        return image_1;
    }

    public void setImage_1(String image_1) {
        this.image_1 = image_1;
    }

    public String getImage_2() {
        return image_2;
    }

    public void setImage_2(String image_2) {
        this.image_2 = image_2;
    }

    public String getImage_3() {
        return image_3;
    }

    public void setImage_3(String image_3) {
        this.image_3 = image_3;
    }

    public String getFile_1() {
        return file_1;
    }

    public void setFile_1(String file_1) {
        this.file_1 = file_1;
    }
}
