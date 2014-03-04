package com.example.bunkmonitor;

import java.util.ArrayList;
import java.util.List;

/**
* Created by Tejas on 6/15/13.
*/
public class Entry {

    private String course_lid;
    private int attended;
    private int bunked;
    private int cancelled;
    private String local_id;
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

    public String getCourse_lid() {
        return course_lid;
    }

    public void setCourse_lid(String course_lid) {
        this.course_lid = course_lid;
    }

    public static List<Entry> getEntryList(List<Course> activeCList){
        List<Entry> entryList = new ArrayList<Entry>();
        for(Course c: activeCList){
            Entry entry = new Entry();
            entry.setCourse_lid(c.getLocalId());
            entryList.add(entry);
        }
        return entryList;
    }

	public String getL_id() {
		// TODO Auto-generated method stub
		return this.local_id;
	}

	public void setL_id(String local_id) {
		// TODO Auto-generated method stub
		this.local_id=local_id;
	}
}
