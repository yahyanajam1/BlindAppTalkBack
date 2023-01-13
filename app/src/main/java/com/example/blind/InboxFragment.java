package com.example.blind;


import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
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

public class InboxFragment extends Fragment implements RecyclerInboxAdapter.OnItemClickListener {

    private TextToSpeech xoSpeech;
    private View view1;
    private RecyclerView myRecycle;
    private List<InboxData> smsView;

    public InboxFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                               Bundle savedInstanceState) {
        view1 = inflater.inflate(R.layout.fragment_inbox, container, false);

        xoSpeech = new TextToSpeech(getActivity(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                xoSpeech.setLanguage(Locale.ENGLISH); }});

        smsView = new ArrayList<>();

       Uri uriSms = Uri.parse("content://sms/inbox");

        Cursor csr;
        csr = requireActivity().getContentResolver().query(uriSms,null,null,null,null);

        assert csr != null;
        if (csr.moveToFirst()) {
            for (int i = 0; i < csr.getCount(); i++) {
                InboxData sms = new InboxData();
                sms.setBody(csr.getString(csr.getColumnIndexOrThrow("body")));
                sms.setNumber(csr.getString(csr.getColumnIndexOrThrow("address")));

                int msgDate = csr.getColumnIndex("date");
                String strMsg = csr.getString(msgDate);
                Date msgDateTime = new Date(Long.valueOf(strMsg));
                String datMsg = DateFormat.getDateTimeInstance(DateFormat.FULL,DateFormat.DATE_FIELD).format(msgDateTime.getTime());
                sms.setTimeDate(datMsg);

                smsView.add(sms);
                csr.moveToNext();
            }
        }
         csr.close();

        myRecycle = view1.findViewById(R.id.recycleInbox);
        RecyclerInboxAdapter recyclerAdapter = new RecyclerInboxAdapter(getContext(),smsView,this);
        myRecycle.setLayoutManager(new LinearLayoutManager(getActivity()));
        myRecycle.setAdapter(recyclerAdapter);

        return view1;
    }

    @Override
    public void onItemClick(int position) {
      String stg1 = smsView.get(position).getNumber();
      String stg2 = smsView.get(position).getTimeDate();
      String stg3 = smsView.get(position).getBody();
      xoSpeech.speak("Message From",TextToSpeech.QUEUE_FLUSH,null,null);
      xoSpeech.speak(stg1,TextToSpeech.QUEUE_ADD,null,null);
      xoSpeech.speak("on",TextToSpeech.QUEUE_ADD,null,null);
      xoSpeech.speak(stg2,TextToSpeech.QUEUE_ADD,null,null);
      xoSpeech.speak("says",TextToSpeech.QUEUE_ADD,null,null);
      xoSpeech.speak(stg3,TextToSpeech.QUEUE_ADD,null,null);
      Toast.makeText(getActivity(),stg1,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPause() {
        super.onPause();
        xoSpeech.stop();
    }

    @Override
    public void onResume() {
        super.onResume();
        xoSpeech.speak("Inbox is open ; Single Click will speak the Details ; or Swipe right for Messaging"
                ,TextToSpeech.QUEUE_FLUSH,null,null);
    }

}
