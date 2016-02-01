package com.lucastanziano.blockbuster.view.frame;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableList;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lucastanziano.blockbuster.internal.di.Repository;
import com.lucastanziano.blockbuster.model.MovieItem;
import com.lucastanziano.blockbuster.navigation.Navigator;
import com.lucastanziano.blockbuster.presenter.MovieInteractor;
import com.lucastanziano.blockbuster.repository.MovieDataRepository;
import com.lucastanziano.blockbuster.view.frame.adapter.IMovieProvider;
import com.lucastanziano.blockbuster.view.frame.adapter.MovieGalleryAdapter;

import org.lucasr.twowayview.ItemClickSupport;


import lucastanziano.blockbuster.R;
import lucastanziano.blockbuster.databinding.FragmentGalleryBinding;
import rx.Subscriber;
import yalantis.com.sidemenu.interfaces.ScreenShotable;


public class MovieGalleryFrame implements IFrame, ScreenShotable {


    private IMovieProvider movieProvider;
    private FragmentGalleryBinding mBinding;
    private Context context;

    private Navigator navigator = new Navigator();
    private MovieDataRepository repo = Repository.getDefaultMovieRepository();

    private Bitmap bitmap;
    MovieGalleryAdapter mAdapter;

    public MovieGalleryFrame(ViewGroup container, IMovieProvider movieProvider) {
        this.movieProvider = movieProvider;
        attachTo(container);
    }

    @Override
    public void attachTo(ViewGroup container) {
        context = container.getContext();
        mAdapter = new MovieGalleryAdapter(context);
        mBinding = DataBindingUtil.inflate(LayoutInflater.from(container.getContext()),
                R.layout.fragment_gallery, container, true);
        initRecyclerView();
        MovieInteractor.loadMovieItems(getContext(), movieProvider.getMoreMovies(), getObservableItems());
    }

    private void initRecyclerView() {
        mBinding.galleryRecyclerView.setHasFixedSize(true);
        mBinding.galleryRecyclerView.setAdapter((RecyclerView.Adapter) mAdapter);
        mBinding.galleryRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int scrollState) {
                updateState(scrollState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int xScroll, int yScroll) {

                if (mBinding.galleryRecyclerView.getLastVisiblePosition() > mAdapter.getItemCount() - 6){
                    MovieInteractor.loadMovieItems(getContext(), movieProvider.getMoreMovies(), getObservableItems());
                }
            }
        });

        ItemClickSupport itemClick = ItemClickSupport.addTo(mBinding.galleryRecyclerView);

        itemClick.setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View child, int position, long id) {
                navigator.navigateToMovieDetails(getContext(), String.valueOf( mAdapter.getItems().get(position).id));
            }
        });

        itemClick.setOnItemLongClickListener(new ItemClickSupport.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(RecyclerView parent, View child, int position, long id) {

                return true;
            }
        });
    }




    public View getView() {
        return mBinding.getRoot();
    }


    public ObservableList<MovieItem> getObservableItems() {
        return mAdapter.getItems();
    }


    public Context getContext() {
        return context;
    }


    private void updateState(int scrollState) {
        String stateName = "Undefined";
        switch (scrollState) {
            case android.support.v7.widget.RecyclerView.SCROLL_STATE_IDLE:

                break;

            case android.support.v7.widget.RecyclerView.SCROLL_STATE_DRAGGING:

                break;

            case android.support.v7.widget.RecyclerView.SCROLL_STATE_SETTLING:

                break;
        }

    }

    @Override
    public void takeScreenShot() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                Bitmap bitmap = Bitmap.createBitmap(getView().getWidth(),
                        getView().getHeight(), Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bitmap);
                getView().draw(canvas);
                MovieGalleryFrame.this.bitmap = bitmap;
            }
        };

        thread.start();

    }

    @Override
    public Bitmap getBitmap() {
        return bitmap;
    }
}
