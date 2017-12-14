package com.appsters.igit.taxicity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Sukalp on 04-Oct-16.
 */

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactsViewHolder> {

    ArrayList<Contacts> contacts;
    Context context;
    String number="";

    public ContactAdapter(ArrayList<Contacts> contacts) {
        this.contacts = contacts;
    }

    @Override
    public ContactsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context=parent.getContext();
        return new ContactsViewHolder(LayoutInflater.from(context).inflate(R.layout.contacts_row,parent,false));
    }

    @Override
    public void onBindViewHolder(ContactsViewHolder holder, int position) {
        holder.tv1.setText(contacts.get(position).getOne());
        holder.tv2.setText(contacts.get(position).getTwo());
        holder.tv3.setText(contacts.get(position).getThree());
        Picasso.with(context).load(contacts.get(position).getFour()).placeholder(R.mipmap.placeholder).into(holder.im1);
        number=contacts.get(position).getThree();
        holder.call_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_DIAL);
                i.setData(Uri.parse("tel: "+number));
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    public class ContactsViewHolder extends RecyclerView.ViewHolder
    {
        TextView tv1,tv2,tv3;
        CircleImageView im1;
        Button call_contact;
        public ContactsViewHolder(View itemView) {
            super(itemView);
            tv1=(TextView)itemView.findViewById(R.id.contactAcceptor);
            tv2=(TextView)itemView.findViewById(R.id.contactdateVar);
            tv3=(TextView)itemView.findViewById(R.id.contactNumber);
            im1=(CircleImageView) itemView.findViewById(R.id.contactsPicture);


            call_contact= (Button) itemView.findViewById(R.id.call_contact);



        }
    }
}
