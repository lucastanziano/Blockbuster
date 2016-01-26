package com.lucastanziano.blockbuster.view.main.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import lucastanziano.blockbuster.interactor.MoviesInteractor;
import lucastanziano.blockbuster.view.main.subscriber.OnGalleryItemSelectedSubscriber;
import lucastanziano.gallery.view.Gallery;

/**
 * Created by Luca on 23/01/2016.
 */
public class TabsViewPagerAdapter extends PagerAdapter {

    private Context mContext;

    public TabsViewPagerAdapter(Context context){
        this.mContext = context;
    }

    @Override
    public Object instantiateItem(ViewGroup collection, int position) {
        Log.d("TABS_ADAPTER", "Instantiating item in position " + position);
        TabsPagerEnum customPagerEnum = TabsPagerEnum.values()[position];
        Gallery mGallery = new Gallery(collection);
        mGallery.registerSubscriberOnGalleryItemSelected(new OnGalleryItemSelectedSubscriber(mContext));
        MoviesInteractor  moviesInteractor = new MoviesInteractor(mContext, mGallery.getObservableItems());
        TabsPagerEnum.values()[position].loadMovies(moviesInteractor);

        return mGallery.getView();
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);
        Log.d("TABS_ADAPTER", "Destroying item in position " + position);
        view = null;
    }

    @Override
    public int getCount() {
        return TabsPagerEnum.values().length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        TabsPagerEnum customPagerEnum = TabsPagerEnum.values()[position];
        return mContext.getString(customPagerEnum.getTitleResId());
    }

}
