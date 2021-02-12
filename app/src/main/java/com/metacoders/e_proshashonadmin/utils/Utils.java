package com.metacoders.e_proshashonadmin.utils;

import com.metacoders.e_proshashonadmin.Models.ComplainModel;
import com.metacoders.e_proshashonadmin.Models.EmpModel;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class Utils {


    public static String COMPLAIN_REPO = "complain_box";
    public static String[] disrictDesignationList = new String[]{
            "নির্বাচন করুন",
            "জেলা প্রশাসন", // admin
            "অতিরিক্ত জেলা ম্যাজিস্ট্রেট",
            "অতিরিক্ত জেলা প্রশাসক(রাজস্ব)",
            "অতিরিক্ত জেলা প্রশাসক(সার্বিক)",
            "উপ পরিচালক,স্থানীয় সরকার(চলতি দায়িত্ব)",
            "অতিরিক্ত জেলা প্রশাসক(শিক্ষা ও আইসিটি)",
            "পুলিশ সুপার",
            "অতিরিক্ত পুলিশ সুপার(প্রশাসন ও অপরাধ)",
            "জেল সুপার",
            "জেলার",
            "সহকারী সার্জন, কারা হাসপাতাল",
            "জেলা কমান্ড্যান্ট(অতিঃদায়িত্ব)আনসার ও ভিডিপি",
            "সহকারী জেলা কমান্ড্যান্ট,আনসার ও ভিডিপি",
            "সার্কেল অ্যাডজুট্যান্ট,আনসার ও ভিডিপি",
            "জেলা শিক্ষা অফিসার",
            "সহকারী পরিদর্শক,জেলা শিক্ষা অফিসার",
            "ডিস্ট্রিক্ট ট্রেনিং কো অর্ডিনেটর",
            "জেলা প্রাথমিক শিক্ষা অফিসার",
            "সহকারী জেলা প্রাথমিক শিক্ষা অফিসার",
            "জেলা প্রশিক্ষণ অফিসার",
            "কৃষি প্রকৌশলী",
            "জেলা খাদ্য নিয়ন্ত্রক"
    };
    public static String[] upzillaDesignationList = new String[]{
            "নির্বাচন করুন",
            "উপজেলা নির্বাহী অফিসার", // admin
            "অফিসার ইনচার্জ (ওসি)",
            "সহকারী কমিশনার (উপজেলা ভূমি অফিস)",
            "সাব-রেজিষ্ট্রার (উপজেলা সাব-রেজিষ্ট্রার অফিস)",
            "সহকারী উপজেলা সেটেলমেন্ট অফিসার",
            "উপজেলা প্রকৌশলী",
            "উপজেলা সহকারী প্রকৌশলী",
            "উপজেলা নির্বাচন কর্মকর্তা,উপজেলা নির্বাচন অফিস",
            "মেডিকেল অফিসার(এমসিএইচ এফপি),পরিবার পরিকল্পনা অফিস",
            "উপজেলা পরিবার পরিকল্পনা কর্মকর্তা",
            "উপজেলা শিক্ষা অফিসার",
            "উপজেলা মাধ্যমিক শিক্ষা অফিসার",
            "উপজেলা সমাজসেবা অফিসার",
            "উপজেলা সমবায় অফিসার",
            "উপজেলা কৃষি অফিসার",
            "উপজেলা মৎস্য কর্মকর্তা",
            "উপজেলা খাদ্য নিয়ন্ত্রক",
            "উপজেলা প্রাণিসম্পদ কর্মকর্তা",
            "উপজেলা পল্লী উন্নয়ন কর্মকর্তা",
            "উপজেলা যুব উন্নয়ন কর্মকর্তা",
            "সহকারী প্রোগ্রামার(উপজেলা আইসিটি অফিসার)",
            "উপসহকারী প্রকৌশলী(উপজেলা জনস্বাস্থ্য প্রকৌশলী)",
            "বন কর্মকর্তা",
            "উপজেলা সমন্বয়কারী,উপজেলা একটি বাড়ি একটি খামার প্রকল্প",
            "তথ্য সেবা কর্মকর্তা",
            "ফিল্ড সুপারভাইজার,ইসলামিক ফাউন্ডেশন",
            "উপজেলা আনসার ও ভিডিপি কর্মকর্তা"
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
}
