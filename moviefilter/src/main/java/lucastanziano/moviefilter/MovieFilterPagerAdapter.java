package lucastanziano.moviefilter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lucastanziano.moviefilter.pages.FilterPage;
import lucastanziano.moviefilter.pages.ReleaseDatePage;

/**
 * Created by Luca on 08/12/2015.
 */
public class MovieFilterPagerAdapter extends PagerAdapter {

    private Context mContext;


    private List<FilterPage<FilterData>> pages;

    public MovieFilterPagerAdapter(Context context) {
        mContext = context;
        pages = new ArrayList<FilterPage<FilterData>>();
        pages.add(new ReleaseDatePage());
    }

    @Override
    public Object instantiateItem(ViewGroup collection, int position) {
        FilterPage page = pages.get(position);
        View layout = page.inflate(collection);
        collection.addView(layout);
        return layout;
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);
    }

    @Override
    public int getCount() {
        return pages.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public CharSequence getPageTitle(int position) {
         return "";
    }

    public FilterData getFilterData(){
        FilterData filterData = new FilterData();
        for (FilterPage<FilterData> page: pages) {
            page.fillData(filterData);
        }
        return filterData;
    }

}
