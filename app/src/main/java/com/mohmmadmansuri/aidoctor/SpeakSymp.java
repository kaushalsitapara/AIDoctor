package com.mohmmadmansuri.aidoctor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.provider.MediaStore;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.IOException;
import java.util.ArrayList;

public class SpeakSymp extends AppCompatActivity implements RecognitionListener {

    int maxlim = 0;
    SpeechRecognizer mSpeechRecognizer = null;
    Intent mSpeechRecognizerIntent = null;
    StringBuffer fq;
    String mainQ [];
    String symparr[];
    int reachedlimit=0;
    int currind = 0;
    String ansarr[];

   //make two variables one for answer and another for the current id of symptom
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speak_symp);

        try {

            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        mSpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
        mSpeechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        mSpeechRecognizer.setRecognitionListener(this);
        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "gu-IN");

        Intent i = getIntent();
        mainQ = i.getStringArrayExtra("ar");
        symparr = i.getStringArrayExtra("sympar");

        maxlim = mainQ.length;
        ansarr= new String[maxlim];

        Button b1 = (Button)findViewById(R.id.button4);

        for (int j=0;j<mainQ.length;j++){
            Log.d("mainq j",""+j);
            Log.d("mainq",""+mainQ[j]);
        }
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ssp();
            }
        });
    }

    public void ssp(){
        //reachedlimit!=maxlim ||
        if(currind!=5) {
            Log.d("maxlim","max : "+maxlim+" reached : "+reachedlimit);
           //reachedlimit
            String s = mainQ[currind];
            MediaPlayer mp = new MediaPlayer();


            try {
                Log.d("foreachloop", "" + s);
                //શુ તમને થાય છે
                fq = new StringBuffer("http://ivrapi.indiantts.co.in/tts?type=indiantts&text=શુ તમને " + s + " થાય છે&api_key=27467830-1be2-11ea-85f7-cfec0bc4adfc&user_id=67937&action=play&numeric=hcurrency&lang=gu_rashmi");
                mp.setDataSource(fq.toString());
                mp.prepareAsync();

                mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mediaPlayer) {
                        mSpeechRecognizer.stopListening();
                        Log.d("media player", "prepared");
                        mediaPlayer.start();

                    }
                });
                mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {

                        Log.d("media player", "stopped");
                        mediaPlayer.stop();
                        mediaPlayer.reset();
                        mediaPlayer.release();
                        Log.d("reachedlimoncompletion",""+reachedlimit);
                        Log.d("maxlimoncompletion",""+maxlim);

                        voicerec();
                        //reachedlimit++;
                        //String d =  mSpeechRecognizer.stopListening();
                       // ssp();
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            Log.d("limit","reached");

            for(int i=0;i<ansarr.length;i++) {
                Log.d("ans", ""+ansarr[i]);
            }

            Intent i1 = new Intent(getApplicationContext(),SendPred.class);
            startActivity(i1);
        }

    }
    public  String voicerec(){
        Log.d("called","voicerec");
        mSpeechRecognizer.startListening(mSpeechRecognizerIntent);
        return "";
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
        mSpeechRecognizer.stopListening();
        ArrayList<String> matches = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);


        Log.d("inonresult",""+matches.get(0));
        Log.d("reachedlmt",""+reachedlimit);
//sympar[] ના નથી થતો હા થાય છે

        if(matches.get(0).equals("હા") || matches.get(0).contains("હા")|| matches.get(0).contains("થાય છે")|| matches.get(0).equals("હા થાય છે")){
            Log.d("the symptom id is",""+symparr[currind]);
            //reachedlimit
            ansarr[currind]=symparr[currind];

            currind++;
        }else{
            //reachedlimit
            ansarr[currind] = "0";
            currind++;
        }
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.d("inside voicerec",""+reachedlimit);
        ssp();
        reachedlimit++;


    }

    @Override
    public void onPartialResults(Bundle bundle) {

    }

    @Override
    public void onEvent(int i, Bundle bundle) {

    }
}
