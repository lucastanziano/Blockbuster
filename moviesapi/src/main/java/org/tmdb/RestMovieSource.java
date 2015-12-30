package org.tmdb;


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

        Retrofit movieAPIRest = new Retrofit.Builder()
                .baseUrl(Constants.MOVIE_DB_HOST)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        moviesDBApi = movieAPIRest.create(MovieDatabaseAPI.class);

    }

    @Override
    public Observable<MoviesWrapper> getMovies() {
        return moviesDBApi.getPopularMovies(Constants.API_KEY);
    }

    @Override
    public Observable<MovieDetail> getMovieDetails(String id) {
        return moviesDBApi.getMovieDetail(Constants.API_KEY, id);
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

      return  moviesDBApi.getPopularMoviesByPage(
                Constants.API_KEY,
                page + ""

        );
    }
}
