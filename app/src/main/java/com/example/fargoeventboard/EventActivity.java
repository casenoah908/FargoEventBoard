package com.example.fargoeventboard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Header;
import retrofit2.http.Path;

public class EventActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //standard create activity stuff
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        //view instantiation
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final LinearLayout linearLayout = findViewById(R.id.linearLayout);
        final ImageView image = (ImageView) findViewById(R.id.imageView);
        final TextView eventTitle = findViewById(R.id.eventTitle);
        final TextView eventTime = findViewById(R.id.eventTime);
        final TextView eventDesc = findViewById(R.id.eventDesc);
        final TextView eventLocation = findViewById(R.id.eventAddress);
        //id for GET request to server (for speaker and event detail)
        final int id = getIntent().getExtras().getInt("id");
        final String authorizationToken = getIntent().getExtras().getString("token");



        //RETROFIT instantiation
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl( "https://challenge.myriadapps.com/api/v1/")
                .addConverterFactory(GsonConverterFactory.create());
        //interface methods add to the base url with their relative urls
        Retrofit retrofit = builder.build(); //build whats defined above
        final APIinterface client = retrofit.create(APIinterface.class);



        //get event info from server
        Call<Event> eventDetail = client.eventDetail(Integer.toString(id), authorizationToken);
        eventDetail.enqueue(new Callback<Event>() {
            @Override
            public void onResponse(Call<Event> call, Response<Event> response) {
                //import event items into layout (up to speaker section)
                Picasso.get().load(response.body().getImage_url()).resize(415,320).into(image); //image WORKS
                eventTitle.setText(response.body().getTitle()); //title WORKS
                eventTime.setText(response.body().getFormattedTime()); //time WORKS
                eventDesc.setText(response.body().getEventDescription()); //desc
                eventLocation.setText(response.body().getLocation()); //address WORKS
            }

            @Override
            public void onFailure(Call<Event> call, Throwable t) {
                //if API cannot get information
                Toast.makeText(EventActivity.this,"Error in retrieving event detail: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        //end of events enqueue



        //get speaker info from server
        Call<Speaker> speakers = client.speaker(Integer.toString(id), authorizationToken);
        speakers.enqueue(new Callback<Speaker>() {
            @Override
            public void onResponse(Call<Speaker> call, Response<Speaker> response) {
                //add text views with speaker detail for a single speaker
                    //add header that says speaker (singular)
                    TextView speakerHeader = new TextView(EventActivity.this);
                    speakerHeader.setText("Speaker");
                    speakerHeader.setPadding(50,20,50,0);
                    speakerHeader.setTextColor(Color.parseColor("#A9A9A9"));
                    linearLayout.addView(speakerHeader);


                //for each speaker...
                //for(Speaker speaker : response.body()){
                //add a speaker title
                TextView speakerName = new TextView(EventActivity.this);
                speakerName.setText(response.body().getFullName());
                speakerName.setPadding(16,20,16,0);
                speakerName.setTextColor(Color.parseColor("#000000"));
                linearLayout.addView(speakerName);
                //add the speaker desc.
                TextView speakerBio = new TextView(EventActivity.this);
                speakerBio.setText(response.body().getBio());
                speakerBio.setPadding(16,20,16,20);
                speakerBio.setTextColor(Color.parseColor("#000000"));
                linearLayout.addView(speakerBio);

            }
            @Override
            public void onFailure(Call<Speaker> call, Throwable t) {
                //if API cannot get information
                Call<List<Speaker>> speakers = client.speakers(Integer.toString(id), authorizationToken);
                speakers.enqueue(new Callback<List<Speaker>>() {
                    @Override
                    public void onResponse(Call<List<Speaker>> call, Response<List<Speaker>> response) {
                        //add text views with speaker detail for a multiple speakers
                        //Header
                         //if multiple speakers
                            //add a header that says speakers (plural)
                            TextView speakerHeader = new TextView(EventActivity.this);
                            speakerHeader.setText("Speakers");
                            speakerHeader.setPadding(50, 20, 50, 0);
                            speakerHeader.setTextColor(Color.parseColor("#A9A9A9"));
                            linearLayout.addView(speakerHeader);


                            //for each speaker...
                            for(Speaker speaker : response.body()) {
                                //add a speaker title
                                TextView speakerName = new TextView(EventActivity.this);
                                speakerName.setText(speaker.getFullName());
                                speakerName.setPadding(16, 20, 16, 0);
                                speakerName.setTextColor(Color.parseColor("#000000"));
                                linearLayout.addView(speakerName);
                                //add the speaker desc.
                                TextView speakerBio = new TextView(EventActivity.this);
                                speakerBio.setText(speaker.getBio());
                                speakerBio.setPadding(16, 20, 16, 20);
                                speakerBio.setTextColor(Color.parseColor("#000000"));
                                linearLayout.addView(speakerBio);

                            }
                    }
                    //end of nested List<Speaker> enqueue

                    @Override
                    public void onFailure(Call<List<Speaker>> call, Throwable t) {
                        Toast.makeText(EventActivity.this, "Error in retrieving Speaker details: "+t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        //end of <Speaker> enqueue
    }
    //end of onCreate



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


    //method to open Main Activity (log in screen)
    public void openMainActivity(){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
    //end openMainActivity method
}
