
/**
 * Write a description of DirectorsFilter here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.util.ArrayList;

public class DirectorsFilter implements Filter {
    
    private String[] myDirectors;
    
    public DirectorsFilter(String directors) {
        myDirectors = directors.split(",");
    }
    
    @Override
    public boolean satisfies(String id) {
        String directors = MovieDatabase.getDirector(id);
        for (String s : myDirectors) {
            if (directors.contains(s)) {
                return true;
            }
        }
        return false;
    }

}