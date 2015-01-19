package models;

/**
 * Created by BlackMark on 18/01/2015.
 */

public class Lift {
    private String name;
    private String ski_arena;
    private int seats;

    // Constructor which sets the location to the specified parameters
    public Lift(String name, String ski_arena, int seats) {

        this.name = name;
        this.seats = seats;
        this.ski_arena = ski_arena;
    }

}
