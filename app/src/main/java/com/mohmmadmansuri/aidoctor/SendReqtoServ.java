package com.mohmmadmansuri.aidoctor;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
/*
public class SendReqtoServ {

    public String mainsq = "";
    public JSONArray jr = null;

    public JSONArray sendR(String s1){

        mainsq = s1;
        MainActivity m1 = new MainActivity();
    RequestQueue queue = Volley.newRequestQueue(m1.getApplicationContext());
    String url = "https://aidocbymm.herokuapp.com/index";

    StringRequest stringRequest = new StringRequest(Request.Method.POST,url,
            new Response.Listener<String>(){

                @Override
                public void onResponse(String response) {

                    Log.d("Response",""+response);

                    try {
                        JSONObject reader = new JSONObject(response);
                        JSONArray sys = reader.getJSONArray("Symp");
                        jr = sys;
                        Log.d("here it is", sys.getString(0));
                        //TextView t2 = (TextView)findViewById(R.id.chn);
                        //t2.setText(response);
                        Log.d("res",response);
                    }catch (Exception e){

                        e.printStackTrace();
                    }

                }
            },

            new Response.ErrorListener(){
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("err",error.toString());
                }
            }){

        @Override
        protected Map<String,String> getParams(){
            Map<String,String> params = new HashMap<>();
            params.put("qstring",""+mainsq);
            return params;
        }
        @Override
        public Map<String,String> getHeaders() throws AuthFailureError {
            Map<String,String> headers = new HashMap<String,String>();
            headers.put("Content-Type","application/x-www-form-urlencoded");
            return headers;
        }
    };

        queue.add(stringRequest);

        return jr;
    }
}

*/
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

public class SendReqtoServ extends AsyncTask<Object,Integer, Bundle> {

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



                String url = "https://aidocbymm.herokuapp.com/index";


                //here post request generation using the keyword itselfમને ગળામાં દુખે છે
                try {
                    String sp = s1.replaceAll("[^a-zA-Z\\s]","");
                    String urlParameters  = "qstring="+sp;

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

                    res = in.readLine();
                    gtrans=res;

                    in.close();
                }catch (Exception e){
                    e.printStackTrace();
                }


        }finally {
            Log.d("translatedText",""+gtrans);
            //b1.putString("mt",""+mtTrans);
            b1.putString("gt",""+gtrans);
            return b1;
        }
    }

    @Override
    protected void onPostExecute(Bundle l1){

        Log.d("bund",""+l1);
    }

}
