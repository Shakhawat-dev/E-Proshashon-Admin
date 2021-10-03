package com.metacoders.e_proshashonadmin.Const;

import java.util.ArrayList;
import java.util.List;

public class Const {
    public static final String COMPLAIN_REPO = "complain_box";
    public static final String USER_PROFILE_REPO = "user_profile";
    public static final String EMPLOYEE_LIST = "emp_list";
    public static final String ONESIGNAL_APP_ID = "a83eda6c-7220-4359-8f14-d9e06b131b98";

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

    public static List<String> complainType() {
        List<String> complainTypes = new ArrayList<String>();
        complainTypes.add("নির্বাচন করুন");
        complainTypes.add("নারী ও শিশু নির্যাতন");
        complainTypes.add("বাল্য বিবাহ নিরসন");
        complainTypes.add("ইভটিজিং");
        complainTypes.add("মাদক");
        complainTypes.add("সন্ত্রাস ও চাঁদাবাজি");
        complainTypes.add("দুর্নীতি");
        complainTypes.add("স্বাস্থ্য");
        complainTypes.add("শিক্ষা");
        complainTypes.add("জন্ম নিবন্ধন");
        complainTypes.add("সোশ্যাল মিডিয়ায় মানহানী");
        complainTypes.add("জন্ম নিবন্ধন");
        complainTypes.add("কৃষি");
        complainTypes.add("অন্যান্য");
        return complainTypes;

    }

    public static List<String> statusList() {
        List<String> statusList = new ArrayList<String>();
        statusList.add("নির্বাচন করুন");
        statusList.add("PENDING");
        statusList.add("ACCEPTED");
        statusList.add("REJECTED");
        statusList.add("INQUIRY");
        statusList.add("COMPLETED");

        return statusList;

    }


    // admin type  adminType
    public static List<String> adminType() {
        List<String> adminType = new ArrayList<String>();
        adminType.add("নির্বাচন করুন");//0
        adminType.add("জেলা প্রশাসন");
        adminType.add("উপজেলা প্রশাসন");
        return adminType;

    }


    public static String[] divisionList() {


        String[] divisionList = {
                "নির্বাচন করুন",
                "জেলা প্রশাসকের কার্যালয়",
                "উপজেলা নির্বাহী অফিসারের কার্যালয়",
                "উপজেলা ভূমি অফিস",
                "পুলিশ সুপারের কার্যালয়",
                "র\u200C্যাপিড এ্যাকশন ব্যাটালিয়ন (র\u200C্যাব)",
                "আনসার ও গ্রাম প্রতিরক্ষা",
                "মাদকদ্রব্য নিয়ন্ত্রন অধিদপ্তর",
                "জেলা নির্বাচন অফিস",
                "পাসপোর্ট অফিস",
                "দুর্নীতি দমন কমিশন",
                "জেলা সমাজসেবা কার্যালয়",
                "জেলা সিভিল সার্জনের কার্যালয়",
                "পরিবার পরিকল্পনা",
                "গণপূর্ত বিভাগ",
                "সড়ক ও জনপথ",
                "স্থানীয় সরকার প্রকৌশল অধিদপ্তর",
                "বিদ্যুৎ উন্নয়ন বোর্ড",
                "জনস্বাস্থ্য প্রকৌশল অধিদপ্তর",
                "নির্বাহী প্রকৌশলীর অফিস বিএডিসি,সেচ",
                "বিআরটিএ",
                "রেলওয়ে",
                "জেলা মহিলা বিষয়ক কর্মকর্তার কার্যালয়",
                "জাতীয় মহিলা সংস্থা,জেলা অফিস",
                "কৃষি সম্প্রসারণ অধিদপ্তর",
                "জেলা খাদ্য নিয়ন্ত্রকের কার্যালয়",
                "জেলা মৎস্য অফিস",
                "জেলা প্রাণিসম্পদ অফিস",
                "জেলা প্রাথমিক শিক্ষা অফিস",
                "জেলা শিক্ষা অফিস",
                "তথ্য ও যোগাযোগ প্রযুক্তি অধিদপ্তর",
                "বন বিভাগ",
                "জেলা ত্রান ও পনর্বাসন অফিস",
                "জাতীয় ভোক্তা অধিকার সংরক্ষণ অধিদপ্তর"
                // "অন্যান্য"
        };

        return divisionList;
    }


}
