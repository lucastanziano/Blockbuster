package com.lucastanziano.blockbuster.widget.gallery.view.adapter;

import android.databinding.ObservableList;
import android.support.v7.widget.RecyclerView;

import com.lucastanziano.blockbuster.widget.gallery.model.GalleryItem;

import java.util.List;

import rx.Observable;

/**
 * Created by Luca on 06/12/2015.
 */
public interface IGalleryAdapterWrapper {

    public ObservableList<GalleryItem> getItems();

//    public Observable<GalleryItem> getObservableSelectedItem();
}
