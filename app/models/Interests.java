package models;

/**
 * Created by Ivan on 19.01.2015.
 */
public class Interests {
    private String interest;

    public Interests() {
        interest = "";
    }

    public Interests(String interest) {
        setInterest(interest);
    }

    public boolean matchInterest(Interests interests) {
        return this.interest.compareTo(interests.getInterest()) == 0;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }

    public String getInterest() {
        return this.interest;
    }
}
