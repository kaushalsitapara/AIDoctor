package com.mohmmadmansuri.aidoctor;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;

import javax.net.ssl.HttpsURLConnection;

public class SpeechTTS2 extends AsyncTask<String,Integer, String> {
    @Override
    protected String doInBackground(String... strings) {
        String finalurl = "";


        try {



            String url = "http://ivrapi.indiantts.co.in/tts?type=indiantts&text=&api_key=27467830-1be2-11ea-85f7-cfec0bc4adfc&user_id=67937&action=play&numeric=hcurrency&lang=gu_rashmi";

            //here post request generation using the keyword itselfમને ગળામાં દુખે છે
            try {
                String murl = strings[0];
                String urlParameters  = "Languages=gujarati23f&ex=execute&ip=શુ તમને "+murl+" થાય છે&op=શુ તમને "+murl+" થાય છે";

                Log.d("inside sendReqtoServ",urlParameters);
                byte[] postData       = urlParameters.getBytes(Charset.defaultCharset());
                int    postDataLength = postData.length;




                URL obj = new URL(url);
                HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
                con.setRequestMethod("POST");
                con.setDoOutput( true );
                con.setRequestProperty( "Content-Type", "application/x-www-form-urlencoded");
                con.setRequestProperty( "charset", "utf-8");
                con.setRequestProperty( "Content-Length", Integer.toString( postDataLength ));

                DataOutputStream wr = new DataOutputStream(con.getOutputStream());
                wr.write( postData );

                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                String res = "";
                String urlhere="";


                for(int i=0;i<=167;i++){
                    res = in.readLine();

                    if(i==166){
                        urlhere=res;
                    }

                }

                String szz = urlhere.substring(36,59);

                Log.d("got url from",szz);
                finalurl = "https://www.iitm.ac.in/donlab/hts/"+szz;
/*
                MediaPlayer mp = new MediaPlayer();
                mp.setDataSource(finalurl);
                mp.prepare();
                mp.start();
*/
                in.close();

            }catch (Exception e){
                e.printStackTrace();
            }


        }finally {

            return finalurl;

        }

    }
}
