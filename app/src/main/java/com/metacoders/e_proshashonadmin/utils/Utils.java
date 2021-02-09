package com.metacoders.e_proshashonadmin.utils;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class Utils {


    public   static  String COMPLAIN_REPO = "complain_box" ;

    public  static String convertItTohash(String password){
        String hashStr = "password"  ;
        try {
           MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            byte hashBytes[] = messageDigest.digest(password.getBytes(StandardCharsets.UTF_8));
            BigInteger noHash = new BigInteger(1, hashBytes);
            hashStr = noHash.toString(16);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }


        return hashStr ;
    }
}
