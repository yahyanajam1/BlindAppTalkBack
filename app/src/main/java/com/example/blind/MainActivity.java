package com.example.blind;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Toast;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {


    CardView card1;
    CardView card2;
    CardView card3;
    CardView card4;
    TextToSpeech speech1;

    public abstract class DoubleClickListener implements View.OnClickListener {
        private static final long DOUBLE_CLICK_TIME_DELTA = 600;//milliseconds
        long lastClickTime = 0;

        @Override
        public void onClick(View view) {
            long clickTime = System.currentTimeMillis();
            if (clickTime - lastClickTime < DOUBLE_CLICK_TIME_DELTA) {
                onDoubleClick(view);
                lastClickTime = 0;
            } else {
                onSingleClick(view);
            }
            lastClickTime = clickTime;
        }

        public abstract void onDoubleClick(View view);

        public abstract void onSingleClick(View view);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        card1 = findViewById(R.id.c1);
        card2 = findViewById(R.id.c2);
        card3 = findViewById(R.id.c3);
        card4 = findViewById(R.id.c4);


        speech1 = new TextToSpeech(MainActivity.this, new TextToSpeech.OnInitListener() {

            @Override
            public void onInit(int i) {
                speech1.setLanguage(Locale.ENGLISH);

                speech1.speak("Welcome to Sahara App ; Click on the different sides of the Screen to know details"
                        , TextToSpeech.QUEUE_FLUSH, null, null);

                card1.setOnClickListener(new DoubleClickListener() {

                    @Override
                    public void onSingleClick(View v) {
                        speech1.speak("You Clicked Message ; Double Click to Confirm"
                                , TextToSpeech.QUEUE_FLUSH, null, null);

                    }

                    @Override
                    public void onDoubleClick(View v) {
                        Intent intent = new
                                Intent(MainActivity.this, Messages.class);
                        startActivity(intent);
                    }
                });

                card2.setOnClickListener(new DoubleClickListener() {
                    @Override
                    public void onSingleClick(View v) {
                        speech1.speak("You Clicked Contact ; Double Click to Confirm"
                                , TextToSpeech.QUEUE_FLUSH, null, null);

                    }

                    @Override
                    public void onDoubleClick(View v) {
                        Intent intent = new
                                Intent(MainActivity.this, Contacts.class);
                        startActivity(intent);
                    }
                });



                card3.setOnClickListener(new DoubleClickListener() {
                    @Override
                    public void onSingleClick(View v) {
                        speech1.speak("You Clicked Date & Time ;  Double Click to Confirm"
                                , TextToSpeech.QUEUE_FLUSH, null, null);

                    }

                    @Override
                    public void onDoubleClick(View v) {
                        Intent intent = new
                                Intent(MainActivity.this, Music.class);
                        startActivity(intent);
                    }
                });


                card4.setOnClickListener(new DoubleClickListener() {
                    @Override
                    public void onSingleClick(View v) {
                        speech1.speak("You Clicked Battery ;  Double Click to Confirm"
                                , TextToSpeech.QUEUE_FLUSH, null, null);
                    }
                    @Override
                    public void onDoubleClick(View v) {
                        Intent intent = new
                                Intent(MainActivity.this, Battery.class);
                        startActivity(intent);
                    }
                });


            }
        });


        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.SEND_SMS) + ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.CALL_PHONE) + ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.READ_SMS) + ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    Manifest.permission.SEND_SMS) ||
                    ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                            Manifest.permission.CALL_PHONE) ||
                    ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                            Manifest.permission.READ_SMS) ||
                    ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                            Manifest.permission.READ_CALL_LOG)) {
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.SEND_SMS, Manifest.permission.CALL_PHONE, Manifest.permission.READ_SMS, Manifest.permission.READ_CALL_LOG}, 1);
            } else {
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.SEND_SMS, Manifest.permission.CALL_PHONE, Manifest.permission.READ_SMS, Manifest.permission.READ_CALL_LOG}, 1);
            }


        } else {}


    }

    @Override
    protected void onPause() {
        super.onPause();
        speech1.stop();

    }
    @Override
    protected void onResume(){
        super.onResume();
        speech1.speak("Welcome to Sahara App ; Click on the different sides of the Screen to know details"
                , TextToSpeech.QUEUE_FLUSH, null, null);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,String[] permissions,int[]grantResults){
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ContextCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show();
                }

            } else {
                Toast.makeText(this, "Permission not granted", Toast.LENGTH_LONG).show();
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.SEND_SMS, Manifest.permission.CALL_PHONE, Manifest.permission.READ_SMS, Manifest.permission.READ_CALL_LOG}, 1);
            }
            return;
        }


    }



}
















