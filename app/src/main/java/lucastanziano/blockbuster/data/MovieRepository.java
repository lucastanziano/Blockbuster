package lucastanziano.blockbuster.data;

import android.content.Context;

import javax.inject.Inject;

import lucastanziano.blockbuster.data.remote.TMDBService;
import lucastanziano.blockbuster.di.DaggerMovieDBComponent;
import lucastanziano.blockbuster.di.MovieDBModule;
import lucastanziano.blockbuster.model.ConfigurationResponse;
import lucastanziano.blockbuster.model.ImagesWrapper;
import lucastanziano.blockbuster.model.MovieDetail;
import lucastanziano.blockbuster.model.MoviesWrapper;
import lucastanziano.blockbuster.model.ReviewsWrapper;
import rx.Observable;


/**
 * Created by Luca on 31/12/2015.
 */
public class MovieRepository {

    @Inject
    protected TMDBService tmdbService;

    public MovieRepository(Context context){
        injectDependencies(context);
    }

    private void injectDependencies(Context context) {
        DaggerMovieDBComponent.builder()
                .movieDBModule(new MovieDBModule())
                .build()
                .inject(this);
    }


    public Observable<MoviesWrapper> getPopularMovies() {
        return tmdbService.getPopularMovies();
    }


    public Observable<MovieDetail> getMovieDetails(String id) {
        return tmdbService.getMovieDetail( id );
    }


    public Observable<ReviewsWrapper> getReviews(String id) {

        return tmdbService.getReviews( id);
    }


    public Observable<ConfigurationResponse> getConfiguration() {

        return tmdbService.getConfiguration();
    }


    public Observable<ImagesWrapper> getImages(String movieId) {

        return tmdbService.getImages( movieId);
    }


    public Observable<MoviesWrapper> getMoviesByPage(int page) {

        return tmdbService.getPopularMoviesByPage(
                page + ""
        );
    }

    public Observable<MoviesWrapper> searchMovieByTitle(String title) {

        return tmdbService.searchMovieByTitle(
                title + ""
        );
    }
}
