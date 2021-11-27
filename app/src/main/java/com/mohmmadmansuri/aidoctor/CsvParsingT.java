package com.mohmmadmansuri.aidoctor;

import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

public class CsvParsingT {

    speech_Rec context = null;
CsvParsingT(speech_Rec con){
    context = con;
}
    public String parseT(String rq){


        MainActivity m1 = new MainActivity();

        String csvFile = "./Symptoms.csv";
    BufferedReader br = null;
    String line = "";
    HashMap<String, String> a1 = new HashMap<>();

        try

    {
        // BufferedReader cx = new BufferedReader(csvReader);
        //BufferedReader csvReader = new BufferedReader(new FileReader(csvFile));

        InputStream is = context.getAssets().open("symptoms.csv");
        BufferedReader csvReader = new BufferedReader(new InputStreamReader(is));

        while ((line = csvReader.readLine()) != null) {
            String[] data = line.split(",");
            for (int i = 1; i < data.length; i++) {

                a1.put(data[0], data[1]);
            }
        }

        csvReader.close();

    }catch(
    Exception e)

    {
        e.printStackTrace();
    }

        return a1.get(rq);
}
}