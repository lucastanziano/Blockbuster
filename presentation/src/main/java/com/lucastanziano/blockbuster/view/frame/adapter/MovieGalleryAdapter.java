package com.lucastanziano.blockbuster.view.frame.adapter;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.databinding.ViewDataBinding;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.lucastanziano.blockbuster.model.MovieItem;


import org.lucasr.twowayview.TwoWayLayoutManager;

import java.lang.ref.WeakReference;


import lucastanziano.blockbuster.R;
import lucastanziano.blockbuster.databinding.ItemViewBinding;


public class MovieGalleryAdapter extends RecyclerView.Adapter<MovieGalleryAdapter.BindingHolder> {

    private ObservableList<MovieItem> items;


    public Intent detailsActivity;

    private Context context;

    private TwoWayView recyclerView;


    public MovieGalleryAdapter(Context context, TwoWayView recyclerView) {
        this.context = context;
        this.items = new ObservableArrayList<MovieItem>();
        this.items.addOnListChangedCallback(new OnItemsChangedCallback(this));
        this.recyclerView = recyclerView;
        Fresco.initialize(context);
    }


    public ObservableList<MovieItem> getItems() {
        return items;
    }




    @Override
    public BindingHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        ItemViewBinding commentBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.item_view,
                parent,
                false);
        return new BindingHolder(commentBinding);

    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(BindingHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        if (position > -1 && position < getItems().size()) {
            ItemViewBinding binding = (ItemViewBinding) holder.mBinding;

            String address = "http://image.tmdb.org/t/p/w185"  + getItems().get(position).backgroundImgURL;
            boolean isVertical = (recyclerView.getOrientation() == TwoWayLayoutManager.Orientation.VERTICAL);
            setupItemImage(binding.picture, Uri.parse(address), isVertical);

        }
    }

    private void setupItemImage(SimpleDraweeView imageView, Uri imageUrl, boolean isVertical) {
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(imageUrl)
                .setProgressiveRenderingEnabled(true)
                .build();
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setImageRequest(request)
                .setOldController(imageView.getController())
                .build();
        imageView.setController(controller);

       // imageView.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) context.getResources().getDimension(R.dimen.poster_grid2_height)));
        
        final GridLayoutManager.LayoutParams lp =
                    (GridLayoutManager.LayoutParams) imageView.getLayoutParams();

            if (!isVertical) {
                lp.height = ViewGroup.LayoutParams.MATCH_PARENT;
                lp.width = 50; //TODO use R.dimen.value
            } else {
                lp.height = (int) context.getResources().getDimension(R.dimen.poster_grid2_height);
                lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
            }
            imageView.setLayoutParams(lp);
    }
    
    
    public void replaceLayoutManager(LayoutManager newLayoutManager){
         for (int i = 0; i < getItems().size(); i++) {
            notifyItemRemoved(i);
        }
        
        recyclerView.setLayoutManager(newLayoutManager);
        
         for (int i = 0; i < items.size(); i++) {
            notifyItemInserted(i);
        }
    }


    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return getItems().size();
    }


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class BindingHolder extends RecyclerView.ViewHolder {

        private ViewDataBinding mBinding;

        public BindingHolder(ItemViewBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;

        }
    }

    private static class OnItemsChangedCallback extends ObservableList.OnListChangedCallback<ObservableList<MovieItem>> {

        private WeakReference<RecyclerView.Adapter> adapterWeakReference;

        private OnItemsChangedCallback(RecyclerView.Adapter adapter) {
            adapterWeakReference = new WeakReference<RecyclerView.Adapter>(adapter);
        }

        @Override
        public void onChanged(ObservableList<MovieItem> sender) {
            Log.d(this.getClass().getName(), "onChanged called");

        }

        @Override
        public void onItemRangeChanged(ObservableList<MovieItem> sender, int positionStart, int itemCount) {
            Log.d(this.getClass().getName(), "onItemRangeChanged called");
        }

        @Override
        public void onItemRangeInserted(ObservableList<MovieItem> sender, int positionStart, int itemCount) {
            Log.d(this.getClass().getName(), "onItemRangeInserted called");
            notifyDataSetChange();
        }

        @Override
        public void onItemRangeMoved(ObservableList<MovieItem> sender, int fromPosition, int toPosition, int itemCount) {
            Log.d(this.getClass().getName(), "onItemRangeMoved called");
            notifyDataSetChange();
        }

        @Override
        public void onItemRangeRemoved(ObservableList<MovieItem> sender, int positionStart, int itemCount) {
            Log.d(this.getClass().getName(), "onItemRangeRemoved called");
            notifyDataSetChange();
        }

        private void notifyDataSetChange(){
            if (adapterWeakReference.get() != null) {
                Log.d(this.getClass().getName(), "notifying data set changed to adapter");
                adapterWeakReference.get().notifyDataSetChanged();
            }else{
                Log.d(this.getClass().getName(), "adapter reference was NULL");
            }
        }
    }
}
