
/**
 * Write a description of MovieRunnerSimilarRatings here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.util.ArrayList;
import java.util.Collections;

public class MovieRunnerSimilarRatings {
    
    public void printAverageRatings () {
        RaterDatabase.initialize("ratings_short.csv");
        MovieDatabase.initialize("ratedmovies_short.csv");
        
        int minRatings = 1; 
        FourthRatings fr = new FourthRatings();
        ArrayList<Rating> avgRatings = fr.getAverageRatings(minRatings);
        Collections.sort(avgRatings);
        System.out.println(avgRatings.size()+" movies met the minimum criteria");
        
        for (Rating r : avgRatings) {
            String currID = r.getItem();
            String title = MovieDatabase.getTitle(currID);
            double rating = r.getValue();
            if (rating > 0.0) System.out.println("\t"+rating+" \t "+title);
        }
        System.out.println("Done ============\n");
    }
    
    public void printAverageRatingsByYearAndGenre () {
        RaterDatabase.initialize("ratings_short.csv");
        MovieDatabase.initialize("ratedmovies_short.csv");
        
        int minRatings = 1;
        int year = 1980;
        String genre = "Romance";
        Filter yearFilter = new YearAfterFilter(year);
        Filter genreFilter = new GenreFilter(genre);
        AllFilters multiFilter = new AllFilters();
        multiFilter.addFilter(yearFilter);
        multiFilter.addFilter(genreFilter);
        FourthRatings fr = new FourthRatings();
        ArrayList<Rating> avgRatings = fr.getAverageRatingsByFilter(minRatings,multiFilter);
        Collections.sort(avgRatings);
        System.out.println(avgRatings.size()+" movies met the minimum criteria");
        
        for (Rating r : avgRatings) {
            String currID = r.getItem();
            String title = MovieDatabase.getTitle(currID);
            double rating = r.getValue();
            String genre2 = MovieDatabase.getGenres(currID);
            int year2 = MovieDatabase.getYear(currID);
            if (rating > 0.0) System.out.println("\t"+rating+"\t"+title+"\t"+year2+"\t"+genre2);
        }
        System.out.println("Done ============\n");
    }
    
    public void printSimilarRatings() {
        //RaterDatabase.initialize("ratings_short.csv");
        //MovieDatabase.initialize("ratedmovies_short.csv");
        RaterDatabase.initialize("ratings.csv");
        MovieDatabase.initialize("ratedmoviesfull.csv");
        FourthRatings fr = new FourthRatings();
        
        String id = "71";
        int numSimilarRaters = 20;
        int minimalRaters = 5;
        
        ArrayList<Rating> similar = fr.getSimilarRatings( id, numSimilarRaters, minimalRaters);
        for (Rating r : similar) {
            String currID = r.getItem();
            String title = MovieDatabase.getTitle(currID);
            double score = r.getValue();
            System.out.println(title+" - "+score);
        }
        System.out.println("Done ===============");
    }
    
    public void printSimilarRatingsByGenre() {
        //RaterDatabase.initialize("ratings_short.csv");
        //MovieDatabase.initialize("ratedmovies_short.csv");
        RaterDatabase.initialize("ratings.csv");
        MovieDatabase.initialize("ratedmoviesfull.csv");
        FourthRatings fr = new FourthRatings();
        
        String id = "964";
        int numSimilarRaters = 20;
        int minimalRaters = 5;
        String genre = "Mystery";
        Filter genreFilter = new GenreFilter(genre);
        
        ArrayList<Rating> similar = fr.getSimilarRatingsWithFilter( id, numSimilarRaters, minimalRaters, genreFilter);
        for (Rating r : similar) {
            String currID = r.getItem();
            String title = MovieDatabase.getTitle(currID);
            double score = r.getValue();
            System.out.println(title+" - "+score);
        }
        System.out.println("Done ===============");
    }

    public void printSimilarRatingsByDirector() {
        //RaterDatabase.initialize("ratings_short.csv");
        //MovieDatabase.initialize("ratedmovies_short.csv");
        RaterDatabase.initialize("ratings.csv");
        MovieDatabase.initialize("ratedmoviesfull.csv");
        FourthRatings fr = new FourthRatings();
        
        String id = "120";
        int numSimilarRaters = 10;
        int minimalRaters = 2;
        String director = "Clint Eastwood,J.J. Abrams,Alfred Hitchcock,Sydney Pollack,David Cronenberg,Oliver Stone,Mike Leigh";
        Filter directorFilter = new DirectorsFilter(director);
        
        ArrayList<Rating> similar = fr.getSimilarRatingsWithFilter( id, numSimilarRaters, minimalRaters, directorFilter);
        for (Rating r : similar) {
            String currID = r.getItem();
            String title = MovieDatabase.getTitle(currID);
            double score = r.getValue();
            System.out.println(title+" - "+score);
        }
        System.out.println("Done ===============");
    }
    
    public void printSimilarRatingsByGenreAndMinutes () {
        RaterDatabase.initialize("ratings.csv");
        MovieDatabase.initialize("ratedmoviesfull.csv");
        
        //int minRatings = 3;
        int minutesMin = 80;
        int minutesMax = 160;
        String genre = "Drama";
        Filter minutesFilter = new MinutesFilter(minutesMin,minutesMax);
        Filter genreFilter = new GenreFilter(genre);
        AllFilters multiFilter = new AllFilters();
        multiFilter.addFilter(minutesFilter);
        multiFilter.addFilter(genreFilter);
        FourthRatings fr = new FourthRatings();
               
        String id = "168";
        int numSimilarRaters = 10;
        int minimalRaters = 3;
                
        ArrayList<Rating> similar = fr.getSimilarRatingsWithFilter( id, numSimilarRaters, minimalRaters, multiFilter);
        for (Rating r : similar) {
            String currID = r.getItem();
            String title = MovieDatabase.getTitle(currID);
            double score = r.getValue();
            System.out.println(title+" - "+score);
        }
        System.out.println("Done ===============");
    }
    
    public void printSimilarRatingsByYearAndMinutes () {
        RaterDatabase.initialize("ratings.csv");
        MovieDatabase.initialize("ratedmoviesfull.csv");
        
        //int minRatings = 1;
        int minutesMin = 70;
        int minutesMax = 200;
        int year = 1975;
        Filter minutesFilter = new MinutesFilter(minutesMin,minutesMax);
        Filter yearFilter = new YearAfterFilter(year);
        AllFilters multiFilter = new AllFilters();
        multiFilter.addFilter(minutesFilter);
        multiFilter.addFilter(yearFilter);
        FourthRatings fr = new FourthRatings();
               
        String id = "314";
        int numSimilarRaters = 10;
        int minimalRaters = 5;
                
        ArrayList<Rating> similar = fr.getSimilarRatingsWithFilter( id, numSimilarRaters, minimalRaters, multiFilter);
        for (Rating r : similar) {
            String currID = r.getItem();
            String title = MovieDatabase.getTitle(currID);
            double score = r.getValue();
            System.out.println(title+" - "+score);
        }
        System.out.println("Done ===============");
    }
}
