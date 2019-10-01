package com.example.fargoeventboard;

import android.media.session.MediaSession;
import android.util.JsonToken;

import java.util.List;


import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIinterface {
    //The purpose of this interface is to provide methods for main activity to use


    //token: supersecrettoken should be used as an authorization header, by adding a parameter as such:
    //@Header("authorization") String token

    //lists events found from api
    @GET("events")
    Call<List<Event>> listEvents(@Header("authorization") String token);

    //posts login information
    @POST("login")
    Call<Token> login(
            @Query("Username") String username,
            @Query("Password") String password);

    @GET ("events/{id}")
    Call<Event> eventDetail(@Path("id") String id, @Header("authorization") String token);

    //for a single speaker object (which is what all items under url give)
    @GET ("speakers/{id}")
    Call<Speaker> speaker(@Path("id") String id, @Header("authorization") String token);

    //for a list of speakers (which is what challenge asks for)
    @GET ("speakers/{id}")
    Call<List<Speaker>> speakers(@Path("id") String id, @Header("authorization") String token);




}
