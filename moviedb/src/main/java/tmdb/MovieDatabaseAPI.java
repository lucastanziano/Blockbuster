package tmdb;


import common.ConfigurationResponse;
import common.ImagesWrapper;
import common.MovieDetail;
import common.MoviesWrapper;
import common.ReviewsWrapper;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;
import rx.Observable;

/**
 * Interface representing the MovieDatabaseAPI endpoints
 * used by retrofit
 */
public interface MovieDatabaseAPI {

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
