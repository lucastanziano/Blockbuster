package com.lucastanziano.blockbuster.view.main.adapter;

import lucastanziano.blockbuster.R;
import lucastanziano.blockbuster.interactor.MoviesInteractor;

/**
 * Created by Luca on 23/01/2016.
 */
public enum TabsPagerEnum {

    POPULAR(R.string.popular, R.layout.fragment_gallery),
    TOP_RATED(R.string.top_rated, R.layout.fragment_gallery),
    COMING_SOON(R.string.coming_soon, R.layout.fragment_gallery);

    private int mTitleResId;
    private int mLayoutResId;

    TabsPagerEnum(int titleResId, int layoutResId) {
        mTitleResId = titleResId;
        mLayoutResId = layoutResId;
    }

    public int getTitleResId() {
        return mTitleResId;
    }

    public int getLayoutResId() {
        return mLayoutResId;
    }

    public void loadMovies(MoviesInteractor moviesInteractor) {
        switch (this){
            case POPULAR: moviesInteractor.loadPopularMovies(1);
                break;
            case TOP_RATED: moviesInteractor.loadTopRatedMovies(1);
                break;
            case COMING_SOON: moviesInteractor.loadComingSoonMovies(1);
                break;
        }
    }
}
