package com.example.bunkmonitor;

/**
* Created by Tejas on 6/15/13.
*/
public class Course {
    private String id,l_id,credits,name,slot,prof;
    private int attended;
    private int bunked;
    private int cancelled;
    private int active;
    private int udBunks;
    private int isLab;
    private int maxBunks;

    public int getIs85() {
        return is85;
    }

    public void setIs85(int is85) {
        this.is85 = is85;
    }

    private int is85;

    public int getMaxBunks() {
        return maxBunks;
    }

    public void setMaxBunks(int maxBunks) {
        this.maxBunks = maxBunks;
    }

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

    public int getUdBunks() {
        return udBunks;
    }

    public void setUdBunks(int udBunks) {
        this.udBunks = udBunks;
    }

    public int getIsLab() {
        return isLab;
    }

    public void setIsLab(int isLab) {
        this.isLab = isLab;
    }
}

