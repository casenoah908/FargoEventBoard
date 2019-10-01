package com.example.fargoeventboard;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.JsonToken;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;


import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    Token authorizationToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //spawn layout
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        //instantiate views
        Button loginButton = (Button)(findViewById(R.id.loginButton));
        final EditText username = (EditText)(findViewById(R.id.usernameTextBox));
        final EditText password = (EditText)(findViewById(R.id.passwordTextBox));
        final TextView title = findViewById(R.id.FargoEvents);



        //RETROFIT instantiation
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl( "https://challenge.myriadapps.com/api/v1/")
                .addConverterFactory(GsonConverterFactory.create());
        //interface methods add to the base url with their relative urls
        Retrofit retrofit = builder.build(); //build whats defined above
        final APIinterface client = retrofit.create(APIinterface.class);



        //LOGIN button press
        loginButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //Do not Login if username or password has not been entered
                //Note: each case was handeled seperately so that Error message could change relative to what was missing
                if ((username.getText().toString().equals("")) && (password.getText().toString().equals(""))) {
                    Toast.makeText(MainActivity.this, "A Username and Password has not been entered!", Toast.LENGTH_LONG).show();
                } else if (username.getText().toString().equals("")) {
                    Toast.makeText(MainActivity.this, "A Username has not been entered!", Toast.LENGTH_LONG).show();
                } else if (password.getText().toString().equals("")) {
                    Toast.makeText(MainActivity.this, "A Password has not been entered!", Toast.LENGTH_LONG).show();
                } else { // if both a username and password have been entered, then...
                    //create a call using username and password.
                    Call<Token> call = client.login(username.getText().toString(), password.getText().toString());
                    //make the call
                    call.enqueue(new Callback<Token>() {
                        @Override
                        public void onResponse(Call<Token> call, Response<Token> response) {
                            //on response, store token and open EventListActivity
                            authorizationToken = response.body(); //store token
                            openEventListActivity(authorizationToken.getToken());
                        }

                        @Override
                        public void onFailure(Call<Token> call, Throwable t) {
                            //on failure, display error message
                            Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                            //If you are getting the on failure message EPERM, you can fix it by uninstalling the app then reinstalling it.
                        }
                    });
                }
            }
        });
        //end of button press
    }
    //end onCreate



    //method to open Event List Activity
    public void openEventListActivity(String authorizationToken){
        Intent intent = new Intent(this,EventListActivity.class);
        intent.putExtra("token",authorizationToken); //add token to event
        startActivity(intent);
    }
    //end openEventListActivity method

}
//end class