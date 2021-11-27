package com.mohmmadmansuri.aidoctor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import javax.net.ssl.HttpsURLConnection;

import static java.nio.charset.StandardCharsets.UTF_8;

public class speech_Rec extends AppCompatActivity implements RecognitionListener{
    String sx = "";
    public TextView t1=null;
    public String gtrans = "";
    public  translateReq rq =null;
    speech_Rec conx = null;
    SpeechRecognizer mSpeechRecognizer = null;
    Intent mSpeechRecognizerIntent = null;
    String answer1 = "";
    boolean fl = false;
   boolean df = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speech__rec);


        Intent i = getIntent();
        sx = i.getStringExtra("lang");
        conx = this;

        t1 =(TextView)findViewById(R.id.txt1);
        mSpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
        mSpeechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        mSpeechRecognizer.setRecognitionListener(this);

        if(sx.equals("Gujarati")){
        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "gu-IN");
        }else{
            mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getAvailableLocales());

        }

        Button b1 = (Button)findViewById(R.id.button2);
        b1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_UP:
                        t1.setText("Inputting");
                        mSpeechRecognizer.stopListening();
                        break;
                    case MotionEvent.ACTION_DOWN:
                        t1.setText("Listening....");
                        mSpeechRecognizer.startListening(mSpeechRecognizerIntent);
                        break;
                }
                return false;
            }
        });


    }


    public void gujrechandle(String s1){
      //  fl = true;
        Log.d("logging","4");
        Bundle bq = gujPars(s1);
        t1.setText("mytranslate : "+bq.get("mt")+" google translate : "+bq.get("gt"));

        String symparr[];
        Log.d("status of task 3",""+rq.getStatus());

        if(bq != null) {
            Log.d("logging","5");
            SendReqtoServ sr = new SendReqtoServ();

            Bundle c2 = null;
            // String szx = rq.trans(s);
            Log.d("inside bq meth",""+bq.get("gt") + " " + bq.get("mt"));
            Object szx = sr.execute(bq.get("gt") + " " + bq.get("mt"));


            try {
                c2 = (Bundle) ((AsyncTask) szx).get();
                Log.d("trans here ", "" + c2.get("gt"));
                JSONObject reader = new JSONObject(c2.get("gt").toString());
                JSONArray sys = reader.getJSONArray("Symp");
                //sys.getString(0)

                //sys array needs to be passed with speaksymp intent
                CsvParsingT sc = new CsvParsingT(conx);
                Bundle c3 = null;
                translReqEngGuj sq = null;
                Object smm = null;
                String cox = "";
                Object zz= null;
                speechTTS tz = null;
                String qnew = "";
                String quer = "";
                String urlar[] = new String[sys.length()];
                symparr = new String[sys.length()];

                for(int i=0;i<sys.length();i++) {
                    Log.d("iteration",""+i);
                    qnew = sc.parseT(sys.get(i).toString());
                    Log.d("first symp", ""+qnew);

                    sq = new translReqEngGuj();
                    // String szx = rq.trans(s);
                    smm = sq.execute(qnew);

                    try {
                        c3 = (Bundle) ((AsyncTask) smm).get();}catch (Exception e){}
                    //JSONObject reader1 = new JSONObject(c3.get("gt").toString());
                    quer = c3.get("gt").toString();

                   // tz = new speechTTS();


                   //zz=  tz.execute(quer);

                  // cox = (String) ((AsyncTask) zz).get();

                   Log.d("got the string",cox);



                    symparr[i]=sys.get(i).toString();
                   urlar[i]=quer;



                   //here media player work

                  // urlar[i] = cox;
                   //speak(cox);

                   Log.d("thread sleep","tlsleep");
                   //Thread.sleep(5000);

                    //mSpeechRecognizer.stopListening();
                    Log.d("got the string",answer1);
                }

                Intent i = new Intent(getApplicationContext(),SpeakSymp.class);
                i.putExtra("ar",urlar);
                i.putExtra("sympar",symparr);
                startActivity(i);
               // try{speak(urlar);}catch (Exception e){}
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } /*catch (IOException e) {
                e.printStackTrace();
            } */ finally {


            }

        }
    }







    public Bundle gujPars(String s){
        Log.d("logging","3");
        Bundle c1 = null;
        rq = new translateReq();
       // String szx = rq.trans(s);
        Object szx = rq.execute(s);
        //translateReq a = (translateReq) szx;
        Log.d("gujarati inp",s);


        final TextView td = (TextView)findViewById(R.id.jj);
        td.setText(s);
        final String s1= s;


        try {
            c1 = (Bundle) ((AsyncTask) szx).get();
            Log.d("trans here ",""+c1);

/*
            while(rq.getStatus()!= AsyncTask.Status.FINISHED){
                Log.d("status of task",""+rq.getStatus());
            }*/
            Log.d("status of task 1",""+rq.getStatus());

        }finally {

            Log.d("status of task 2",""+rq.getStatus());
                return c1;

        }

    }
    public void engPars(String s){
        Log.d("here hi ",s);
    }

    public void fins(){

    }

    @Override
    public void onReadyForSpeech(Bundle bundle) {

    }

    @Override
    public void onBeginningOfSpeech() {

    }

    @Override
    public void onRmsChanged(float v) {

    }

    @Override
    public void onBufferReceived(byte[] bytes) {

    }

    @Override
    public void onEndOfSpeech() {

    }

    @Override
    public void onError(int i) {

    }

    @Override
    public void onResults(Bundle bundle) {


        ArrayList<String> matches = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);

        t1.setText(matches.get(0));

        String s1 = matches.get(0);
        if(sx.equals("Gujarati")){

                 Log.d("logging", "1");
                 gujrechandle(s1);

        }else{
            engPars(s1);
        }
    }

    @Override
    public void onPartialResults(Bundle bundle) {

    }

    @Override
    public void onEvent(int i, Bundle bundle) {

    }
}


        /*



    boolean play =true;
//make array of urls and then one by one speak
public void speak(String q[]) throws IOException, InterruptedException {
            fl = true;
            Log.d("called","1");

//for(int i=0;i<q.length;i++) {


int i=0;
while(true) {
         if(play==true) {
             String fq = "http://ivrapi.indiantts.co.in/tts?type=indiantts&text="+q[i]+"&api_key=27467830-1be2-11ea-85f7-cfec0bc4adfc&user_id=67937&action=play&numeric=hcurrency&lang=gu_rashmi";
             Log.d("here",""+q[i]);
             mSpeechRecognizer.stopListening();
             //q[i]
             //"http://ivrapi.indiantts.co.in/tts?type=indiantts&text=&api_key=27467830-1be2-11ea-85f7-cfec0bc4adfc&user_id=67937&action=play&numeric=hcurrency&lang=gu_rashmi";
             mediaplay(fq);
             i++;
         }
         if(i==q.length){
             break;
         }

    }
    //

   // try{Thread.sleep(5000);}catch (Exception e){}
//}

/*

            mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    Log.d("got the string", "stopped");

                    voicerec();

                }
            });
*/
  //  }
/*

public void mediaplay(String sp) throws IOException{
        play=false;
        //MediaPlayer mp = new MediaPlayer();
        //mp.setDataSource(sp);
        //mp.prepare();
        //mp.start();

 /*       mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                Log.d("media player","prepared");
                mediaPlayer.start();
            }
        });

        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                Log.d("media player","stopped");
                mediaPlayer.stop();
                mediaPlayer.reset();
                mediaPlayer.release();
                voicerec();
            }
        });
    }

        new Thread(){
            @Override
            public void run(){
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
                    Log.d("got it ",""+res);
                    gtrans = res;
                    b1.putString("gt",gtrans);
                    in.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }.start();
*/
       /*boolean azz = true;
        while(azz){
            if(a.sb1!="unmodified"){



                t1.setText(""+a.sb1+" : gtrans : ");
                b1.putString("mt",a.sb1);
                a.sb1 = "unmodified";
                azz = false;
            }else{
                continue;
            }

        }*/


//  translateReq szv = (translateReq) szx;
//  Log.d("here hi ",""+a.sb1);