package com.lucastanziano.blockbuster.view.frame.tabs;

import android.view.ViewGroup;

import com.lucastanziano.blockbuster.Movie;
import com.lucastanziano.blockbuster.view.frame.IFrame;

import rx.Observable;

/**
 * Created by Luca on 25/01/2016.
 */
public interface IMovieTab {

    String getTitle();

    int getTitleResId();

    IFrame getFrame();

    void instantiateFrame(ViewGroup collection);
}
