
/**
 * Write a description of SecondRatings here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.util.*;

public class SecondRatings {
    
    private ArrayList<Movie> myMovies;
    private ArrayList<Rater> myRaters;
    private String path;
    
    public SecondRatings() {
        // default constructor
        //this("ratedmoviesfull.csv", "ratings.csv");
        this("ratedmovies_short.csv", "ratings_short.csv");
        path = "F:\\Java Specialization\\Course 5 - Build a Recommendation System\\Step Two\\";
    }
    
    public SecondRatings(String moviefile, String ratingsfile) {
        FirstRatings fr = new FirstRatings();
        myMovies = fr.loadMovies(moviefile);
        myRaters = fr.loadRaters(ratingsfile);
    }
    
    public int getMovieSize() {
        return myMovies.size();
    }
    
    public int getRaterSize() {
        return myRaters.size();
    }
    
    private double getAverageByID (String id, int minimalRaters) {
        //---id is (or should be) MovieID---
        double totalRatings = 0;
        double totalStars = 0;
        //getAllRatings
        for (Rater r : myRaters) {
            ArrayList<String> moviesRated = r.getItemsRated();
            //System.out.println(ratings);
            for (String s : moviesRated) {
                double rating = r.getRating(s);
                //System.out.println("Rater: "+r.getID()+"\tMovie: "+s+"\tRating: "+rating+"\tID: "+id);
                if (s.equals(id)){
                    totalStars += rating;
                    totalRatings++;
                }
            }
        }
        if (totalRatings < minimalRaters) return 0.0;
        double average = totalStars/totalRatings;
        //System.out.println("Total Stars: "+totalStars+"\tTotal Ratings: "+totalRatings+"\tAverage Rating: "+average);
        return average;
    }
    
    public void testGetAverageByID () {
        //The Godfather (0068646)
        Movie m = myMovies.get(2);
        String currID = m.getID();
        String title = m.getTitle();
        double avgRating = getAverageByID(currID,0);
        System.out.println(avgRating+" - "+title+" ("+currID+")");
        System.out.println("Done ========== \n");
    }
    
    public ArrayList<Rating> getAverageRatings (int minimalRaters) {
        ArrayList<Rating> avgRatings = new ArrayList<Rating>();
        for (Movie m : myMovies) {
            String currID = m.getID();
            double rating = getAverageByID(currID,minimalRaters);
            Rating currRating = new Rating(currID,rating);
            avgRatings.add(currRating);
        }
        return avgRatings;
    }
    
    public void testGetAverageRatings () {
        //The Godfather (0068646)
        int minRaters = 1;
        ArrayList<Rating> avgRatings = getAverageRatings(minRaters);
        for (Rating r : avgRatings) {
            System.out.println(r);
        }
        System.out.println("Done ========== \n");
    }
    
    public String getTitle (String id) {
        for (Movie m : myMovies) {
            String currID = m.getID();
            if (currID.equals(id)) {
                return m.getTitle();
            }
        }
        return "Movie with ID "+id+" not found";
    }
    
    public void testGetTitle () {
        for (Movie m : myMovies) {
            System.out.println(m.getID()+" - "+getTitle(m.getID()));
        }
        System.out.println("Done ========== \n");
    }
    
    public String getID(String title) {
        for (Movie m : myMovies) {
            String currTitle = m.getTitle();
            if (currTitle.equals(title)) {
                return m.getID();
            }
        }
        return "No such title found\n";
    }
    
    public void testGetID () {
        for (Movie m : myMovies) {
            System.out.println(m.getTitle()+" - "+getID(m.getTitle()));
        }
        System.out.println("Done ========== \n");
    }
}
