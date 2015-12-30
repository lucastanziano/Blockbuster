package lucastanziano.gallery.view;

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

import java.util.List;

import javax.inject.Inject;

import lucastanziano.gallery.model.GalleryItem;
import lucastanziano.gallery.view.adapter.IGalleryAdapterWrapper;
import lucastanziano.gallery.view.adapter.MovieGalleryAdapter;
import lucastanziano.gallery.R;
import lucastanziano.gallery.databinding.FragmentGalleryBinding;
import lucastanziano.gallery.di.DaggerGalleryComponent;
import lucastanziano.gallery.di.GalleryComponent;
import lucastanziano.gallery.di.GalleryModule;
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
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(context, 2);
        mBinding.galleryRecyclerView.setLayoutManager(mLayoutManager);
        mBinding.galleryRecyclerView.setAdapter((RecyclerView.Adapter) mAdapter);
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


    public Observable<GalleryItem> getObservableSelectedItem() {
        return mAdapter.getObservableSelectedItem();
    }

    public void registerSubscriberOnGalleryItemSelected(Subscriber<GalleryItem> subscriber){
        mAdapter.getObservableSelectedItem().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void updateLayoutManager(RecyclerView.LayoutManager layoutManager) {
        mBinding.galleryRecyclerView.setLayoutManager(layoutManager);
    }

    public ObservableList<GalleryItem> getObservableItems() {
        return mAdapter.getItems();
    }


    public Context getContext() {
        return context;
    }
}
