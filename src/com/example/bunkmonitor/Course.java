package com.example.bunkmonitor;

import java.util.HashMap;

/**
* Created by Tejas on 6/15/13.
*/
public class Course {
    private String id,l_id,credits,name,slot,prof;

    public Course(){

    }

    public String getId(){
        return this.id;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getLocalId(){
        return this.l_id;
    }

    // setting ID
    public void setLocalId(String l_id){
        this.l_id = l_id;
    }

    // getting credits
    public String getCredits(){
        return this.credits;
    }

    // setting credits
    public void setCredits(String credits){
        this.credits = credits;
    }

    // getting prof
    public String getProf(){
        return this.prof;
    }

    // setting prof
    public void setProf(String prof){
        this.prof = prof;
    }

    public String getL_id() {
        return l_id;
    }

    public void setL_id(String l_id) {
        this.l_id = l_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSlot() {
        return slot;
    }

    public void setSlot(String slot) {
        this.slot = slot;
    }
}
