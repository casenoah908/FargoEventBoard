package com.example.fargoeventboard;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;


import com.squareup.picasso.Picasso;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{




    private ArrayList<String> eventNames = new ArrayList<String>();
    private ArrayList<String> eventImages = new ArrayList<String>();
    private ArrayList<String> eventDetails = new ArrayList<String>();
    private ArrayList<String> eventIDs = new ArrayList<>();
    String authorizationToken;
    private Context context;

    //constructor
    public RecyclerViewAdapter(Context context, ArrayList<String> eventImages, ArrayList<String> eventNames, ArrayList<String> eventDetails, ArrayList<String> eventIDs, String authorizationToken){

        this.eventImages = eventImages;
        this.eventNames = eventNames;
        this.eventDetails = eventDetails;
        this.eventIDs = eventIDs;
        this.authorizationToken = authorizationToken;
        this.context = context;
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    //this method is where we retrieve our items from the event Lists and set them into our current view
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        //put in log if you need to see which item failed



        //IMPORTANT! USE PICASSO TO GET AND SET IMAGES HERE!
        //IF IT APPEARS WE"RE ALLOWED IT, USE GLIDE TO TRANSFER IMAGES
        Picasso.get().load(eventImages.get(position)).resize(250,200).into(holder.eventImage);


        //get event name
        holder.eventName.setText(eventNames.get(position));
        //set marquee in motion
        holder.eventName.setSelected(true);
        holder.eventName.setSelected(true);
        //get event detail
        holder.eventDetail.setText(eventDetails.get(position));

        holder.eventItemLayout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //later we need this to load a new activity.
                //this message does not work. Thats alright, we don't really need it
                openEventActivity(Integer.parseInt(eventIDs.get(position))); //pass event's ID to activity opener method
            }
        });

    }

    @Override
    public int getItemCount() {
        return eventNames.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        AppCompatImageView eventImage;
        TextView eventName;
        TextView eventDetail;
        RelativeLayout eventItemLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            eventImage = itemView.findViewById(R.id.event_image);
            eventName = itemView.findViewById(R.id.event_name);
            eventDetail = itemView.findViewById(R.id.event_detail);
            eventItemLayout = itemView.findViewById(R.id.parent_layout);
        }
    }
    //end View Holder method

    //method to open Event Activity
    public void openEventActivity(int id){
        Intent intent = new Intent(context,EventActivity.class);
        intent.putExtra("id",id); //add id to intent
        intent.putExtra("token",authorizationToken);
        context.startActivity(intent);
    }
    //end openEventActivity method
}
