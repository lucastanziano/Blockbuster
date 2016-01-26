package com.lucastanziano.blockbuster.navigation;

import android.content.Context;
import android.content.Intent;


import com.lucastanziano.blockbuster.view.movie.MovieDetailsActivity;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Class used to navigate through the application.
 */
@Singleton
public class Navigator {

  @Inject
  public void Navigator() {
    //empty
  }

  /**
   * Goes to the user list screen.
   *
   * @param context A Context needed to open the destiny activity.
   */
  public void navigateToMovieDetails(Context context, String movieID) {
    if (context != null) {
      Intent intentToLaunch = MovieDetailsActivity.getCallingIntent(context);
      intentToLaunch.putExtra(MovieDetailsActivity.MOVIE_ID_TAG, movieID);
      context.startActivity(intentToLaunch);
    }
  }


}
