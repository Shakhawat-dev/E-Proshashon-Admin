package com.metacoders.e_proshashonadmin.utils;

import android.content.Context;
import android.util.Log;

import com.metacoders.e_proshashonadmin.Models.ComplainModel;
import com.metacoders.e_proshashonadmin.Models.EmpModel;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Utils {
/*
      /*
        অতিরিক্ত জেলা ম্যাজিস্ট্রেট
অতিরিক্ত জেলা প্রশাসক (রাজস্ব)
অতিরিক্ত জেলা প্রশাসক (সার্বিক)
উপ পরিচালক, স্থানীয় সরকার (চলতি দায়িত্ব)
অতিরিক্ত জেলা প্রশাসক (শিক্ষা ও আইসিটি)
         */


    public static String COMPLAIN_REPO = "complain_box";
    public static String[] disrictDesignationList = new String[]{
            "নির্বাচন করুন",
            "জেলা প্রশাসক_জেলা প্রশাসকের কার্যালয়",
            "পুলিশ সুপার_পুলিশ সুপারের কার্যালয়",
            "অতিরিক্ত জেলা ম্যাজিস্ট্রেট_জেলা প্রশাসকের কার্যালয়",
            "অতিরিক্ত জেলা প্রশাসক (রাজস্ব)_জেলা প্রশাসকের কার্যালয়",
            "উপ পরিচালক,স্থানীয় সরকার (চলতি দায়িত্ব)_জেলা প্রশাসকের কার্যালয়",
            "অতিরিক্ত জেলা প্রশাসক (শিক্ষা ও আইসিটি)_জেলা প্রশাসকের কার্যালয়",
            "কোম্পানী কমান্ডার সিপিসি-৩_র\u200C্যাপিড এ্যাকশন ব্যাটালিয়ন (র\u200C্যাব)",
            "জেলা কমান্ড্যান্ট (অতিঃ দায়িত্ব)_আনসার ও গ্রাম প্রতিরক্ষা",
            "সহকারী পরিচালক_মাদকদ্রব্য নিয়ন্ত্রন অধিদপ্তর",
            "জেলা নির্বাচন অফিসার_জেলা নির্বাচন অফিস",
            "সহকারী পরিচালক_পাসপোর্ট অফিস",
            "উপ-পরিচালক_দুর্নীতি দমন কমিশন",
            "উপপরিচালক_জেলা সমাজসেবা কার্যালয়",
            "সিভিল সার্জন_জেলা সিভিল সার্জনের কার্যালয়",
            "উপ-পরিচালক (ভারপ্রাপ্ত)_পরিবার পরিকল্পনা",
            "নির্বাহী প্রকৌশলী_গণপূর্ত বিভাগ",
            "উপ-বিভাগীয় প্রকৌশলী_সড়ক ও জনপথ",
            "নির্বাহী প্রকৌশলী_স্থানীয় সরকার প্রকৌশল অধিদপ্তর",
            "নির্বাহী প্রকৌশলী_বিদ্যুৎ উন্নয়ন বোর্ড",
            "নির্বাহী প্রকৌশলী_জনস্বাস্থ্য প্রকৌশল অধিদপ্তর",
            "নির্বাহী প্রকৌশলী_নির্বাহী প্রকৌশলীর অফিস বিএডিসি,সেচ",
            "সহকারী পরিচালক (ইঞ্জিনিয়ারিং)_বিআরটিএ",
            "প্রধান নির্বাহী_রেলওয়ে",
            "উপ-পরিচালক(অ.দা.)_জেলা মহিলা বিষয়ক কর্মকর্তার কার্যালয়",
            "জেলা নির্বাহী অফিসার_জাতীয় মহিলা সংস্থা,জেলা অফিস",
            "উপপরিচালক (ভারপ্রাপ্ত)_কৃষি সম্প্রসারণ অধিদপ্তর",
            "জেলা খাদ্য নিয়ন্ত্রক_জেলা খাদ্য নিয়ন্ত্রকের কার্যালয়",
            "মৎস্য কর্মকর্তা_জেলা মৎস্য অফিস",
            "জেলা প্রাণিসম্পদ কর্মকর্তা_জেলা প্রাণিসম্পদ অফিস",
            "জেলা প্রাথমিক শিক্ষা অফিসার_জেলা প্রাথমিক শিক্ষা অফিস",
            "জেলা শিক্ষা অফিসার_জেলা শিক্ষা অফিস",
            "প্রোগ্রামার_তথ্য ও যোগাযোগ প্রযুক্তি অধিদপ্তর",
            "বিভাগীয় বন কর্মকর্তা_বন বিভাগ",
            "জেলা ত্রাণ ও পুনর্বাসন কর্মকর্তা_জেলা ত্রান ও পনর্বাসন অফিস",
            "সহকারী পরিচালক_জাতীয় ভোক্তা অধিকার সংরক্ষণ অধিদপ্তর"


    };
    public static String[] upzillaDesignationList = new String[]{
            "নির্বাচন করুন",
            "উপজেলা নির্বাহী অফিসার_উপজেলা নির্বাহী অফিসারের কার্যালয়",
            "ভারপ্রাপ্ত কর্মকর্তা-ওসি_পুলিশ সুপারের কার্যালয়",
            "উপজেলা আনসার ও ভিডিপি প্রশিক্ষক_আনসার ও গ্রাম প্রতিরক্ষা",
            "মাদকদ্রব্য নিয়ন্ত্রন কর্মকর্তা_মাদকদ্রব্য নিয়ন্ত্রন অধিদপ্তর",
            "উপজেলা নির্বাচন অফিসার_জেলা নির্বাচন অফিস",
            "পাসপোর্ট কর্মকর্তা_পাসপোর্ট অফিস",
            "দুর্নীতি দমন কমিশন কর্মকর্তা_দুর্নীতি দমন কমিশন",
            "উপজেলা অফিসের কর্মকর্তা_জেলা সমাজসেবা কার্যালয়",
            "মেডিকেল অফিসার_জেলা সিভিল সার্জনের কার্যালয়",
            "ইউএফপিও_পরিবার পরিকল্পনা",
            "গণপূর্ত কর্মকর্তা_গণপূর্ত বিভাগ",
            "সড়ক ও জনপথ কর্মকর্তা_সড়ক ও জনপথ",
            "উপজেলা প্রকৌশলী কর্মকর্তা_স্থানীয় সরকার প্রকৌশল অধিদপ্তর",
            "উপজেলা প্রকৌশলী কর্মকর্তা_বিদ্যুৎ উন্নয়ন বোর্ড",
            "উপজেলা প্রকৌশলী কর্মকর্তা_জনস্বাস্থ্য প্রকৌশল অধিদপ্তর",
            "উপজেলা প্রকৌশলী কর্মকর্তা_নির্বাহী প্রকৌশলীর অফিস বিএডিসি,সেচ",
            "বিআরটিএ কর্মকর্তা_বিআরটিএ",
            "রেলওয়ে কর্মকর্তা_রেলওয়ে",
            "মহিলা বিষয়ক কর্মকর্তা_জেলা মহিলা বিষয়ক কর্মকর্তার কার্যালয়",
            "উপজেলা অফিসার_জাতীয় মহিলা সংস্থা,জেলা অফিস",
            "উপজেলা কৃষি অফিসার_কৃষি সম্প্রসারণ অধিদপ্তর",
            "উপজেলা খাদ্য কর্মকর্তা_জেলা খাদ্য নিয়ন্ত্রকের কার্যালয়",
            "উপজেলা মৎস্য কর্মকর্তা_জেলা মৎস্য অফিস",
            "উপজেলা প্রাণিসম্পদ কর্মকর্তা_জেলা প্রাণিসম্পদ অফিস",
            "উপজেলা কর্মকর্তা_জেলা প্রাথমিক শিক্ষা অফিস",
            "উপজেলা কর্মকর্তা_জেলা শিক্ষা অফিস",
            "প্রোগ্রামার_তথ্য ও যোগাযোগ প্রযুক্তি অধিদপ্তর",
            "রেঞ্জ কর্মকর্তা_বন বিভাগ",
            "ত্রাণ ও পুনর্বাসন কর্মকর্তা_জেলা ত্রান ও পনর্বাসন অফিস",
            "জাতীয় ভোক্তা অধিকার সংরক্ষণ কর্মকর্তা_জাতীয় ভোক্তা অধিকার সংরক্ষণ অধিদপ্তর",

    };

    public static String convertItTohash(String password) {
        String hashStr = "password";
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            byte hashBytes[] = messageDigest.digest(password.getBytes(StandardCharsets.UTF_8));
            BigInteger noHash = new BigInteger(1, hashBytes);
            hashStr = noHash.toString(16);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }


        return hashStr;
    }


    public static List<EmpModel> FillterEmpModel(
            String department,
            String zila_upzila,
            String role,
            List<EmpModel> mainList
    ) {
        Log.d("EMP", "FillterEmpModel: " + department + " "  +zila_upzila + " " + role);
        List<EmpModel> fillteredList = new ArrayList<>();
        EmpModel model = new EmpModel()  ;
        model.setEmp_name("নির্বাচন করুন");
        model.setEmp_uid("");
        fillteredList.add(model);


        for (EmpModel item : mainList) {
            if (item.getDepartment().contains(department) &&
                    item.getUpzila().contains(zila_upzila) &&
                    item.getEmp_role().contains(role)
            ) {
                fillteredList.add(item);
            }
        }

        return fillteredList;

    }

    public static List<ComplainModel> FillterComplainModel(String uid, String upzila, String role,
                                                           List<ComplainModel> complainModelList, String status
            , String origINDepartment , String complain_type) {


        Log.d("TAG", "FillterComplainModel: " + uid + " " + upzila + " " );
        List<ComplainModel> fillteredList = new ArrayList<>();
        for (ComplainModel item : complainModelList) {
            if (item.getEmp_uid().contains(uid) &&
                    item.getComplain_thana_upzilla().contains(upzila) &&
                    item.getEmp_role().contains(role) && item.getComplain_status().contains(status)
                    && item.getComplain_officer_department_name().contains(origINDepartment)
                    && item.getComplain_type().contains(complain_type)
            ) {
                fillteredList.add(item);
            }
        }

        return fillteredList;
    }

    public static List<ComplainModel> FillterRegAdminComplainModel(String departmentName, String uid, String upzila,
                                                                   String role, List<ComplainModel> complainModelList ,
                                                                   String complainType , String status  ) {


        if(role.contains("_")){
            String[] a = role.split("_");
            role = a[0] ;
        }
        Log.d("TAG", "FillterComplainModel: " + uid + " " + upzila + " " + role + " " + departmentName );
        List<ComplainModel> fillteredList = new ArrayList<>();
        for (ComplainModel item : complainModelList) {
            if (item.getEmp_uid().contains(uid) &&
                    item.getComplain_thana_upzilla().contains(upzila) &&
                    item.getEmp_role().contains(role)
                    && item.getComplain_officer_department_name().contains(departmentName)
                    && item.getComplain_type().contains(complainType)
                    && item.getComplain_status().contains(status)
            ) {
                fillteredList.add(item);
            }
        }

        return fillteredList;
    }

    public static String getRole(Context context) {

        String ROLESTR = "";
        EmpModel model = SharedPrefManager.getInstance(context)
                .getUser();

        if (model != null) {
            String data = model.getEmp_role();
            List<String> role = Arrays.asList(data.split("_"));
            ROLESTR = role.get(0);
        }

        return ROLESTR;

    }
}




