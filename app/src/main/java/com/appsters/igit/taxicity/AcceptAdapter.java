package com.appsters.igit.taxicity;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class AcceptAdapter extends RecyclerView.Adapter<AcceptAdapter.AcceptViewHolder> {

    private ArrayList<Accept> accepts;
    Context context;

    public AcceptAdapter(ArrayList<Accept> accepts) {
        this.accepts=accepts;
    }

    @Override
    public AcceptViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context=parent.getContext();
        return new AcceptViewHolder(LayoutInflater.from(context).inflate(R.layout.acceptor_row,parent,false));
    }

    @Override
    public void onBindViewHolder(AcceptViewHolder holder, final int position) {
        holder.textViewField1.setText(accepts.get(position).getTexField1());
        String con=accepts.get(position).getTextField4()+" ► "+accepts.get(position).getTextField5();
        holder.textViewField2.setText(con);
        holder.textViewField3.setText(accepts.get(position).getTextField7());
        holder.textViewField4.setText(accepts.get(position).getTextField6());
        holder.textViewField5.setText(accepts.get(position).getTextField3());
        Picasso.with(context).load(accepts.get(position).getTextField8()).placeholder(R.mipmap.placeholder).into(holder.acceptPicture);
        holder.acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AcceptRequests.getInstance().acceptConfirm(accepts.get(position).getTexField1());
            }
        });
    }

    @Override
    public int getItemCount() {
        return accepts.size();
    }

    public class AcceptViewHolder extends RecyclerView.ViewHolder
    {
        TextView textViewField1,textViewField2,textViewField3,textViewField4,textViewField5;
        Button acceptButton;
        ImageView acceptPicture;
        public AcceptViewHolder(View itemView) {
            super(itemView);
            acceptPicture= (ImageView) itemView.findViewById(R.id.acceptPicture);
            textViewField1=(TextView)itemView.findViewById(R.id.acceptField1);
            textViewField2=(TextView)itemView.findViewById(R.id.acceptField2);
            textViewField3=(TextView)itemView.findViewById(R.id.acceptField3);
            textViewField4=(TextView)itemView.findViewById(R.id.acceptField4);
            textViewField5=(TextView)itemView.findViewById(R.id.acceptField5);
            acceptButton=(Button)itemView.findViewById(R.id.acceptField6);
        }
    }
}
