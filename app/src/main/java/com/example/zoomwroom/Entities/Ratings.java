package com.example.zoomwroom.Entities;

import java.util.ArrayList;

public class Ratings {
    private ArrayList<Rating> ratingList;
    private int numThumbsUp;
    private int numThumbsDown;

    public Ratings() {
        ratingList = new ArrayList<>();
        numThumbsUp = 0;
        numThumbsDown = 0;
    }

    public void add(boolean rating) {
        if (rating) {
            numThumbsUp++;
        } else {
            numThumbsDown++;
        }
        ratingList.add(new Rating(rating));
    }

    public void add(Rating rating) {
        if (rating.getThumbStatus() == Rating.THUMBSUP) {
            numThumbsUp++;
        } else {
            numThumbsDown++;
        }
        ratingList.add(rating);
    }

    //<editor-fold desc="Getter & Setter">
    public ArrayList<Rating> getRatingList() {
        return ratingList;
    }

    public void setRatingList(ArrayList<Rating> ratingList) {
        this.ratingList = ratingList;
    }

    public int getNumThumbsUp() {
        return numThumbsUp;
    }

    // ! Do NOT call this method, only for firebase !
    public void setNumThumbsUp(int numThumbsUp) {
        this.numThumbsUp = numThumbsUp;
    }

    public int getNumThumbsDown() {
        return numThumbsDown;
    }

    // ! Do NOT call this method, only for firebase !
    public void setNumThumbsDown(int numThumbsDown) {
        this.numThumbsDown = numThumbsDown;
    }
    //</editor-fold>
}
