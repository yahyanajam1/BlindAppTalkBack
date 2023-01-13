package com.example.blind;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView    ;

public class RecyclerInboxAdapter extends RecyclerView.Adapter<RecyclerInboxAdapter.MyViewHolder>{

    private Context context;
    private List<InboxData> smsList;
    private OnItemClickListener monItemClickListener;

   public RecyclerInboxAdapter(Context context, List<InboxData> smsList,OnItemClickListener onItemClickListener){

        this.context = context;
        this.smsList = smsList;
        this.monItemClickListener = onItemClickListener;

    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View rowView;
        rowView = LayoutInflater.from(context).inflate(R.layout.item_inbox,parent,false );
        MyViewHolder  vwholder = new MyViewHolder(rowView,monItemClickListener);

        return vwholder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.text1.setText(smsList.get(position).getNumber());
        holder.text2.setText(smsList.get(position).getBody());
        holder.text3.setText(smsList.get(position).getTimeDate());
   }

    @Override
    public int getItemCount() {
        if (smsList!=null)
            return smsList.size();
        return 0;
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView text1;
        TextView text2;
        TextView text3;
        OnItemClickListener onItemClickListener;

        public MyViewHolder(View itemView,OnItemClickListener onItemClickListener) {
            super(itemView);

            text1 = itemView.findViewById(R.id.smsNumber);
            text2 = itemView.findViewById(R.id.smsDetail);
            text3 = itemView.findViewById(R.id.smsDateTime);
            this.onItemClickListener = onItemClickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onItemClickListener.onItemClick(getAdapterPosition());

        }
    }

   public  interface OnItemClickListener{
       void onItemClick(int position);
   }


}
