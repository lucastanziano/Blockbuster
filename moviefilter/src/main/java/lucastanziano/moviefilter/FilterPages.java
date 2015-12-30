package lucastanziano.moviefilter;

import android.databinding.DataBindingUtil;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import lucastanziano.moviefilter.databinding.ReleasedatePageBinding;

/**
 * Created by Luca on 08/12/2015.
 */
public enum FilterPages {

    RELEASE( R.layout.releasedate_page);
//    RELEASE2( R.layout.releasedate_page);

    private int mLayoutResId;

    FilterPages(int layoutResId) {

        mLayoutResId = layoutResId;
    }

     public ViewGroup inflate(ViewGroup container, FilterData filterData){
         Log.d("DEBUGGG", "inflating " + getLayoutResId());
         LayoutInflater inflater = LayoutInflater.from(container.getContext());
         switch (this){
             case RELEASE: ReleasedatePageBinding binding = DataBindingUtil.inflate(inflater, R.layout.releasedate_page, container, true);
                // binding.setFilter(filterData);
                 return binding.layout;
             default: return container;
         }

     }

    public int getLayoutResId() {
        return mLayoutResId;
    }

}
