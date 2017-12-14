package com.appsters.igit.taxicity;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;


public class FeedbackAdapter extends RecyclerView.Adapter<FeedbackAdapter.FeedBackHolder> {

    private ArrayList<Feedbackrow> feedbacks;
    private Context context;

    public FeedbackAdapter(ArrayList<Feedbackrow> feedbacks) {
        this.feedbacks=feedbacks;
    }

    @Override
    public FeedBackHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context=parent.getContext();
        return new FeedBackHolder(LayoutInflater.from(context).inflate(R.layout.feedback_row,parent,false));
    }

    @Override
    public void onBindViewHolder(FeedBackHolder holder, int position) {

        holder.fText.setText(feedbacks.get(position).getFbText());
        holder.fComment.setText(feedbacks.get(position).getFbComment());
        if (feedbacks.get(position).getFbLike().equalsIgnoreCase("nothappy"))
        {
            Picasso.with(context).load(R.drawable.ic_sad2).into(holder.fLike);
        }
        else if (feedbacks.get(position).getFbLike().equalsIgnoreCase("likedit"))
        {
            Picasso.with(context).load(R.drawable.thumb_up).into(holder.fLike);
        }
        else if (feedbacks.get(position).getFbLike().equalsIgnoreCase("lovedit"))
        {
            Picasso.with(context).load(R.drawable.ic_heart).into(holder.fLike);
        }
        Log.e("Image Error",feedbacks.get(position).getFbImage());
        Log.e("Like Error",feedbacks.get(position).getFbLike());
        Log.e("Comment Error",feedbacks.get(position).getFbComment());
        Log.e("Text Error",feedbacks.get(position).getFbText());
        Picasso.with(context).load(feedbacks.get(position).getFbImage()).placeholder(R.mipmap.placeholder).into(holder.fImage);
    }

    @Override
    public int getItemCount() {
        return feedbacks.size();
    }

    public class FeedBackHolder extends RecyclerView.ViewHolder
    {
        CircleImageView fImage;
        TextView fText,fComment;
        ImageView fLike;
        public FeedBackHolder(View itemView) {
            super(itemView);
            fComment=(TextView)itemView.findViewById(R.id.fComment);
            fImage=(CircleImageView)itemView.findViewById(R.id.fImage);
            fLike=(ImageView)itemView.findViewById(R.id.fLike);
            fText=(TextView)itemView.findViewById(R.id.fText);
        }
    }

}
