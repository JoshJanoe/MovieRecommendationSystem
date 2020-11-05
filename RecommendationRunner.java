
/**
 * Write a description of RecommendationRunner here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.util.ArrayList;

public class RecommendationRunner implements Recommender {
    
    public ArrayList<String> getItemsToRate() {
        ArrayList<String> movies = new ArrayList<String>();
        //movies compiled using MovieRunnerWithFilters printAverageRatingsByYear()
        //using the criteria of 40 minimum ratings/raters and made since the year 2000
        //some eliminated by personal familiarity to reduce number
        movies.add("1535108");//Elysium
        movies.add("0816711");//World War Z
        movies.add("0770828");//Man of Steel
        movies.add("1343092");//The Great Gatsby
        movies.add("1670345");//Now You See Me
        movies.add("1300854");//Iron Man 3
        //movies.add("0790636");//Dallas Buyers Club
        movies.add("1045658");//Silver Linings Playbook
        movies.add("1408101");//Star Trek Into Darkness
        //movies.add("1392214");//Prisoners
        movies.add("2278388");//The Grand Budapest Hotel
        movies.add("1535109");//Captain Phillips
        //movies.add("0993846");//The Wolf of Wall Street
        movies.add("1454468");//Gravity
        //movies.add("2084970");//The Imitation Game
        movies.add("2267998");//Gone Girl
        movies.add("2024544");//12 Years a Slave
        movies.add("1853728");//Django Unchained
        movies.add("0816692");//Interstellar
        return movies;
    }
     
    public void printRecommendationsFor (String webRaterID) {
        FourthRatings fr = new FourthRatings();        
        String id = webRaterID;
        int numSimilarRaters = 30;
        int minimalRaters = 3;        
        ArrayList<Rating> similar = fr.getSimilarRatings(id, numSimilarRaters, minimalRaters);
        String head = "<head><style>table{border:3px solid black; text-align:center; width:100%;}"
                        +"th{border-bottom: 1px solid black; text-align:center; width:25%;}"
                        +"td{text-align: center; width: 25%;}"
                        +"table img{height:144px; width: 96px;}</style></head>";
        String setupHTML = "<html>"+head+"<body><table><tr><th>Title and Score</th><th>Poster</th><th>Genre, Runtime, Director</th></tr>";
        System.out.print(setupHTML);
        
        for (Rating r : similar) {
            String currID = r.getItem();
            String title = MovieDatabase.getTitle(currID);
            double score = r.getValue();
            String poster = MovieDatabase.getPoster(currID);
            String genre = MovieDatabase.getGenres(currID);
            int runtime = MovieDatabase.getMinutes(currID);
            String directors = MovieDatabase.getDirector(currID);
            String entryHTML = "<tr><td rowspan=2><h3>"+title+"</h3></td><td rowspan=3><img src=\""+poster+"\"></td><td>"+genre+"</td></tr>"
                                +"<tr><td>"+runtime+"</td></tr><tr><td>"+score+"</td><td>"+directors+"</td></tr><tr><th colspan=3></th><tr>";
            System.out.print(entryHTML);
        }
        System.out.print("</table></body></html>");
    }
    
    public void testPrintRecommendationsFor() {
        String id = "120";
        printRecommendationsFor(id);
    }
    
}
