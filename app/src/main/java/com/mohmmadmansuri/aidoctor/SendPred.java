package com.mohmmadmansuri.aidoctor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Arrays;

import javax.net.ssl.HttpsURLConnection;

public class SendPred extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_pred);

        Button b1 = (Button)findViewById(R.id.b11);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    speak();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public  void speak() throws IOException {
        MediaPlayer mp = new MediaPlayer();
        String s= "તમને ડેન્ગ્યુ હોઈ શકે છે";
        String fq = "http://ivrapi.indiantts.co.in/tts?type=indiantts&text=" + s + "&api_key=27467830-1be2-11ea-85f7-cfec0bc4adfc&user_id=67937&action=play&numeric=hcurrency&lang=gu_rashmi";
        mp.setDataSource(fq);
        mp.prepare();

        mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                Log.d("media player", "prepared");
                mediaPlayer.start();

            }
        });
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mediaPlayer.stop();
                mediaPlayer.reset();
                mediaPlayer.release();

            }
        });
    }
}
