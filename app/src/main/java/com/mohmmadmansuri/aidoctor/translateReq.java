package com.mohmmadmansuri.aidoctor;



import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.*;
import java.nio.charset.Charset;

import javax.net.ssl.HttpsURLConnection;

public class translateReq extends AsyncTask<Object,Integer,Bundle> {

/*try {
                String url = "https://api.mymemory.translated.net/get?q="+s1+"&langpair=gu|en";

                URL obj = new URL(url);
                HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
                con.setRequestMethod("GET");

                System.out.println("res:" + con.getResponseMessage());

                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                String res = "";

                res = in.readLine();

                JSONObject reader = new JSONObject(res);

                JSONObject reader1 = new JSONObject(reader.get("responseData").toString());

                Log.d("here it is", reader1.get("translatedText").toString());
                mtTrans = reader1.get("translatedText").toString();

                in.close();


            }catch(Exception e){e.printStackTrace();}
            finally {
                b1.putString("mt",mtTrans);
                return mtTrans;
            }
*/
    /*
              // String url = "https://translate.googleapis.com/translate_a/single?client=gtx&sl=gu&tl=en&dt=t&q=" + s1;


            //https://transapi.herokuapp.com/index
            //con.setRequestProperty("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36");

    //con.getContent();
     */

    //   System.out.println("res:" + res);

    // sb = res.substring(res.indexOf('"') + 1, res.indexOf('"', 4));
    // System.out.println("res:" + sb);

    /*    BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        String res = "";

        res = in.readLine();
        sb = res.substring(res.indexOf('"') + 1, res.indexOf('"', 4));
        System.out.println("res:" + sb);
        in.close();
*/
    public String sb1 = "unmodified";
    public Bundle b1 = new Bundle();
    //https://translate.googleapis.com/translate_a/single?client=gtx&sl=en&tl=gu&dt=t&q=i%20have%20fever%20and%20headache

/*
    public Object trans(String s1){


    }
*/
    @Override
    protected Bundle doInBackground(Object[] objects) {
        Object o1 = null;
        String mtTrans ="";
        String gtrans ="";
        String s1 = objects[0].toString();
        try {
  /*          Object sz = trans(objects[0].toString());
            Log.d("zz", sz.toString());
            sb1 = sz.toString();
            o1 = sz;*/
{
                String url = "https://api.mymemory.translated.net/get?q=" + s1 + "&langpair=gu|en";

                URL obj = new URL(url);
                HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
                con.setRequestMethod("GET");

                System.out.println("res:" + con.getResponseMessage());

                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                String res = "";

                res = in.readLine();

                JSONObject reader = new JSONObject(res);

                JSONObject reader1 = new JSONObject(reader.get("responseData").toString());

                Log.d("here it is", reader1.get("translatedText").toString());
                mtTrans = reader1.get("translatedText").toString();

                in.close();


            }

            {

                String url = "https://transapi.herokuapp.com/index";


                //here post request generation using the keyword itselfમને ગળામાં દુખે છે
                try {
                    String urlParameters  = "qstring="+s1;

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

                    res = in.readLine();
                    gtrans=res;

                    wr.close();
                    in.close();

                }catch (Exception e){
                    e.printStackTrace();
                }
            }

        }finally {
            Log.d("translatedText",""+mtTrans);
            b1.putString("mt",""+mtTrans);
            b1.putString("gt",""+gtrans);
            return b1;
        }
    }

    @Override
    protected void onPostExecute(Bundle l1){

        Log.d("bund",""+l1);
        Log.d("satus in file",""+getStatus());

        speech_Rec rc = new speech_Rec();
        rc.fins();

    }

}
