package com.example.bunkmonitor;

/**
 * Created by Tejas on 6/15/13.
 */
public class EntryDetails {
    private String l_id;
    private String course_lid;
    private int status;
    private String time;
    private int entered;


    public int getEntered() {
        return entered;
    }

    public void setEntered(int entered) {
        this.entered = entered;
    }

    public String getSlot() {
        return slot;
    }

    public void setSlot(String slot) {
        this.slot = slot;
    }

    private String slot;

    public String getL_id() {
        return l_id;
    }

    public void setL_id(String l_id) {
        this.l_id = l_id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCourse_lid() {
        return course_lid;
    }

    public void setCourse_lid(String course_lid) {
        this.course_lid = course_lid;
    }



}
