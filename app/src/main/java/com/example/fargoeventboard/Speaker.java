package com.example.fargoeventboard;


public class Speaker {

    private int id;
    private String first_name;
    private String last_name;
    private String bio;

    public int getId(){ return id; }

    public String getFirstName(){ return first_name; }

    public String getLastName(){ return last_name; }

    public String getBio(){ return bio; }

    public String getFullName(){ return (first_name + " "+last_name); } //put the names together for actual use

}
