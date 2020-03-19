package com.example.zoomwroom.Entities;

/**
 * Rating
 *
 * A Rating object stores the number of thumbsUp and thumbsDown that a driver received.
 * The Rating class was refactored on Mar 13.
 *
 * @version 1.2
 *
 * Mar 13, 2020
 *
 * @author Sharyar Memon, Dulong Sang
 */
public class Rating {
    private int thumbsUp;
    private int thumbsDown;

    public Rating() {
        thumbsUp = 0;
        thumbsDown = 0;
    }

    /**
     * @param isThumbsUp true if the rider gives a thumbs up, false if gives a thumbs down
     */
    public void addRating(boolean isThumbsUp) {
        if (isThumbsUp) {
            thumbsUp++;
        } else {
            thumbsDown++;
        }
    }

    //<editor-fold desc="getter & setter">

    /**
     * @return  the number of thumbsUp that a driver received
     */
    public int getThumbsUp() {
        return thumbsUp;
    }

    public void setThumbsUp(int thumbsUp) {
        this.thumbsUp = thumbsUp;
    }

    /**
     * @return  the number of thumbsDown that a driver received.
     */
    public int getThumbsDown() {
        return thumbsDown;
    }

    public void setThumbsDown(int thumbsDown) {
        this.thumbsDown = thumbsDown;
    }
    //</editor-fold>
}
