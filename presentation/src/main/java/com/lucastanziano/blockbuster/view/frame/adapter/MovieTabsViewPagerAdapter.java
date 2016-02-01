package com.lucastanziano.blockbuster.view.frame.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.lucastanziano.blockbuster.view.frame.tabs.IMovieTab;

import java.util.List;


/**
 * Created by Luca on 23/01/2016.
 */
public class MovieTabsViewPagerAdapter extends PagerAdapter {

    private final List<IMovieTab> tabs;
    private Context mContext;


    public MovieTabsViewPagerAdapter(Context context, List<IMovieTab> tabs) {
        this.mContext = context;
        this.tabs = tabs;
    }


    @Override
    public Object instantiateItem(ViewGroup collection, int position) {
        Log.d("TABS_ADAPTER", "Instantiating item in position " + position);
        View item = collection;
        if(tabs != null && tabs.size()>position) {
            IMovieTab tab = tabs.get(position);
            tab.instantiateFrame(collection);
            item = tab.getFrame().getView();
        }

        return item;
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);
        Log.d("TABS_ADAPTER", "Destroying item in position " + position);
        view = null;
    }

    @Override
    public int getCount() {
        return tabs.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getString(tabs.get(position).getTitleResId());
    }

}
