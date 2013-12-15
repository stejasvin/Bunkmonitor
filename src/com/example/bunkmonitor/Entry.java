package com.example.bunkmonitor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
* Created by Tejas on 6/15/13.
*/
public class Entry {

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

    public static List<Entry> getEntryList(List<Course> activeCList){
        List<Entry> entryList = new ArrayList<Entry>();
        for(Course c: activeCList){
            Entry entry = new Entry();
            entry.setCourse_id(c.getId());
            entryList.add(entry);
        }
        return entryList;
    }
}
