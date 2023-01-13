package com.example.blind;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;


public class CallingFragment extends Fragment {

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

    private static final int REQUEST_CODE_SPEECH_INPUT = 1000;

    private View vw;
    private TextView tx1;
    private CardView dC1;
    private CardView dC2;
    private CardView dC3;
    private CardView dC4;
    private TextToSpeech speechCo;

    public CallingFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        vw = inflater.inflate(R.layout.fragment_calling, container, false);


        tx1 = vw.findViewById(R.id.text);
        dC1 = vw.findViewById(R.id.con1);
        dC2 = vw.findViewById(R.id.con2);
        dC3 = vw.findViewById(R.id.con3);
        dC4 = vw.findViewById(R.id.con4);


        speechCo = new TextToSpeech(getActivity(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                speechCo.setLanguage(Locale.ENGLISH);

                speechCo.speak("Dialer is open ; Click on any item to Know details ; or  Swipe left for Call Log"
                        , TextToSpeech.QUEUE_FLUSH, null, null);

                dC1.setOnClickListener(new DoubleClickListener() {
                    @Override
                    public void onSingleClick(View view) {
                        speechCo.speak("Double Tap and speak the Phone Number to make a Call"
                                , TextToSpeech.QUEUE_FLUSH, null, null);
                    }

                    @Override
                    public void onDoubleClick(View view) {
                        {
                            speak();
                        }
                    }

                    private void speak() {
                        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
                        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "say something");

                        try {
                            startActivityForResult(intent, REQUEST_CODE_SPEECH_INPUT);

                        } catch (Exception e) {
                            Toast.makeText(getActivity(), "", Toast.LENGTH_SHORT).show();
                        }
                    }

                });

                dC2.setOnClickListener(new DoubleClickListener() {

                    @Override
                    public void onSingleClick(View view) {
                        speechCo.speak("Double Tap to Call"
                                , TextToSpeech.QUEUE_FLUSH, null, null);
                    }

                    @Override
                    public void onDoubleClick(View view) {
                        {
                            makeCall();
                        }
                    }

                    private void makeCall() {

                        String number = tx1.getText().toString();
                        if (number.trim().length()>0) {
                            String dial = "tel:" + number;
                            startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));

                        } else{
                            Toast.makeText(getActivity(), "Enter Phone Number", Toast.LENGTH_SHORT).show();
                           // speechCo.speak("Could not place a Call"
                                    //, TextToSpeech.QUEUE_FLUSH, null, null);
                        }
                    }
                });

                dC3.setOnClickListener(new DoubleClickListener() {

                    @Override
                    public void onSingleClick(View view) {
                        speechCo.speak("Double Tap to Hear the Details"
                                , TextToSpeech.QUEUE_FLUSH, null, null);
                    }

                    @Override
                    public void onDoubleClick(View view) {
                        String stg = tx1.getText().toString();
                        speechCo.speak("The Details are"
                                , TextToSpeech.QUEUE_FLUSH, null, null);
                        speechCo.speak(stg, TextToSpeech.QUEUE_ADD, null, null);

                    if(stg.isEmpty()){
                        speechCo.speak("The Text Field is Empty"
                                , TextToSpeech.QUEUE_FLUSH, null, null);
                    }


                    }
                });

                dC4.setOnClickListener(new DoubleClickListener() {

                    @Override
                    public void onSingleClick(View view) {
                        speechCo.speak("Double Tap to Clear the Text"
                                , TextToSpeech.QUEUE_FLUSH, null, null);
                    }

                    @Override
                    public void onDoubleClick(View view) {
                        tx1.setText("");
                        Toast.makeText(getActivity(), "Text Cleared", Toast.LENGTH_SHORT).show();
                        speechCo.speak("Text Cleared"
                                , TextToSpeech.QUEUE_FLUSH, null, null);
                    }


                });



            }
        });

        return vw;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SPEECH_INPUT) {
            if (resultCode == RESULT_OK && null != data) {

                ArrayList<String> result = data
                        .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                tx1.setText(result.get(0));
                speechCo.speak("Thank you ; Click the Call Button"
                        , TextToSpeech.QUEUE_FLUSH, null, null);
            }
        }
    }
    @Override
    public void onPause() {
        super.onPause();
        speechCo.stop();
    }



}
