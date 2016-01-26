package com.lucastanziano.blockbuster.view.frame;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.lucastanziano.blockbuster.internal.di.PerActivity;
import com.lucastanziano.blockbuster.view.main.adapter.TabsViewPagerAdapter;
import com.lucastanziano.blockbuster.widget.slidingtab.SlidingTabLayout;

import javax.inject.Inject;

import lucastanziano.blockbuster.R;
import lucastanziano.blockbuster.databinding.TabsLayoutBinding;

/**
 * Created by Luca on 25/01/2016.
 */
@PerActivity
public class MovieTabsFrame implements  IFrame{

    private Context context;

    @Inject
    public MovieTabsFrame(ViewGroup container){
        setContext(container.getContext());
        attachTo(container);
    }

    @Override
    public void attachTo(ViewGroup container) {

        TabsLayoutBinding tabsBinding = DataBindingUtil.inflate(LayoutInflater.from(getContext()),
                R.layout.tabs_layout, container, true);
        // Creating The ViewPagerAdapter and Passing Fragment Manager, Titles fot the Tabs and Number Of Tabs.
        PagerAdapter adapter =  new TabsViewPagerAdapter(getContext());

        // Assigning ViewPager View and setting the adapter
        ViewPager pager = tabsBinding.pager;
        pager.setAdapter(adapter);

        // Assiging the Sliding Tab Layout View
        SlidingTabLayout tabs = tabsBinding.tabs;
        tabs.setDistributeEvenly(true); // To make the Tabs Fixed set this true, This makes the tabs Space Evenly in Available width

        // Setting Custom Color for the Scroll bar indicator of the Tab View
        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getContext().getResources().getColor(R.color.tabsScrollColor);
            }
        });

        // Setting the ViewPager For the SlidingTabsLayout
        tabs.setViewPager(pager);
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public Context getContext() {
        return context;
    }
}
