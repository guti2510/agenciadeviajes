package com.luispc.travelangency;

import android.app.Application;

public class global extends Application {


    // Variables
    private String email = "";

    // Getter email
    public String getEmail(){
        return email;
    }

    // Setter email
    public void setEmail(String nombre){
        this.email = nombre;
    }

}