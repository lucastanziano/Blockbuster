package com.lucastanziano.blockbuster.widget.gallery.view;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableList;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.lucastanziano.blockbuster.widget.gallery.model.GalleryItem;
import com.lucastanziano.blockbuster.widget.gallery.view.adapter.IGalleryAdapterWrapper;

import org.lucasr.twowayview.ItemClickSupport;

import java.util.List;

import javax.inject.Inject;


import lucastanziano.blockbuster.R;
import lucastanziano.blockbuster.databinding.FragmentGalleryBinding;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class Gallery {


    private FragmentGalleryBinding mBinding;
    private Context context;

    @Inject
    IGalleryAdapterWrapper mAdapter;

    public Gallery(ViewGroup container) {
        context = container.getContext();
        injectDependencies();
        mBinding = DataBindingUtil.inflate(LayoutInflater.from(container.getContext()),
                R.layout.fragment_gallery, container, true);
        initRecyclerView();
    }

    private void initRecyclerView() {
        mBinding.galleryRecyclerView.setHasFixedSize(true);
//        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(context, 2);
//        mBinding.galleryRecyclerView.setLayoutManager(mLayoutManager);
        mBinding.galleryRecyclerView.setAdapter((RecyclerView.Adapter) mAdapter);


        mBinding.galleryRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int scrollState) {
                updateState(scrollState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int xScroll, int yScroll) {
                if(mBinding.galleryRecyclerView.getLastVisiblePosition()> x) addNewMovies();
//                mPositionText.setText("First: " + mBinding.galleryRecyclerView.getFirstVisiblePosition());
//                mCountText.setText("Count: " + mBinding.galleryRecyclerView.getChildCount());
            }
        });
    }

    private void injectDependencies() {
        GalleryComponent galleryComponent = DaggerGalleryComponent.builder()
                .galleryModule(new GalleryModule(context)).build();
        galleryComponent.inject(this);
        galleryComponent.inject(mAdapter);

    }

    public View getView() {
        return mBinding.getRoot();
    }




    public void registerSubscriberOnGalleryItemSelected(Subscriber<GalleryItem> subscriber){
        final ItemClickSupport itemClick = ItemClickSupport.addTo(mBinding.galleryRecyclerView);

        itemClick.setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View child, int position, long id) {

            }
        });

        itemClick.setOnItemLongClickListener(new ItemClickSupport.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(RecyclerView parent, View child, int position, long id) {

                return true;
            }
        });
    }



    public ObservableList<GalleryItem> getObservableItems() {
        return mAdapter.getItems();
    }


    public Context getContext() {
        return context;
    }


    private void updateState(int scrollState) {
        String stateName = "Undefined";
        switch(scrollState) {
            case android.support.v7.widget.RecyclerView.SCROLL_STATE_IDLE:

                break;

            case android.support.v7.widget.RecyclerView.SCROLL_STATE_DRAGGING:

                break;

            case android.support.v7.widget.RecyclerView.SCROLL_STATE_SETTLING:

                break;
        }

    }
}
