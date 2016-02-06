package com.lucastanziano.blockbuster.internal.di;

import com.lucastanziano.blockbuster.data.repository.MovieDataRepository;

/**
 * Created by Luca on 30/01/2016.
 */
public class Repository {

    private static MovieDataRepository movieDataRepository;
    public static MovieDataRepository getDefaultMovieRepository(){
        if(movieDataRepository==null){
            movieDataRepository = new MovieDataRepository();
        }
        return movieDataRepository;
    }
}
