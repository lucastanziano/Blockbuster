package com.lucastanziano.blockbuster.repository;

import com.lucastanziano.blockbuster.ConfigurationResponse;
import com.lucastanziano.blockbuster.ImagesWrapper;
import com.lucastanziano.blockbuster.Movie;
import com.lucastanziano.blockbuster.entity.MovieMapper;
import com.lucastanziano.blockbuster.entity.MoviesWrapper;
import com.lucastanziano.blockbuster.ReviewsWrapper;
import com.lucastanziano.blockbuster.entity.MovieDetail;
import com.lucastanziano.blockbuster.net.ServiceFactory;
import com.lucastanziano.blockbuster.net.TMDBService;

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

