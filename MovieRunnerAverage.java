
/**
 * Write a description of MovieRunnerAverage here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.util.*;

public class MovieRunnerAverage {

    public void printAverageRatings () {
        SecondRatings sr = new SecondRatings();
        int movieSize = sr.getMovieSize();
        int raterSize = sr.getRaterSize();
        System.out.println("Movies: "+movieSize+"\tRaters: "+raterSize);
        
        int minRatings = 12;
        ArrayList<Rating> avgRatings = sr.getAverageRatings(minRatings);
        Collections.sort(avgRatings);
        for (Rating r : avgRatings) {
            String currID = r.getItem();
            String title = sr.getTitle(currID);
            double rating = r.getValue();
            if (rating > 0.0) System.out.println(title+" - "+rating);
        }
        System.out.println("Done ============\n");
    }
    
    public void getAverageRatingOneMovie () {
        SecondRatings sr = new SecondRatings();
        String movie = "Vacation";
        String id = sr.getID(movie);
        ArrayList<Rating> ratings = sr.getAverageRatings(0);
        for (Rating r : ratings) {
            double currRating = r.getValue();
            String title = sr.getTitle(r.getItem());
            if (title.equals(movie)) {
                System.out.println(movie+" - "+currRating);
            }
        }
        System.out.println("Done ========\n ");
    }
    
}
