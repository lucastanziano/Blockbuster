package common;

import rx.Observable;

public interface MediaDataSource {

    Observable<MoviesWrapper> getMovies();

    Observable<MovieDetail> getMovieDetails(String id);

    Observable<ReviewsWrapper> getReviews(String id);

    Observable<ConfigurationResponse> getConfiguration();

    Observable<ImagesWrapper> getImages(String movieId);
}
