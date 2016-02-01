package com.lucastanziano.blockbuster.view.frame.tabs;

import android.content.res.Resources;
import android.view.ViewGroup;

import com.lucastanziano.blockbuster.repository.MovieDataRepository;
import com.lucastanziano.blockbuster.view.frame.IFrame;
import com.lucastanziano.blockbuster.view.frame.MovieGalleryFrame;
import com.lucastanziano.blockbuster.view.frame.adapter.IMovieProvider;

/**
 * Created by Luca on 30/01/2016.
 */
public abstract  class BaseMovieTab implements  IMovieTab{

    private MovieDataRepository repo;

    private int currentPage = 1;

    private MovieGalleryFrame movieGalleryFrame;
    private IMovieProvider movieProvider;

    public BaseMovieTab(MovieDataRepository repo){
        this.repo = repo;
    }


    @Override
    public String getTitle() {
        return Resources.getSystem().getString(getTitleResId());
    }


    @Override
    public abstract int getTitleResId();

     public abstract IMovieProvider getMovieProvider();

    @Override
    public IFrame getFrame() {
        return movieGalleryFrame;
    }

    @Override
    public void instantiateFrame(ViewGroup parent){
        movieGalleryFrame = new MovieGalleryFrame(parent, getMovieProvider());

    }

    public void setCurrentPage(int page){
        this.currentPage = page;
    }

    protected MovieDataRepository getRepository(){return repo;}

}
