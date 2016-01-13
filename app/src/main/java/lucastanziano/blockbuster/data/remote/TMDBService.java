package lucastanziano.blockbuster.data.remote;


import lucastanziano.blockbuster.model.ConfigurationResponse;
import lucastanziano.blockbuster.model.ImagesWrapper;
import lucastanziano.blockbuster.model.MovieDetail;
import lucastanziano.blockbuster.model.MoviesWrapper;
import lucastanziano.blockbuster.model.ReviewsWrapper;

import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;
import rx.Observable;


public interface TMDBService {

    public static final String API_KEY = "811f7e456317bda5a359db2152782167";
    public static final String MOVIE_DB_HOST = "http://api.themoviedb.org/3/";
    public static final String API_KEY_QUERY = "?api_key="+API_KEY;


    @GET("movie/popular"+ API_KEY_QUERY)
    Observable<MoviesWrapper> getPopularMovies();

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


    @GET("movie/popular")
    Observable<MoviesWrapper> getPopularMovies(
            @Query("api_key") String apiKey);

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