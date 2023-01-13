package com.example.blind;


import android.database.Cursor;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.provider.CallLog;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class CallLogFragment extends Fragment implements RecyclerCallLogAdapter.OnItemClicksListener {

    private TextToSpeech toSpeech;
    private View viewC;
    private RecyclerView caRecycle;
    private List<CallLogData>callsListView;

    public CallLogFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        viewC = inflater.inflate(R.layout.fragment_call_log, container, false);

        toSpeech = new TextToSpeech(getActivity(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                toSpeech.setLanguage(Locale.ENGLISH); }});

        callsListView = new ArrayList<>();

        StringBuilder sb = new StringBuilder();
        sb.append("Call Details :\n\n");

        Cursor cr;
        cr = requireActivity().getContentResolver().query(CallLog.Calls.CONTENT_URI, null, null, null, CallLog.Calls.DEFAULT_SORT_ORDER);

        assert cr != null;
        if (cr.moveToFirst()) {
            for (int i = 0; i < cr.getCount(); i++) {
                CallLogData cons = new CallLogData();

                cons.setNumber(cr.getString(cr.getColumnIndex(CallLog.Calls.NUMBER)));
                cons.setType(cr.getString(cr.getColumnIndex(CallLog.Calls.TYPE)));

                int date = cr.getColumnIndex(CallLog.Calls.DATE);
                String strDate = cr.getString(date);
                Date dateTime = new Date(Long.valueOf(strDate));
                String datEti = DateFormat.getDateTimeInstance(DateFormat.FULL,DateFormat.DATE_FIELD).format(dateTime.getTime());
                cons.setDateTime(datEti);

                callsListView.add(cons);
                cr.moveToNext();
            }
        }

        cr.close();

        caRecycle = viewC.findViewById(R.id.recycleCallLog);
        RecyclerCallLogAdapter recyclerAdapter = new RecyclerCallLogAdapter(getContext(), callsListView,this);
        caRecycle.setLayoutManager(new LinearLayoutManager(getActivity()));
        caRecycle.setAdapter(recyclerAdapter);

        return viewC;
    }

    @Override
    public void onItemClicks(int position) {
           String str1 = callsListView.get(position).getType();
           String str2 = callsListView.get(position).getNumber();
           String str3 = callsListView.get(position).getDateTime();
           if (str1.equals("1")){
               toSpeech.speak("INCOMING Call From",TextToSpeech.QUEUE_FLUSH,null,null); }
           if(str1.equals("2")){
               toSpeech.speak("OUTGOING Call To",TextToSpeech.QUEUE_FLUSH,null,null); }
           if(str1.equals("3")){
               toSpeech.speak("MISSED Call From",TextToSpeech.QUEUE_FLUSH,null,null); }
           toSpeech.speak(str2,TextToSpeech.QUEUE_ADD,null,null);
           toSpeech.speak("on",TextToSpeech.QUEUE_ADD,null,null);
           toSpeech.speak(str3,TextToSpeech.QUEUE_ADD,null,null);
           Toast.makeText(getActivity(),str2,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPause() {
        super.onPause();
        toSpeech.stop();
    }

    @Override
    public void onResume() {
        super.onResume();
        toSpeech.speak("Call log is open ; Single Click will speak the Details ; or Swipe right for Dialer"
                ,TextToSpeech.QUEUE_FLUSH,null,null);
    }

}
