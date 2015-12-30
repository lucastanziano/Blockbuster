package lucastanziano.gallery.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.databinding.ViewDataBinding;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
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

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import lucastanziano.gallery.model.GalleryItem;
import lucastanziano.gallery.PosterSizes;
import lucastanziano.gallery.R;
import lucastanziano.gallery.databinding.ItemViewBinding;
import rx.Observable;
import rx.Subscriber;
import rx.subjects.PublishSubject;
import rx.subjects.Subject;


public class MovieGalleryAdapter extends RecyclerView.Adapter<MovieGalleryAdapter.BindingHolder> implements IGalleryAdapterWrapper {

    private ObservableList<GalleryItem> items;

    public PosterSizes size;

    public Intent detailsActivity;

    private Context context;

    private Subject<GalleryItem, GalleryItem> selectedItem;


    public MovieGalleryAdapter(Context context, PosterSizes size) {
        this.context = context;
        this.size = size;
        this.items = new ObservableArrayList<GalleryItem>();
        this.items.addOnListChangedCallback(new OnItemsChangedCallback(this));
        Fresco.initialize(context);
        selectedItem = PublishSubject.create();
    }


    public void setSize(PosterSizes size) {
        this.size = size;
    }

    @Override
    public ObservableList<GalleryItem> getItems() {
        return items;
    }

    @Override
    public Observable<GalleryItem> getObservableSelectedItem() {
        return selectedItem;
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

            String address = "http://image.tmdb.org/t/p/w" + size.getWidth() + getItems().get(position).backgroundImgURL;

            setupItemImage(binding.picture, Uri.parse(address));
            binding.picture.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selectedItem.onNext(getItems().get(position));

                }
            });
        }
    }

    private void setupItemImage(SimpleDraweeView imageView, Uri imageUrl) {
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(imageUrl)
                .setProgressiveRenderingEnabled(true)
                .build();
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setImageRequest(request)
                .setOldController(imageView.getController())
                .build();
        imageView.setController(controller);
        imageView.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) context.getResources().getDimension(R.dimen.poster_grid2_height)));
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

    private static class OnItemsChangedCallback extends ObservableList.OnListChangedCallback<ObservableList<GalleryItem>> {

        private WeakReference<RecyclerView.Adapter> adapterWeakReference;

        private OnItemsChangedCallback(RecyclerView.Adapter adapter) {
            adapterWeakReference = new WeakReference<RecyclerView.Adapter>(adapter);
        }

        @Override
        public void onChanged(ObservableList<GalleryItem> sender) {
            Log.d(this.getClass().getName(), "onChanged called");

        }

        @Override
        public void onItemRangeChanged(ObservableList<GalleryItem> sender, int positionStart, int itemCount) {
            Log.d(this.getClass().getName(), "onItemRangeChanged called");
        }

        @Override
        public void onItemRangeInserted(ObservableList<GalleryItem> sender, int positionStart, int itemCount) {
            Log.d(this.getClass().getName(), "onItemRangeInserted called");
            notifyDataSetChange();
        }

        @Override
        public void onItemRangeMoved(ObservableList<GalleryItem> sender, int fromPosition, int toPosition, int itemCount) {
            Log.d(this.getClass().getName(), "onItemRangeMoved called");
            notifyDataSetChange();
        }

        @Override
        public void onItemRangeRemoved(ObservableList<GalleryItem> sender, int positionStart, int itemCount) {
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
