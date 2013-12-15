package com.example.bunkmonitor;

/**
* Created by Tejas on 6/15/13.
*/
public class Entry {
    private String l_id;
    private String course_id;
    private int attended;
    private int bunked;
    private int cancelled;
    private int active;          //No need for this now

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

    public String getCourse_id() {
        return course_id;
    }

    public void setCourse_id(String course_id) {
        this.course_id = course_id;
    }

    public String getL_id() {
        return l_id;
    }

    public void setL_id(String l_id) {
        this.l_id = l_id;
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
