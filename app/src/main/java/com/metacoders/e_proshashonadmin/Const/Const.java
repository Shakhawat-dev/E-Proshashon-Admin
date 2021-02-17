package com.metacoders.e_proshashonadmin.Const;

import java.util.ArrayList;
import java.util.List;

public class Const {
    public static final String COMPLAIN_REPO = "complain_box";
    public static final String USER_PROFILE_REPO = "user_profile";
    public static final String PUBLISHABLE = "pk_test_51ILMXLELeQjZhqHV6W3QTtzVzKitUVEpdoJOf3xUwXslmO5x1zNG9wO7SZxVXM2pGeFnb5YtzeDJZxTpmFNG1e9100qd1hRJCE";
    public static final String SECRET_KEY = "sk_test_51ILMXLELeQjZhqHV70x6UA3wvLdikhX7hjTE8n3g97N9X6m2l6T9ZOwY9O9AJxGnOKL6ieNT8QsQIsEoM3Mc9fDq00oGdySOt9";
    public static final String EMPLOYEE_LIST ="emp_list" ;


    //upozillaType

    public static List<String> upozillaType() {
        List<String> upozillaType = new ArrayList<String>();
        upozillaType.add("নির্বাচন করুন");
        upozillaType.add("জেলা প্রশাসন");
        upozillaType.add("নোয়াখালী সদর");
        upozillaType.add("কোম্পানীগঞ্জ");
        upozillaType.add("বেগমগঞ্জ");
        upozillaType.add("হাতিয়া");
        upozillaType.add("সুবর্ণচর");
        upozillaType.add("কবিরহাট");
        upozillaType.add("সেনবাগ");
        upozillaType.add("চাটখিল");
        upozillaType.add("সোনাইমুড়ী");
        return upozillaType;

    }

    // admin type  adminType
    public static List<String> adminType() {
        List<String> adminType = new ArrayList<String>();
        adminType.add("নির্বাচন করুন");
        adminType.add("জেলা প্রশাসন");
        adminType.add("উপজেলা প্রশাসন");
        return adminType;

    }

    // department
    public static String[] divisionList() {

        String[] divisionList = {
                "নির্বাচন করুন",
                "জেলা প্রশাসন",
                "পুলিশ প্রশাসন",
                "আনসার ও ভিডিপি",
                "জেলা নির্বাচন অফিস",
                "জেলা শিক্ষা অফিস",
                "জেলা প্রাথমিক শিক্ষা অফিস",
                "জেলা ভূমি অফিস",
                "পরিবার পরিকল্পনা অধিদপ্তর অফিস",
                "সমাজসেবা অধিদপ্তর অফিস",
                "কৃষি অধিদপ্তর অফিস",
                "মৎস্য অধিদপ্তর অফিস",
                "খাদ্য অধিদপ্তর অফিস",
                "প্রাণিসম্পদ অধিদপ্তর অফিস",
                "আইসিটি বিভাগ",
                "অন্যান্য"
        };

        return divisionList ;
    }


}
