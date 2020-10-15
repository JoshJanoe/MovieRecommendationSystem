/**
 * Write a description of FourthRatings here.
 * 
 * @author      Josh Janoe 
 * @version     10/09/2020
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Collections;

public class FourthRatings {
   
    private double getAverageByID (String id, int minimalRaters) { 
        //id is Movie ID
        double totalRatings = 0;
        double totalStars = 0;
        for (Rater r : RaterDatabase.getRaters()) {
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
        //MovieDatabase md = new MovieDatabase();
        RaterDatabase.initialize("ratings_short.csv");
        MovieDatabase.initialize("ratedmovies_short.csv");
        //String currID = m.getID();
        //String title = m.getTitle();
        String currID = "0068646";
        String title = MovieDatabase.getTitle(currID);
        double avgRating = getAverageByID(currID,0);
        System.out.println(avgRating+" - "+title+" ("+currID+")");
        System.out.println("Done ========== \n");
    }
    
    public ArrayList<Rating> getAverageRatings (int minimalRaters) {
        ArrayList<Rating> avgRatings = new ArrayList<Rating>();
        ArrayList<String> movies = MovieDatabase.filterBy(new TrueFilter());
        for (String s : movies) {
            double rating = getAverageByID(s,minimalRaters);
            Rating currRating = new Rating(s,rating);
            //System.out.println(rating+" - "+s);
            if (rating > 0) avgRatings.add(currRating);
        }
        return avgRatings;
    }
    
    public ArrayList<Rating> getAverageRatingsByFilter(int minimalRaters, Filter filterCriteria) {
        ArrayList<String> movies = MovieDatabase.filterBy(filterCriteria);
        ArrayList<Rating> ratings = getAverageRatings(minimalRaters);
        ArrayList<Rating> filtered = new ArrayList<Rating>();
        for (Rating r : ratings) {
            if ( movies.contains(r.getItem()) ) {
                filtered.add(r);   
            }
        }
        return filtered;
    }
    
    private double dotProduct (Rater me, Rater r) {
        ArrayList<String> movies = MovieDatabase.filterBy(new TrueFilter());
        double product = 0.0;
        
        String myID = me.getID();
        String rID = r.getID();
        //System.out.println(myID);
        Rater meRater = RaterDatabase.getRater(myID);
        Rater rRater = RaterDatabase.getRater(rID);

        for (String s : movies) {
            if (meRater.hasRating(s) && rRater.hasRating(s)) {
                double myScaledScore = meRater.getRating(s) - 5.0;
                double rScaledScore = rRater.getRating(s) - 5.0;
                double temp = myScaledScore * rScaledScore;
                //System.out.println(s+": "+meRater.getRating(s)+" + "+rRater.getRating(s));
                //System.out.println("Scaled: "+myScaledScore+" + "+rScaledScore+" = "+temp);
                product += temp;
            }
        }
        return product;
    }
    
    public void testDotProduct () {
        RaterDatabase.initialize("ratings_short.csv");
        MovieDatabase.initialize("ratedmovies_short.csv");
        Rater raterA = RaterDatabase.getRater("3");
        Rater raterB = RaterDatabase.getRater("4");
        double product = dotProduct(raterA,raterB);
        System.out.println(raterA);
        System.out.println(raterB);
        System.out.println(product);
        System.out.println("------- testDotProduct Complete -------");
    }
       
    private ArrayList<Rating> getSimilarities(String id) {
        ArrayList<Rating> ratings = new ArrayList<Rating>();
        Rater me = RaterDatabase.getRater(id);
        for (Rater r : RaterDatabase.getRaters()) {
            double currScore = dotProduct(me,r);
            String currID = r.getID();
            Rating currRating = new Rating(currID,currScore);
            if (currScore>0 && !currID.equals(id)) ratings.add(currRating);
        }   
        Collections.sort(ratings);
        Collections.reverse(ratings);
        return ratings;
    }
    
    public void testGetSimilarities() {
        RaterDatabase.initialize("ratings_short.csv");
        MovieDatabase.initialize("ratedmovies_short.csv");
        
        String id = "1";
        
        ArrayList<Rating> similarities = getSimilarities(id);
        for (Rating r : similarities) {
            System.out.println(r);
        }
        System.out.println("------- testGetSimilarities Complete -------");
    }
    
    public ArrayList<Rating> getSimilarRatings(String id, int numSimilarRaters, int minimalRaters) {
        ArrayList<Rating> weightedRatings = new ArrayList<Rating>();
        ArrayList<Rating> similarities = getSimilarities(id);
        ArrayList<String> movies = MovieDatabase.filterBy(new TrueFilter());
        for (String movie : movies) {
            double totPoints = 0.0;
            double totRatings = 0.0;
            for (int i=0; i <= numSimilarRaters; i++) {
                Rating r = similarities.get(i);
                String compID = r.getItem();
                double multiplier = r.getValue();
                Rater currRater = RaterDatabase.getRater(compID);
                if (currRater.hasRating(movie)) {
                    double currRating = currRater.getRating(movie);
                    double adjRating = currRating*multiplier;
                    //System.out.println("currRating: "+currRating+" * multiplier: "+multiplier+" = adjRating: "+adjRating);
                    totRatings++;
                    totPoints += adjRating;
                }
            }
            if (totRatings >= minimalRaters) {
                double adjAverage = totPoints/totRatings;
                Rating recRating = new Rating(movie,adjAverage);
                weightedRatings.add(recRating);
            }
        }
        Collections.sort(weightedRatings);
        Collections.reverse(weightedRatings);
        return weightedRatings;
    }
    
    public void testGetSimilarRatings() {
        RaterDatabase.initialize("ratings_short.csv");
        MovieDatabase.initialize("ratedmovies_short.csv");
        
        String id = "1";
        int numSimilarRaters = 4;   //number of similar ratings starting with the top ranking/most similar
        int minimalRaters = 2;      //minimum number of ratings per rater/user
        
        ArrayList<Rating> similarRatings = getSimilarRatings(id, numSimilarRaters, minimalRaters);
        for (Rating r : similarRatings) {
            String title = MovieDatabase.getTitle(r.getItem());
            double score = r.getValue();
            System.out.println(title+" - "+score);
        }
        System.out.println("------- testGetSimilarRatings Complete -------");
    }
    
    public ArrayList<Rating> getSimilarRatingsWithFilter(String id, int numSimilarRaters, int minimalRaters, Filter f) {
        ArrayList<Rating> weightedRatings = new ArrayList<Rating>();
        ArrayList<Rating> similarities = getSimilarities(id);
        ArrayList<String> movies = MovieDatabase.filterBy(f);
        for (String movie : movies) {
            double totPoints = 0.0;
            double totRatings = 0.0;
            for (int i=0; i <= numSimilarRaters; i++) {
                Rating r = similarities.get(i);
                String compID = r.getItem();
                double multiplier = r.getValue();
                Rater currRater = RaterDatabase.getRater(compID);
                if (currRater.hasRating(movie)) {
                    double currRating = currRater.getRating(movie);
                    double adjRating = currRating*multiplier;
                    totRatings++;
                    totPoints += adjRating;
                }
            }
            if (totRatings >= minimalRaters) {
                double adjAverage = totPoints/totRatings;
                Rating recRating = new Rating(movie,adjAverage);
                weightedRatings.add(recRating);
            }
        }
        Collections.sort(weightedRatings);
        Collections.reverse(weightedRatings);
        return weightedRatings;
    }
}