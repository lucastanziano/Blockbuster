package com.lucastanziano.blockbuster.gallery;

import com.lucastanziano.blockbuster.data.repository.MovieDataRepository;
import com.lucastanziano.blockbuster.tmdb.Movie;
import com.lucastanziano.blockbuster.internal.di.Repository;

import rx.Observable;

/**
 * Created by Luca on 06/02/2016.
 */
public class MovieProviderFactory {

    private final static MovieDataRepository movieDataRepository = Repository.getDefaultMovieRepository();

    public static IMovieProvider getTrendingMoviesProvider(){
        return new IMovieProvider() {
            int page = 1;

            @Override
            public Observable<Movie> getMoreMovies() {
                return getMovies(page++);
            }

            @Override
            public Observable<Movie> getMovies(int page) {
                return movieDataRepository.getPopularMovies(page);
            }
        };
    }

    public static IMovieProvider getTopRatedMoviesProvider(){
        return new IMovieProvider() {
            int page = 1;

            @Override
            public Observable<Movie> getMoreMovies() {
                return getMovies(page++);
            }

            @Override
            public Observable<Movie> getMovies(int page) {
                return movieDataRepository.getTopRatedMovies(page);
            }
        };
    }

    public static IMovieProvider getUpcomingMoviesProvider(){
        return new IMovieProvider() {
            int page = 1;

            @Override
            public Observable<Movie> getMoreMovies() {
                return getMovies(page++);
            }

            @Override
            public Observable<Movie> getMovies(int page) {
                return movieDataRepository.getComingSoonMovies(page);
            }
        };
    }
}
