package com.example.demo.util;

import java.util.Base64;
import java.util.UUID;

public class AppUtil {

    public static String generateFieldCode(){
        return "FIELD-" + UUID.randomUUID();
    }

    public static String generateCropCode(){
        return "CROP-" + UUID.randomUUID();
    }

    public static String profilePicToBase64(byte[] profilePic){
        return Base64.getEncoder().encodeToString(profilePic);
    }
}
