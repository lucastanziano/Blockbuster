package lucastanziano.blockbuster;

import tmdb.RestMovieSource;

/**
 * Created by Luca on 06/12/2015.
 */
public enum Repository {

    INSTANCE;

    RestMovieSource repo = new RestMovieSource();

    public RestMovieSource getMovieRepository(){
        return repo;
    }
}
