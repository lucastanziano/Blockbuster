package com.lucastanziano.blockbuster.view.frame;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lucastanziano.blockbuster.internal.di.PerActivity;
import com.lucastanziano.blockbuster.internal.di.Repository;
import com.lucastanziano.blockbuster.view.frame.adapter.MovieTabsViewPagerAdapter;
import com.lucastanziano.blockbuster.view.frame.tabs.ComingSoonMoviesTab;
import com.lucastanziano.blockbuster.view.frame.tabs.IMovieTab;
import com.lucastanziano.blockbuster.view.frame.tabs.PopularMoviesTab;
import com.lucastanziano.blockbuster.view.frame.tabs.TopRatedMoviesTab;
import com.lucastanziano.blockbuster.widget.slidingtab.SlidingTabLayout;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import lucastanziano.blockbuster.R;
import lucastanziano.blockbuster.databinding.TabsLayoutBinding;

/**
 * Created by Luca on 25/01/2016.
 */
@PerActivity
public class MovieTabsFrame implements  IFrame{

    private Context context;

    private List<IMovieTab> tabs = Arrays.asList(PopularMoviesTab.newInstance(Repository.getDefaultMovieRepository()),
            new TopRatedMoviesTab(Repository.getDefaultMovieRepository())
            , new ComingSoonMoviesTab(Repository.getDefaultMovieRepository()));

    private TabsLayoutBinding tabsBinding;

    @Inject
    public MovieTabsFrame(ViewGroup container){
        setContext(container.getContext());
        attachTo(container);
    }

    @Override
    public void attachTo(final ViewGroup container) {

        TabsLayoutBinding tabsBinding = DataBindingUtil.inflate(LayoutInflater.from(getContext()),
                R.layout.tabs_layout, container, true);
        // Creating The ViewPagerAdapter and Passing Fragment Manager, Titles fot the Tabs and Number Of Tabs.
        final PagerAdapter adapter =  new MovieTabsViewPagerAdapter(getContext(), tabs);

        // Assigning ViewPager View and setting the adapter
        ViewPager pager = tabsBinding.pager;
        pager.setAdapter(adapter);
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
             //   adapter.instantiateItem(container, position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

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

    @Override
    public View getView() {
        return tabsBinding.getRoot();
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public Context getContext() {
        return context;
    }
}
