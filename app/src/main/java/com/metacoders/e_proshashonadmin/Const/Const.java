package com.metacoders.e_proshashonadmin.Const;

import java.util.ArrayList;
import java.util.List;

public class Const {



    //upozillaType

    public  static  List<String> upozillaType (){
        List<String> upozillaType=new ArrayList<String>();
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
        return  upozillaType ;

    }
    // admin type  adminType
    public  static  List<String> adminType (){
        List<String> adminType=new ArrayList<String>();
        adminType.add("নির্বাচন করুন");
        adminType.add("জেলা প্রশাসন");
        adminType.add("উপজেলা প্রশাসন");
        return  adminType ;

    }
}
