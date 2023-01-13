package com.example.blind;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerCallLogAdapter extends RecyclerView.Adapter<RecyclerCallLogAdapter.MyViewHolder> {

    private Context context;
    private List<CallLogData> callList;
    private OnItemClicksListener conItemClicksListener;

    public RecyclerCallLogAdapter(Context context, List<CallLogData> callList,OnItemClicksListener onItemClicksListener) {

        this.context = context;
        this.callList = callList;
        this.conItemClicksListener = onItemClicksListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, final int viewType) {

        final View rowVw;
        rowVw = LayoutInflater.from(context).inflate(R.layout.item_calllog, parent, false);
        final MyViewHolder viewHd = new MyViewHolder(rowVw,conItemClicksListener);

        return viewHd;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder,  int position) {

        holder.textC1.setText(callList.get(position).getNumber());
        holder.textC2.setText(callList.get(position).getType());
        holder.textC3.setText(callList.get(position).getDateTime());
    }

    @Override
    public int getItemCount(){
        if (callList!=null)
            return callList.size();
        return 0;
    }



    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView textC1;
        TextView textC2;
        TextView textC3;
        OnItemClicksListener onItemClicksListener;

        public MyViewHolder( View itemView,OnItemClicksListener onItemClicksListener) {
            super(itemView);

            textC1 = itemView.findViewById(R.id.callNumber);
            textC2 = itemView.findViewById(R.id.callType);
            textC3 = itemView.findViewById(R.id.callDT);
            this.onItemClicksListener = onItemClicksListener;
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
          onItemClicksListener.onItemClicks(getAdapterPosition());
        }
    }

    public  interface OnItemClicksListener{
        void onItemClicks(int position);
    }




}
