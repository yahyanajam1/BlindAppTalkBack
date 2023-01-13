package com.example.blind;

import android.content.Intent;
import android.os.Bundle;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Locale;
import static android.app.Activity.RESULT_OK;


public class MessagingFragment extends Fragment {

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
    private boolean isFirst = true;

    private View view;
    private TextView tv1;
    private TextView tv2;
    private CardView mC1;
    private CardView mC2;
    private CardView mC3;
    private CardView mC4;
    private TextToSpeech  speechMe;


    public MessagingFragment(){}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_messaging, container, false);

        tv1 = view.findViewById(R.id.text1);
        tv2 = view.findViewById(R.id.text2);
        mC1 = view.findViewById(R.id.msg1);
        mC2 = view.findViewById(R.id.msg2);
        mC3 = view.findViewById(R.id.msg3);
        mC4 = view.findViewById(R.id.msg4);

        speechMe = new TextToSpeech(getActivity(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int  status) {
                speechMe.setLanguage(Locale.ENGLISH);
                speechMe.speak("Messaging is open ; Click on any item to Know details ; or  Swipe left for Inbox"
                        ,TextToSpeech.QUEUE_FLUSH,null,null);

                mC1.setOnClickListener(new DoubleClickListener() {
                    @Override
                    public void onSingleClick(View view) {
                        speechMe.speak("Double Tap and speak the Phone Number to send Text Message"
                        ,TextToSpeech.QUEUE_FLUSH,null,null);
            }
            @Override
            public void onDoubleClick(View view) {
                {
                    speak();
                }
            }
            private void speak() {
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
                try{
                    startActivityForResult(intent,REQUEST_CODE_SPEECH_INPUT);
                }catch (Exception e){
                    Toast.makeText(getActivity(),"",Toast.LENGTH_SHORT).show();
                }
            }
        });

        mC2.setOnClickListener(new DoubleClickListener() {
            @Override
            public void onSingleClick(View view) {
                speechMe.speak("Double Tap to sent"
                        , TextToSpeech.QUEUE_FLUSH, null, null);
            }

            @Override
            public void onDoubleClick(View view) {
                String number = tv1.getText().toString();
                String sms = tv2.getText().toString();

                if(number.length()>0 && sms.length()>0) {

                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(number, null, sms, null, null);


                    Toast.makeText(getActivity(), "Sent", Toast.LENGTH_SHORT).show();
                    speechMe.speak("Message sent Successful"
                            , TextToSpeech.QUEUE_FLUSH, null, null);
                } else{
                    Toast.makeText(getActivity(), "Enter Details", Toast.LENGTH_SHORT).show();
                    speechMe.speak("Message failed to sent"
                            , TextToSpeech.QUEUE_FLUSH, null, null);

                }
            }
        });

        mC3.setOnClickListener(new DoubleClickListener() {
            @Override
            public void onSingleClick(View v) {
                speechMe.speak("Double Tap to Hear the Details"
                        , TextToSpeech.QUEUE_FLUSH, null, null);
            }
            @Override
            public void onDoubleClick(View v) {
                String st1 = tv1.getText().toString();
                String st2 = tv2.getText().toString();

                speechMe.speak("The Details are"
                        , TextToSpeech.QUEUE_FLUSH, null, null);
                speechMe.speak(st1, TextToSpeech.QUEUE_ADD, null, null);
                speechMe.speak(st2, TextToSpeech.QUEUE_ADD, null, null);

                if(st1.isEmpty() || st2.isEmpty()){
                    speechMe.speak(" The Text Fields are Empty"
                            , TextToSpeech.QUEUE_FLUSH, null, null);
                }
            }
        });

        mC4.setOnClickListener(new DoubleClickListener() {
            @Override
            public void onSingleClick(View v) {
                speechMe.speak("Double Tap to Clear the Text"
                        , TextToSpeech.QUEUE_FLUSH, null, null);
            }
            @Override
            public void onDoubleClick(View v) {
                tv1.setText("");
                tv2.setText("");
                Toast.makeText(getActivity(), "Text Cleared", Toast.LENGTH_SHORT).show();
                speechMe.speak("Text Cleared"
                        , TextToSpeech.QUEUE_FLUSH, null, null);
            }
        });

            }
        });

        return view;

    }

    @Override
     public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SPEECH_INPUT) {
            if (resultCode == RESULT_OK&& null != data) {
                if (isFirst) {
                    isFirst = false;
                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    tv1.setText(result.get(0));
                    speechMe.speak("Double Tap and speak the Text Message"
                            , TextToSpeech.QUEUE_FLUSH, null, null);
                } else {

                    isFirst = true;
                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    tv2.setText(result.get(0));
                    speechMe.speak("Thank you ; Click the Send button"
                            , TextToSpeech.QUEUE_FLUSH, null, null);

                }
            }
        }
    }
    @Override
    public void onPause() {
        super.onPause();
        speechMe.stop();
    }
}
