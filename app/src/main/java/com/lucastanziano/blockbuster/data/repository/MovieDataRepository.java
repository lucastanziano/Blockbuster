package com.lucastanziano.blockbuster.data.repository;


import com.lucastanziano.blockbuster.data.entity.MovieDetail;
import com.lucastanziano.blockbuster.data.entity.MovieMapper;
import com.lucastanziano.blockbuster.data.entity.MoviesWrapper;
import com.lucastanziano.blockbuster.data.net.ServiceFactory;
import com.lucastanziano.blockbuster.data.net.TMDBService;
import com.lucastanziano.blockbuster.tmdb.ConfigurationResponse;
import com.lucastanziano.blockbuster.tmdb.ImagesWrapper;
import com.lucastanziano.blockbuster.tmdb.Movie;
import com.lucastanziano.blockbuster.tmdb.ReviewsWrapper;
import com.lucastanziano.blockbuster.tmdb.repository.MovieRepository;

import java.util.List;

import javax.inject.Singleton;

import rx.Observable;

/**
 * Created by Luca on 24/01/2016.
 */
@Singleton
public class MovieDataRepository implements MovieRepository {

    private TMDBService restService;

    public MovieDataRepository() {
        this.restService = ServiceFactory.newTMDBService();
    }

    @Override
    public Observable<Movie> getMovies() {
        return restService.getPopularMovies("1").concatMap(new MovieMapper());
    }


    @Override
    public Observable<List<Movie>> getMovie(String id) {
        return null;
    }


    public Observable<Movie> getPopularMovies(int page) {
        return restService.getPopularMovies(Integer.toString(page)).concatMap(new MovieMapper());
    }

    public Observable<Movie> getTopRatedMovies(int page) {
        return restService.getTopRatedMovies(Integer.toString(page)).concatMap(new MovieMapper());
    }

    public Observable<Movie> getComingSoonMovies(int page) {
        return restService.getUpcomingMovies(Integer.toString(page)).concatMap(new MovieMapper());
    }

    public Observable<MovieDetail> getMovieDetails(String id) {
        return restService.getMovieDetail(id);
    }


    public Observable<ReviewsWrapper> getReviews(String id) {

        return restService.getReviews(id);
    }


    public Observable<ConfigurationResponse> getConfiguration() {

        return restService.getConfiguration();
    }


    public Observable<ImagesWrapper> getImages(String movieId) {

        return restService.getImages(movieId);
    }


    public Observable<MoviesWrapper> getMoviesByPage(int page) {

        return restService.getPopularMoviesByPage(
                page + ""
        );
    }

    public Observable<MoviesWrapper> searchMovieByTitle(String title) {

        return restService.searchMovieByTitle(
                title + ""
        );
    }
}

