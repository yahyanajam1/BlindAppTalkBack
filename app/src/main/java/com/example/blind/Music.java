package com.example.blind;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.TextView;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Music extends AppCompatActivity {

    TextView textd1;
    TextView textt1;
    Calendar calendar;
    CardView carddat;
    CardView cardtim;
    TextToSpeech speechda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);

        textd1 = findViewById(R.id.textdate);
        textt1 = findViewById(R.id.textime);
        carddat = findViewById(R.id.cda);
        cardtim = findViewById(R.id.cdb);

        speechda = new TextToSpeech(Music.this, new TextToSpeech.OnInitListener() {

            @Override
            public void onInit(int i) {
                speechda.setLanguage(Locale.ENGLISH);
                speechda.speak("Date and Time is open ; Click on any Item to know details"
                        , TextToSpeech.QUEUE_FLUSH, null, null);
            }

        });

        calendar = Calendar.getInstance();

        String date = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
        textd1.setText(date);

        String time = DateFormat.getTimeInstance().format(calendar.getTime());
        textt1.setText(time);

      carddat.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              String stg = textd1.getText().toString();
              String sts = ("The Date is:");
              speechda.speak(sts,TextToSpeech.QUEUE_FLUSH,null,null);
              speechda.speak( stg,TextToSpeech.QUEUE_ADD,null,null);
          }
      });

      cardtim.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        String str = textt1.getText().toString();
        String stn = ("The Time is:");
        speechda.speak(stn,TextToSpeech.QUEUE_FLUSH,null,null);
        speechda.speak(str,TextToSpeech.QUEUE_ADD,null,null);
    }});

    }

    @Override
    protected void onPause() {
        super.onPause();
        speechda.stop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        speechda.speak("Date and Time is open ; Click on any Item to know details"
                , TextToSpeech.QUEUE_FLUSH, null, null);
    }

}
