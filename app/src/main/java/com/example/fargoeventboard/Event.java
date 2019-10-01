package com.example.fargoeventboard;

public class Event {
    //the event class defines an object for each event, which the API
    // interface and class can use to form a list of Events

    //you will need get methods for all the general object utilities


    private int id;
    private String image_url;
    private String event_description;
    private String title;
    private String start_date_time;
    private String end_date_time;
    private String location;
    private boolean featured;



    public int getId(){ return id; }

    public String getImage_url(){
        return image_url;
    }

    public String getTitle(){
        return title;
    }

    public String getStart_date_time(){
        return start_date_time;
    }

    public String getEnd_date_time(){
        return end_date_time;
    }

    public String getLocation(){
        return location;
    }

    public boolean getFeatured(){ return featured; }

    public String getEventDescription(){ return event_description; }



    public String getFormattedTime(){
        //all events are on the same day
        String build = "";
        build += start_date_time.substring(5,7); //month
        build +="/";
        build += start_date_time.substring(8,10); //day
        build +="/";
        build += start_date_time.substring(2,4); //year
        build += " ";

        //start time
        int startHour = Integer.parseInt(start_date_time.substring(11,13)); //start hour
        if(startHour%12 == 0){ // if time is 12, don't mod 12 (would result in time of 0:00)
            build += 12;
        }else {
            build += startHour % 12;
        }
        build += start_date_time.substring(13,16); //start minutes (and :)
        //determine AM or PM
        if(startHour>11){
            build += "PM";
        }else{
            build += "AM";
        }

        //divider
        build += " - ";

        //end time
        int endHour = Integer.parseInt(end_date_time.substring(11,13)); //start hour
        if(endHour%12 == 0){ // if time is 12, don't mod 12 (would result in time of 0:00)
            build += 12;
        }else {
            build += endHour % 12;
        }
        build += end_date_time.substring(13,16); //start minutes (and :)
        //determine AM or PM
        if(endHour>11){
            build += "PM";
        }else{
            build += "AM";
        }

        return build;
    }
}
