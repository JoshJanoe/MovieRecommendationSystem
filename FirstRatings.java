 /**
 * This program sorts through custom objects (Rater and Rating)
 * to extract information about movies (runtime, director, genre, etc)
 * and raters (id#, rated movie ID, rating)
 *
 * @author      Josh Janoe
 * @version     10/01/2020
 */

import edu.duke.*;
import java.util.*;
import org.apache.commons.csv.*;

public class FirstRatings {
    
    private String path = "F:\\Java Specialization\\Course 5 - Build a Recommendation System\\";
    
    public ArrayList<Movie> loadMovies (String filename) {
        FileResource fr = new FileResource(filename);
        CSVParser parser = fr.getCSVParser();
        ArrayList<Movie> movies = new ArrayList<Movie>();
        for (CSVRecord record : parser) {
            String anID = record.get("id");
            String aTitle = record.get("title");
            String aYear = record.get("year");
            String theGenres = record.get("genre");
            String aDirector = record.get("director");
            String aCountry = record.get("country");
            String aPoster = record.get("poster");
            int theMinutes = Integer.parseInt(record.get("minutes"));
            Movie currMovie = new Movie(anID, aTitle, aYear, theGenres, aDirector, aCountry, aPoster, theMinutes);
            movies.add(currMovie);
        }
        return movies;
    }
    
    public void testLoadMovies () {
        String filename = "ratedmovies_short.csv";
        filename = "ratedmoviesfull.csv";
        ArrayList<Movie> movies = loadMovies(path+filename);
        int comedies = 0;
        int longMovies = 0;
        HashMap<String,Integer> directors = new HashMap<String,Integer>();
        int maxCredits = 0;
        for (int i=0; i<movies.size();i++) {
            //System.out.println(movies.get(i));
            if (movies.get(i).getGenres().contains("Comedy")) comedies++;
            if (movies.get(i).getMinutes()>150) longMovies++;
            String currDirectors = movies.get(i).getDirector();
            String[] director = currDirectors.split(", ");
            for (int k=0;k<director.length;k++) {
                if (!directors.containsKey(director[k])) {
                    directors.put(director[k],0);
                }
                directors.put(director[k],directors.get(director[k])+1);
            }
        }
        System.out.println("Comedies: "+comedies);
        System.out.println("Long Movies: "+longMovies);
        for (String s1 : directors.keySet()) {
            if (directors.get(s1) > maxCredits) maxCredits = directors.get(s1);
        }   
        for (String s2 : directors.keySet()) {
            if (directors.get(s2)==maxCredits) System.out.println(s2+" directed "+directors.get(s2)+" movies");
        }
        System.out.println("Done ======= ");
    }
    
    public ArrayList<Rater> loadRaters (String filename) {
        FileResource fr = new FileResource(filename);
        CSVParser parser = fr.getCSVParser();
        ArrayList<Rater> raterList = new ArrayList<Rater>();
        HashMap<String,ArrayList<Rating>> raterMap = new HashMap<String,ArrayList<Rating>>();
        //ArrayList<Rating> ratings = new ArrayList<Rating>();
        for (CSVRecord record : parser) {
            ArrayList<Rating> ratings = new ArrayList<Rating>();
            String raterID = record.get("rater_id");
            String movieID = record.get("movie_id");
            double rating = Double.parseDouble(record.get("rating"));
            Rating currRating = new Rating(movieID,rating);
            if (raterMap.containsKey(raterID)) {
                ratings = raterMap.get(raterID);
            }
            ratings.add(currRating);
            raterMap.put(raterID,ratings);
        }   
        for (String s : raterMap.keySet()) {
            Rater currRater = new Rater(s);
            for (Rating r : raterMap.get(s)) {
                String item = r.getItem();
                double dbl = r.getValue();
                currRater.addRating(item,dbl);
            }
            raterList.add(currRater);
        }
        return raterList;
    }
    
    public void testLoadRaters() {
        String filename = "ratings_short.csv";
        filename = "ratings.csv";
        ArrayList<Rater> raters = loadRaters(path+filename);
        System.out.println("Total Raters: "+raters.size());
        
        String raterID = "193";
        String movieID = "1798709";
        
        int maxRatings = 0;
        HashMap<String,Integer> timesRated = new HashMap<String,Integer>();
        for (Rater r : raters) {
            String ID = r.getID();
            int numRatings = r.numRatings();
            ArrayList<String> ratings = r.getItemsRated();
            //System.out.println("User "+ID+" has "+numRatings+" ratings"+"\n"+ratings);
            if (ID==raterID) System.out.println("User "+ID+" has "+numRatings+" ratings"+"\n"+ratings);
            if (numRatings>maxRatings) maxRatings=numRatings;
            for (String s : ratings) {
                if (timesRated.containsKey(s)) {
                    int count = timesRated.get(s);
                    timesRated.put(s,count+1);
                }
                if (!timesRated.containsKey(s)) {
                    timesRated.put(s,1);
                }
            }
        }
        for (Rater r : raters) {
            int numRatings = r.numRatings();
            if (numRatings==maxRatings) System.out.println("User "+r.getID()+" has the highest number of ratings"+"("+maxRatings+")");
            if (r.getID().equals(raterID)) System.out.println("User "+r.getID()+" rated "+numRatings+" unique movies");
        }
        for (String s : timesRated.keySet()){
            if (s.equals(movieID)) System.out.println(movieID+" was rated by "+timesRated.get(s)+" users");
            
        }
        System.out.println("There were a total of "+timesRated.size()+" unique movies rated");
        System.out.println("Done ======= ");
    }
    
}