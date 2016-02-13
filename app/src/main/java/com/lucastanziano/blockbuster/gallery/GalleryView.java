package com.lucastanziano.blockbuster.gallery;

import android.databinding.DataBindingUtil;
import android.databinding.ObservableList;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.FrameLayout;

import com.lucastanziano.blockbuster.data.repository.MovieDataRepository;
import com.lucastanziano.blockbuster.internal.di.Repository;
import com.lucastanziano.blockbuster.navigation.Navigator;

import org.lucasr.twowayview.ItemClickSupport;

import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;
import com.lucastanziano.blockbuster.R;
import com.lucastanziano.blockbuster.databinding.FragmentGalleryBinding;
import yalantis.com.sidemenu.interfaces.ScreenShotable;

/**
 * Created by Luca on 06/02/2016.
 */
public class GalleryView extends FrameLayout implements ScreenShotable {

    private final IMovieProvider movieProvider;
    private FragmentGalleryBinding mBinding;

    private Navigator navigator = new Navigator();
    private MovieDataRepository repo = Repository.getDefaultMovieRepository();

    private Bitmap bitmap;
    private MovieGalleryAdapter mAdapter;


    public GalleryView(ViewGroup parent, IMovieProvider movieProvider){
        super(parent.getContext());
        this.movieProvider = movieProvider;
        createView(parent);
    }
    private void createView(ViewGroup container) {
        mBinding = DataBindingUtil.inflate(LayoutInflater.from(getContext()),
                R.layout.fragment_gallery, container, true);
        mAdapter = new MovieGalleryAdapter(getContext(), mBinding.galleryRecyclerView);
        initRecyclerView();
        MovieInteractor.loadMovieItems(getContext(), movieProvider.getMoreMovies(), getObservableItems());
    }

    private void initRecyclerView() {
        mBinding.galleryRecyclerView.setHasFixedSize(true);

        mBinding.galleryRecyclerView.setItemAnimator(new SlideInUpAnimator(new OvershootInterpolator(1f)));

        AlphaInAnimationAdapter alphaAdapter = new AlphaInAnimationAdapter((RecyclerView.Adapter) mAdapter);
        alphaAdapter.setInterpolator(new OvershootInterpolator());


        mBinding.galleryRecyclerView.setAdapter(alphaAdapter);
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


    public void changeLayoutManager(RecyclerView.LayoutManager newLayoutManager){
        mAdapter.replaceLayoutManager(newLayoutManager);
    }


    public View getView() {
        return mBinding.getRoot();
    }


    public ObservableList<MovieItem> getObservableItems() {
        return mAdapter.getItems();
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
                GalleryView.this.bitmap = bitmap;
            }
        };

        thread.start();

    }

    @Override
    public Bitmap getBitmap() {
        return bitmap;
    }
}
