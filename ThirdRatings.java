/**
 * Write a description of ThirdRatings here.
 * 
 * @author      Josh Janoe 
 * @version     10/06/2020
 */

import java.util.*;

public class ThirdRatings {
    
    //private ArrayList<Movie> myMovies;
    private ArrayList<Rater> myRaters;
    private String path;
    
    public ThirdRatings() {
        // default constructor
        //this("ratings_short.csv");
        this("ratings.csv");
        path = "F:\\Java Specialization\\Course 5 - Build a Recommendation System\\Step Two\\";
    }
    
    public ThirdRatings(String ratingsfile) {
        FirstRatings fr = new FirstRatings();
        //myMovies = fr.loadMovies(moviefile);
        myRaters = fr.loadRaters(ratingsfile);
    }
    
    public int getRaterSize() {
        return myRaters.size();
    }
    
    private double getAverageByID (String id, int minimalRaters) { 
        //id is Movie ID
        double totalRatings = 0;
        double totalStars = 0;
        for (Rater r : myRaters) {
            ArrayList<String> moviesRated = r.getItemsRated();
            for (String s : moviesRated) {
                double rating = r.getRating(s);
                //System.out.println(s+" - "+id+" - "+rating);
                if (s.equals(id)){
                    totalStars += rating;
                    totalRatings++;
                }
            }
        }
        if (totalRatings < minimalRaters) return 0.0;
        double average = totalStars/totalRatings;
        return average;
    }
    
    public void testGetAverageByID () {
        //The Godfather (0068646)
        //Movie m = myMovies.get(2);
        MovieDatabase md = new MovieDatabase();
        //String currID = m.getID();
        //String title = m.getTitle();
        String currID = "0068646";
        String title = md.getTitle(currID);
        double avgRating = getAverageByID(currID,0);
        System.out.println(avgRating+" - "+title+" ("+currID+")");
        System.out.println("Done ========== \n");
    }
    
    public ArrayList<Rating> getAverageRatings (int minimalRaters) {
        ArrayList<Rating> avgRatings = new ArrayList<Rating>();
        ArrayList<String> movies = MovieDatabase.filterBy(new TrueFilter());
        //for (Movie m : myMovies) {
        for (String s : movies) {
            //String currID = movies.getID(s);
            //double rating = getAverageByID(currID,minimalRaters);
            //Rating currRating = new Rating(currID,rating);
            double rating = getAverageByID(s,minimalRaters);
            Rating currRating = new Rating(s,rating);
            System.out.println(rating+" - "+s);
            avgRatings.add(currRating);
        }
        return avgRatings;
    }
    
    public void testGetAverageRatings () {
        //The Godfather (0068646)
        int minRaters = 1;
        ArrayList<Rating> avgRatings = getAverageRatings(minRaters);
        for (Rating r : avgRatings) {
            //System.out.println(r);
        }
        System.out.println("Done ========== \n");
    }
    
    private HashMap <String,ArrayList<String>> buildRatingMap () { 
        HashMap <String,ArrayList<String>> allRatings = new HashMap<String,ArrayList<String>>();
        ArrayList<String> movies = MovieDatabase.filterBy(new TrueFilter());
        for (Rater r : myRaters) {
            ArrayList<String> moviesRated = r.getItemsRated();
            for (String s : moviesRated) {
                String rating = Double.toString(r.getRating(s));
                if (!allRatings.containsKey(s)) {
                    allRatings.put(s,new ArrayList<String>());
                }
                ArrayList<String> currList = allRatings.get(s);
                currList.add(rating);
                allRatings.put(s,currList);
            }
        }
        return allRatings;
    }
    
    public void testBuildRatingMap () {
        MovieDatabase mdb = new MovieDatabase();
        HashMap <String,ArrayList<String>> map = buildRatingMap();
        
        for (String s : map.keySet()) {
            String title = mdb.getTitle(s);
            System.out.println(title+" - "+map.get(s));
        }
    }
    
    public ArrayList<Rating> getAverageRatingsWithMap (int minimalRaters) {
        ArrayList<Rating> avgRatings = new ArrayList<Rating>();
        HashMap <String,ArrayList<String>> map = buildRatingMap();
        for (String s : map.keySet()) {
            ArrayList<String> ratings = map.get(s);
            double totalRatings = ratings.size();
            double totalStars = 0;
            for (String s2 : ratings) {
                double currRating = Double.parseDouble(s2);
                totalStars+=currRating;
            }
            double average = totalStars/totalRatings;
            if (totalRatings >= minimalRaters) avgRatings.add(new Rating(s,average));
        }
        return avgRatings;
    }
    
    public void testGetAverageRatingsWithMap () {
        MovieDatabase mdb = new MovieDatabase();
        int minRaters = 5;
        ArrayList<Rating> avgRatings = getAverageRatingsWithMap(minRaters);
        for (Rating r : avgRatings) {
            String id = r.getItem();
            String title = mdb.getTitle(id);
            double rating = r.getValue();
            //System.out.println("Total Movies: "+mdb.size());
            System.out.println(title+" - "+rating);
        }
        System.out.println("Done ========== \n");
    }
    
    public ArrayList<Rating> getAverageRatingsByFilter(int minimalRaters, Filter filterCriteria) {
        ArrayList<String> movies = MovieDatabase.filterBy(filterCriteria);
        ArrayList<Rating> ratings = getAverageRatingsWithMap(minimalRaters);
        ArrayList<Rating> filtered = new ArrayList<Rating>();
        for (Rating r : ratings) {
            if ( movies.contains(r.getItem()) ) {
                filtered.add(r);   
            }
        }
        return filtered;
    }
}
