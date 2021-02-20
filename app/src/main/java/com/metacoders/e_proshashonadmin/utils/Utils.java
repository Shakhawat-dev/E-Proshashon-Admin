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


    public static String COMPLAIN_REPO = "complain_box";
    public static String[] disrictDesignationList = new String[]{
            "নির্বাচন করুন",
            "DC জেলা প্রশাসক",
            "পুলিশ সুপার",
            "কোম্পানী কমান্ডার সিপিসি-৩",
            "জেলা কমান্ড্যান্ট (অতিঃ দায়িত্ব)",
            "সহকারী পরিচালক",
            "জেলা নির্বাচন অফিসার",
            "সহকারী পরিচালক",
            "উপ-পরিচালক",
            "উপপরিচালক",
            "সিভিল সার্জন",
            "উপ-পরিচালক (ভারপ্রাপ্ত)",
            "নির্বাহী প্রকৌশলী",
            "উপ-বিভাগীয় প্রকৌশলী",
            "নির্বাহী প্রকৌশলী",
            "নির্বাহী প্রকৌশলী",
            " নির্বাহী প্রকৌশলী",
            " নির্বাহী প্রকৌশলী",
            "সহকারী পরিচালক (ইঞ্জিনিয়ারিং)",
            " প্রধান নির্বাহী",
            " উপ-পরিচালক(অ.দা.)",
            " জেলা নির্বাহী অফিসার",
            "উপপরিচালক (ভারপ্রাপ্ত)",
            " জেলা খাদ্য নিয়ন্ত্রক",
            "মৎস্য কর্মকর্তা",
            "জেলা প্রাণিসম্পদ কর্মকর্তা",
            "জেলা প্রাথমিক শিক্ষা অফিসার",
            "জেলা শিক্ষা অফিসার",
            "প্রোগ্রামার",
            "বিভাগীয় বন কর্মকর্তা",
            "জেলা ত্রাণ ও পুনর্বাসন কর্মকর্তা",
            "সহকারী পরিচালক"


    };
    public static String[] upzillaDesignationList = new String[]{
            "নির্বাচন করুন",
            "UNO উপজেলা নির্বাহী কর্মকর্তা",
            "ভারপ্রাপ্ত কর্মকর্তা- ওসি",
            "উপজেলা আনসার ও ভিডিপি প্রশিক্ষক",
            "মাদকদ্রব্য নিয়ন্ত্রন কর্মকর্তা",
            "উপজেলা নির্বাচন অফিসার",
            "পাসপোর্ট কর্মকর্তা",
            "দুর্নীতি দমন কমিশন কর্মকর্তা",
            "উপজেলা অফিসের কর্মকর্তা",
            "মেডিকেল অফিসার",
            "ইউএফপিও",
            "গণপূর্ত কর্মকর্তা",
            "সড়ক ও জনপথ কর্মকর্তা",
            "উপজেলা প্রকৌশলী কর্মকর্তা",
            "উপজেলা প্রকৌশলী কর্মকর্তা",
            "উপজেলা প্রকৌশলী কর্মকর্তা",
            "উপজেলা প্রকৌশলী কর্মকর্তা",
            "বিআরটিএ কর্মকর্তা",
            "রেলওয়ে কর্মকর্তা",
            "মহিলা বিষয়ক কর্মকর্তা",
            "উপজেলা অফিসার",
            "উপজেলা কৃষি অফিসার",
            "উপজেলা খাদ্য কর্মকর্তা",
            "উপজেলা মৎস্য কর্মকর্তা",
            "উপজেলা প্রাণিসম্পদ কর্মকর্তা",
            "উপজেলা কর্মকর্তা",
            "উপজেলা কর্মকর্তা",
            "প্রোগ্রামার",
            "রেঞ্জ কর্মকর্তা",
            "ত্রাণ ও পুনর্বাসন কর্মকর্তা",
            "জাতীয় ভোক্তা অধিকার সংরক্ষণ কর্মকর্তা",

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
        List<EmpModel> fillteredList = new ArrayList<>();
        for (EmpModel item : mainList) {
            if (item.getDepartment().contains(department) &&
                    item.getUpzila().contains(zila_upzila) &&
                    item.getEmp_role().contains(role)) {
                fillteredList.add(item);
            }
        }

        return fillteredList;

    }

    public static List<ComplainModel> FillterComplainModel(String uid, String upzila, String role, List<ComplainModel> complainModelList) {

        Log.d("TAG", "FillterComplainModel: " + uid + " " + upzila + " " + role);
        List<ComplainModel> fillteredList = new ArrayList<>();
        for (ComplainModel item : complainModelList) {
            if (item.getEmp_uid().contains(uid) &&
                    item.getComplain_thana_upzilla().contains(upzila) &&
                    item.getEmp_role().contains(role)
            ) {
                fillteredList.add(item);
            }
        }

        return fillteredList;
    }

    public static List<ComplainModel> FillterRegAdminComplainModel(String departmentName, String uid, String upzila,
                                                                   String role, List<ComplainModel> complainModelList) {

        Log.d("TAG", "FillterComplainModel: " + uid + " " + upzila + " " + role);
        List<ComplainModel> fillteredList = new ArrayList<>();
        for (ComplainModel item : complainModelList) {
            if (item.getEmp_uid().contains(uid) &&
                    item.getComplain_thana_upzilla().contains(upzila) &&
                    item.getEmp_role().contains(role) && item.getComplain_officer_department_name().contains(departmentName)
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




