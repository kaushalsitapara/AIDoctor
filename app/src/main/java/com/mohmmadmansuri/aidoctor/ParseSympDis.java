package com.mohmmadmansuri.aidoctor;

import org.json.JSONArray;
import org.json.JSONObject;

public class ParseSympDis {
    public static void main(String args[]){
        String res = "";

       try {
           JSONObject reader = new JSONObject(res);
           JSONArray sys = reader.getJSONArray("Symp");

       }catch (Exception e){e.printStackTrace();}
       }
}
