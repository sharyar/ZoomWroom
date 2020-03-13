package com.example.zoomwroom.Entities;

import java.util.ArrayList;

/**
 * Rating
 *
 * Stores the rating given to a driver by a user. Is stored within a drive request as it is
 * provided by the user at the end of a drive.
 *
 * @see DriveRequest
 *
 * @version 1.0
 *
 * Feb 26, 2020
 *
 * @author Sharyar Memon
 *
 */
public class Rating {
    private int thumbsUp;
    private int thumbsDown;

    public Rating() {
        thumbsUp = 0;
        thumbsDown = 0;
    }

    //<editor-fold desc="getter & setter">
    public int getThumbsUp() {
        return thumbsUp;
    }

    public void setThumbsUp(int thumbsUp) {
        this.thumbsUp = thumbsUp;
    }

    public int getThumbsDown() {
        return thumbsDown;
    }

    public void setThumbsDown(int thumbsDown) {
        this.thumbsDown = thumbsDown;
    }

    //</editor-fold>
}
