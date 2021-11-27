package com.mohmmadmansuri.aidoctor;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

   public String langs = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.INTERNET},101);

        Spinner sp = (Spinner)findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.langs,R.layout.support_simple_spinner_dropdown_item);
        sp.setAdapter(adapter);

        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                langs = adapterView.getItemAtPosition(i).toString();
                Log.d("here",langs);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
    //https://aidocbymm.herokuapp.com/

    public void star(View v){

        Intent i = new Intent(getApplicationContext(),speech_Rec.class);
        i.putExtra("lang",langs);
        startActivity(i);
    }

    public void qStr(View v){


        //MainActivity m1 = new MainActivity();
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://aidocbymm.herokuapp.com/index";

        StringRequest stringRequest = new StringRequest(Request.Method.POST,url,
                new Response.Listener<String>(){

                    @Override
                    public void onResponse(String response) {

                        Log.d("Response",""+response);

                        try {
                            JSONObject reader = new JSONObject(response);
                            JSONArray sys = reader.getJSONArray("Symp");
                            Log.d("here it is", sys.getString(0));
                            TextView t2 = (TextView)findViewById(R.id.chn);
                            t2.setText(response);
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
                params.put("qstring","ihavehighfeverandvomiting");
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
    }
}
