package com.lucastanziano.blockbuster.data.net.net;



import com.lucastanziano.blockbuster.data.entity.MovieDetail;
import com.lucastanziano.blockbuster.data.entity.MoviesWrapper;
import com.lucastanziano.blockbuster.domain.ConfigurationResponse;
import com.lucastanziano.blockbuster.domain.ImagesWrapper;
import com.lucastanziano.blockbuster.domain.ReviewsWrapper;

import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;
import rx.Observable;


public interface TMDBService {

    public static final String API_KEY = "811f7e456317bda5a359db2152782167";
    public static final String MOVIE_DB_HOST = "http://api.themoviedb.org/3/";
    public static final String API_KEY_QUERY = "?api_key="+API_KEY;


    @GET("discover/movie"+ API_KEY_QUERY)
    Observable<MoviesWrapper> discoverMovie(
            @Query("certification") String certification,
            @Query("page") String page,
            @Query("primary_release_year") String primary_release_year,
            @Query("primary_release_date.gte") String primary_release_date_gte,
            @Query("primary_release_date.lte") String primary_release_date_lte,
            @Query("sort_by") String sort_by,
            @Query("vote_count.gte") String vote_count_gte,
            @Query("vote_average.gte") String vote_average_gte,
            @Query("vote_average.lte") String vote_average_lte,
            @Query("with_genres") String with_genres
    );
    
    @GET("movie/popular"+ API_KEY_QUERY)
    Observable<MoviesWrapper> getPopularMovies(@Query("page") String page);
    
    @GET("movie/latest"+ API_KEY_QUERY)
    Observable<MoviesWrapper> getLatestMovies(@Query("page") String page);
    
    @GET("movie/top_rated"+ API_KEY_QUERY)
    Observable<MoviesWrapper> getTopRatedMovies(@Query("page") String page);
    
    @GET("movie/upcoming"+ API_KEY_QUERY)
    Observable<MoviesWrapper> getUpcomingMovies(@Query("page") String page);

    @GET("movie/{id}"+ API_KEY_QUERY)
    Observable<MovieDetail> getMovieDetail(
            @Path("id") String id);

    @GET("movie/popular"+ API_KEY_QUERY)
    Observable<MoviesWrapper> getPopularMoviesByPage(
            @Query("page") String page
    );

    @GET("configuration"+ API_KEY_QUERY)
    Observable<ConfigurationResponse> getConfiguration(

    );

    @GET("movie/{id}/reviews"+ API_KEY_QUERY)
    Observable<ReviewsWrapper> getReviews(

            @Path("id") String id
    );

    @GET("movie/{id}/images"+ API_KEY_QUERY)
    Observable<ImagesWrapper> getImages(

            @Path("id") String movieId
    );

    @GET("search/movie"+ API_KEY_QUERY)
    Observable<MoviesWrapper> searchMovieByTitle(
            @Query("query") String query
    );




    @GET("movie/{id}")
    Observable<MovieDetail> getMovieDetail(
            @Path("id") String id,
            @Query("api_key") String apiKey
    );

    @GET("movie/popular")
    Observable<MoviesWrapper> getPopularMoviesByPage(
            @Query("api_key") String apiKey,
            @Query("page") String page
    );

    @GET("configuration")
    Observable<ConfigurationResponse> getConfiguration(
            @Query("api_key") String apiKey
    );

    @GET("movie/{id}/reviews")
    Observable<ReviewsWrapper> getReviews(
            @Query("api_key") String apiKey,
            @Path("id") String id
    );

    @GET("movie/{id}/images")
    Observable<ImagesWrapper> getImages(
            @Query("api_key") String apiKey,
            @Path("id") String movieId
    );

    @GET("search/movie")
    Observable<MoviesWrapper> searchMovieByTitle(
            @Query("api_key") String apiKey,
            @Query("query") String query
    );
}
