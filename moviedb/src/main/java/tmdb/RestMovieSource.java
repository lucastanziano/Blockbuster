package tmdb;


import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.logging.HttpLoggingInterceptor;

import common.ConfigurationResponse;
import common.ImagesWrapper;
import common.MovieDetail;
import common.MoviesWrapper;
import common.ReviewsWrapper;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;
import rx.Observable;


public class RestMovieSource implements RestDataSource {

    private final MovieDatabaseAPI moviesDBApi;


    public RestMovieSource() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
// set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);


        OkHttpClient httpClient = new OkHttpClient();
// add your other interceptors …

// add logging as last interceptor
        httpClient.interceptors().add(logging);  // <-- this is the important line!

        Retrofit movieAPIRest = new Retrofit.Builder()
                .baseUrl(Constants.MOVIE_DB_HOST)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(httpClient)
                .build();

        moviesDBApi = movieAPIRest.create(MovieDatabaseAPI.class);

    }

    @Override
    public Observable<MoviesWrapper> getPopularMovies() {
        return moviesDBApi.getPopularMovies(Constants.API_KEY);
    }

    @Override
    public Observable<MovieDetail> getMovieDetails(String id) {
        return moviesDBApi.getMovieDetail( id, Constants.API_KEY);
    }

    @Override
    public Observable<ReviewsWrapper> getReviews(String id) {

        return moviesDBApi.getReviews(Constants.API_KEY, id);
    }

    @Override
    public Observable<ConfigurationResponse> getConfiguration() {

        return moviesDBApi.getConfiguration(Constants.API_KEY);
    }

    @Override
    public Observable<ImagesWrapper> getImages(String movieId) {

        return moviesDBApi.getImages(Constants.API_KEY, movieId);
    }


    @Override
    public Observable<MoviesWrapper> getMoviesByPage(int page) {

        return moviesDBApi.getPopularMoviesByPage(
                Constants.API_KEY,
                page + ""

        );
    }

    public Observable<MoviesWrapper> searchMovieByTitle(String title) {

        return moviesDBApi.searchMovieByTitle(
                Constants.API_KEY,
                title + ""

        );
    }
}
