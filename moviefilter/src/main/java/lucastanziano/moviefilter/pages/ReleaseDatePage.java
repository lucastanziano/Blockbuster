package lucastanziano.moviefilter.pages;

import android.databinding.DataBindingUtil;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import java.util.logging.Filter;

import lucastanziano.moviefilter.FilterData;
import lucastanziano.moviefilter.R;
import lucastanziano.moviefilter.databinding.ReleasedatePageBinding;
import lucastanziano.rangebar.RangeBar;

/**
 * Created by Luca on 10/12/2015.
 */
public class ReleaseDatePage implements FilterPage<FilterData> {

    private int layout = R.layout.releasedate_page;
    private ReleasedatePageBinding mBinding;

    private int startYear = 1960;
    private int interval = 1;

    @Override
    public View inflate(ViewGroup container) {
        LayoutInflater inflater = LayoutInflater.from(container.getContext());
        mBinding = DataBindingUtil.inflate(inflater, layout, container, true);
        mBinding.rangebar.setOnRangeBarChangeListener(new RangeBar.OnRangeBarChangeListener() {
            @Override
            public void onIndexChangeListener(RangeBar rangeBar, int leftThumbIndex, int rightThumbIndex) {
                mBinding.startYear.setText(getStartYear() +"");
                mBinding.endYear.setText(getEndYear()+"");
            }
        });
        return mBinding.getRoot();
    }

    private int getStartYear(){
       return ((int)mBinding.rangebar.getLeftIndex() * interval) + startYear ;
    }

    private int getEndYear(){
       return ((int)mBinding.rangebar.getRightIndex() * interval) + startYear;
    }

    @Override
    public void fillData(FilterData data) {
        data.releaseYearStart = getStartYear();
        data.releaseYearEnd = getEndYear();
    }
}
