package com.example.bunkmonitor;

import java.util.HashMap;

/**
* Created by Tejas on 6/15/13.
*/
public class Course {
    private String id,l_id,credits,name,slot,prof;
    private int attended;
    private int bunked;
    private int cancelled;
    private int active;

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

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public int getCancelled() {
        return cancelled;
    }

    public void setCancelled(int cancelled) {
        this.cancelled = cancelled;
    }

    public int getBunked() {
        return bunked;
    }

    public void setBunked(int bunked) {
        this.bunked = bunked;
    }

    public int getAttended() {
        return attended;
    }

    public void setAttended(int attended) {
        this.attended = attended;
    }

    public void incAttended(){
        attended++;
    }

    public void decAttended(){
        attended--;
    }

    public void incBunked(){
        bunked++;
    }

    public void decBunked(){
        bunked--;
    }
    public void incCancelled(){
        cancelled++;
    }

    public void decCancelled(){
        cancelled--;
    }
}
