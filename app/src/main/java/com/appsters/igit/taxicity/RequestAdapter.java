package com.appsters.igit.taxicity;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.RequestViewholder> {

    private ArrayList<Request> requests;
    private Context context;

    public RequestAdapter(ArrayList<Request> requests) {
        this.requests=requests;
    }

    @Override
    public RequestViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        context=parent.getContext();
        return new RequestViewholder(LayoutInflater.from(context).inflate(R.layout.request_row,parent,false));
    }

    @Override
    public void onBindViewHolder(final RequestViewholder holder, int position) {
        holder.textViewField1.setText(requests.get(position).getTexField1());
        String con=requests.get(position).getTextField2()+" ► "+requests.get(position).getTextField3();
        holder.textViewField2.setText(con);
        holder.textViewField3.setText(requests.get(position).getTextField4());
        holder.textViewField4.setText(requests.get(position).getTextField5());
        holder.textViewField5.setText(requests.get(position).getTextField6());
        Log.e("Photo Error :",requests.get(position).getTextField7());
        Picasso.with(context).load(requests.get(position).getTextField7()).placeholder(R.mipmap.placeholder).into(holder.picture);
        holder.requestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Notifier.getInstance().sendRequest(requests.get(holder.getAdapterPosition()).getTexField1());
            }
        });
    }

    @Override
    public int getItemCount() {
        return requests.size();
    }

    public class RequestViewholder extends RecyclerView.ViewHolder
    {
        TextView textViewField1,textViewField2,textViewField3,textViewField4,textViewField5;
        Button requestButton;
        ImageView picture;
        public RequestViewholder(View itemView) {
            super(itemView);
            picture= (ImageView) itemView.findViewById(R.id.requestImageField);
            textViewField1=(TextView)itemView.findViewById(R.id.requestField1);
            textViewField2=(TextView)itemView.findViewById(R.id.requestField2);
            textViewField3=(TextView)itemView.findViewById(R.id.requestField3);
            textViewField4=(TextView)itemView.findViewById(R.id.requestField4);
            textViewField5=(TextView)itemView.findViewById(R.id.requestField5);
            requestButton=(Button)itemView.findViewById(R.id.requestField6);
        }
    }
}
