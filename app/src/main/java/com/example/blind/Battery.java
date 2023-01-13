package com.example.blind;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.Locale;

public class Battery extends AppCompatActivity {
     TextToSpeech batSpeech;
    CardView cardBat1;
    CardView cardBat2;
    TextView textBat;
    TextView textBat1;
    ImageView imageBat;
    ImageView imageBat1;
    Handler handler;
    Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battery);

        imageBat = findViewById(R.id.imgba1);
        imageBat1 = findViewById(R.id.imgba2);
        textBat = findViewById(R.id.textba1);
        textBat1 = findViewById(R.id.textba2);
        cardBat1 = findViewById(R.id.cb1);
        cardBat2 = findViewById(R.id.cb3);

        batSpeech = new TextToSpeech(Battery.this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                batSpeech.setLanguage(Locale.ENGLISH);
                batSpeech.speak("Battery is open ; Click on any Item to Know Details"
                        , TextToSpeech.QUEUE_FLUSH, null, null);

                cardBat1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String stb = textBat.getText().toString();
                        batSpeech.speak("The device",TextToSpeech.QUEUE_FLUSH, null, null);
                        batSpeech.speak(stb,TextToSpeech.QUEUE_ADD, null, null);

                    }
                });

                cardBat2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String stb1 = textBat1.getText().toString();
                        batSpeech.speak("The device is",TextToSpeech.QUEUE_FLUSH, null, null);
                        batSpeech.speak(stb1,TextToSpeech.QUEUE_ADD, null, null);
                    }
                });

            }});

        runnable = new Runnable() {
            @Override
            public void run() {

                int level = (int) BatteryLevel();
                String sText = ("Battery is:" + level + "%");
                textBat.setText(sText);

                int status = (int) BatteryInfo();
                String stgB1 = ("" + status);
                if (stgB1.equals("2")) {
                    String strn1 = ("Charging");
                    textBat1.setText(strn1);
                    imageBat1.setImageResource(R.drawable.ic_battery_charging_full_black_24dp);
                }
                if (stgB1.equals("4")) {
                    String str2 = ("Not Charging");
                    textBat1.setText(str2);
                    imageBat1.setImageResource(R.drawable.ic_battery_charging_white);
                }


                if (level > 95) {
                    imageBat.setImageResource(R.drawable.ic_battery_full_white);
                }
                if (level > 85 && level <= 90) {
                    imageBat.setImageResource(R.drawable.ic_battery_90_white);
                }
                if (level > 70 && level <= 85) {
                    imageBat.setImageResource(R.drawable.ic_battery_80_white);
                }
                if (level > 55 && level <= 65) {
                    imageBat.setImageResource(R.drawable.ic_battery_60_white);
                }
                if (level > 40 && level <= 55) {
                    imageBat.setImageResource(R.drawable.ic_battery_50_white);
                }
                if (level > 25 && level <= 35) {
                    imageBat.setImageResource(R.drawable.ic_battery_30_white);
                }
                if (level > 15 && level <= 25) {
                    imageBat.setImageResource(R.drawable.ic_battery_20_white);
                }
                if (level > 5 && level <= 15) {
                    imageBat.setImageResource(R.drawable.ic_battery_alert_white);
                }

                handler.postDelayed(runnable, 5000);


            }
        };


        handler = new Handler();
        handler.postDelayed(runnable, 0);

    }

    public float BatteryLevel() {


        Intent BatIntent =   registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));

        assert BatIntent != null;
        int level = BatIntent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);

        return ((float)level);

    }

    public  float BatteryInfo(){

        Intent batIntent =   registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));

        assert batIntent != null;
        int status = batIntent.getIntExtra(BatteryManager.EXTRA_STATUS,-1);

        return ((float) status);


    }
    @Override
    protected void onPause() {
        super.onPause();
        batSpeech.stop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        batSpeech.speak("Battery is open ; Click on any Item to know details"
                , TextToSpeech.QUEUE_FLUSH, null, null);
    }

}


