/**
 * Write a description of class EfficientRater here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.util.*;

public class EfficientRater implements Rater{
    
    private String myID;
    //private ArrayList<Rating> myRatings;
    private HashMap<String,Rating> myRatings;
    
    public EfficientRater(String id) {
        myID = id;
        //myRatings = new ArrayList<Rating>();
        myRatings = new HashMap<String,Rating>();
    }

    public void addRating(String item, double rating) {
        myRatings.put(item,new Rating(item,rating));
    }

    public boolean hasRating(String item) {
        if (myRatings.containsKey(item)){
            return true;
        }
        return false;
    }

    public String getID() {
        return myID;
    }

    public double getRating(String item) {
        Rating r = myRatings.get(item);
        return r.getValue();
    }

    public int numRatings() {
        return myRatings.size();
    }

    public ArrayList<String> getItemsRated() {
        ArrayList<String> list = new ArrayList<String>();
        for(String s : myRatings.keySet()){
            list.add(s);
        }
        return list;
    }
    
    public String toString() {
        String info = "ID: "+myID+" :: Ratings: "+myRatings;
        return info;
    }
    
    public int hashCode() {
        String currString = this.toString();
        int hashCode = currString.hashCode();
        return hashCode;
    }
}