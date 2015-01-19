package models;

/**
 * Created by BlackMark on 18/01/2015.
 */

import java.util.Date;

public class Meeting {
    private Skier skier1;
    private Skier skier2;
    private Lift lift;
    private Date date;

    // Default constructor
    public Meeting(Skier skier1, Skier skier2, Lift lift) {
        this.skier1=skier1;
        this.skier2= skier2;
        this.lift=lift;
        this.date = new Date();
    }
}