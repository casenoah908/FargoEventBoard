package com.example.fargoeventboard;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EventListActivity extends AppCompatActivity {

    //authorization token for GET requests
    String authorizationToken;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //spawn layout
        //quick try catch for testing purposes. Just to see why the layout won't open if it decides not to
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_event_list);
        }catch(Exception e){
            Log.e("Error Message: ",e.getMessage());
        }

        authorizationToken = getIntent().getExtras().getString("token");

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);




        //RETROFIT instantiation
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl( "https://challenge.myriadapps.com/api/v1/")
                .addConverterFactory(GsonConverterFactory.create());
        //interface methods add to the base url with their relative urls
        Retrofit retrofit = builder.build(); //build whats defined above
        final APIinterface client = retrofit.create(APIinterface.class);




        //create call of type list of events (for later)
        Call<List<Event>> eventsCall = client.listEvents(authorizationToken);
        eventsCall.enqueue(new Callback<List<Event>>() {
            @Override
            public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
                ArrayList<String> eventNames = new ArrayList<String>();
                ArrayList<String> eventImages = new ArrayList<String>();
                ArrayList<String> eventDetails = new ArrayList<String>();
                ArrayList<String> eventIDs = new ArrayList<String>();


                for(Event event : response.body()){
                    //store event in recycler view
                    eventNames.add(event.getTitle());
                    eventDetails.add(event.getFormattedTime());
                    eventImages.add(event.getImage_url());
                    eventIDs.add(Integer.toString(event.getId()));
                }
                //Send event components to recycler view
                spawnRecyclerView(eventNames,eventDetails,eventImages,eventIDs);
            }
            @Override
            public void onFailure(Call<List<Event>> call, Throwable t) {
                //on failure, display error message
                Toast.makeText(EventListActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        //end get request for events
    }
    //end onCreate method



    //overriden overflow menu methods
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.overflow_icon, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        openMainActivity();
        return super.onOptionsItemSelected(item);
    }
    //end overriden overflow menu methods



    //method to create recycler view for events
    private void spawnRecyclerView(ArrayList<String> eventNames,ArrayList<String> eventDetails,ArrayList<String> eventImages,ArrayList<String> eventIDs){
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this,eventImages,eventNames,eventDetails,eventIDs, authorizationToken);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    //method to open Main Activity (log in screen)
    public void openMainActivity(){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
    //end openMainActivity method
}