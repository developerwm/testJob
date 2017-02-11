package com.joyjettechinterview.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.joyjettechinterview.R;
import com.joyjettechinterview.helper.Global;
import com.joyjettechinterview.model.DataModel;
import com.joyjettechinterview.ui.activity.DetailsActivity;
import com.joyjettechinterview.ui.activity.IndexActivity;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import java.util.List;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {

    private List<DataModel> items;
    private Context ctx;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView txtTitle;
        public TextView txtDescription;
        public TextView txtCategory;
        public TextView txtId;
        public ImageView imgGallery;
        public ImageView imgNext;
        public ImageView imgPrev;
        public ImageView imgGalleryLoading;

        public ViewHolder(View v) {
            super(v);

            txtTitle = (TextView) v.findViewById(R.id.txtTitle);
            txtDescription = (TextView) v.findViewById(R.id.txtDescription);
            txtCategory = (TextView) v.findViewById(R.id.txtCategory);
            txtId = (TextView) v.findViewById(R.id.txtId);
            imgGallery = (ImageView) v.findViewById(R.id.imgGallery);
            imgNext = (ImageView) v.findViewById(R.id.imgNext);
            imgPrev = (ImageView) v.findViewById(R.id.imgPrev);
            imgGalleryLoading = (ImageView) v.findViewById(R.id.imgGalleryLoading);
        }
    }

    public DataAdapter(List<DataModel> items, Context ctx) {
        this.items = items;
        this.ctx = ctx;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_data, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder,final int i) {
        viewHolder.txtTitle.setText(items.get(i).getTitle());
        viewHolder.txtDescription.setText(items.get(i).getDescription());
        viewHolder.txtCategory.setText(items.get(i).getCategory());
        viewHolder.txtId.setText(""+items.get(i).getId());

        try{
            setImage(i, items.get(i).getArrayImages().get(0).toString(), viewHolder.imgGallery, viewHolder.imgGalleryLoading);
         } catch (Exception e){
         }

        viewHolder.imgGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                final DataModel dataModel = items.get(i);
                Global.globalDataModelDetails = dataModel;
                goToDetails();
            }

        });

        viewHolder.imgPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
               setImgPrev(i, viewHolder);
            }

        });

        viewHolder.imgNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                setNextImg(i, viewHolder);
            }

        });
    }

    public  void setNextImg(int i, ViewHolder viewHolder){

        final DataModel dataModel = items.get(i);

        if (Global.globalDataModel != items.get(i)) {
            Global.globalDataModel = items.get(i);
            Global.globalControlNextImg = 0;
        }

        if (Global.globalControlNextImg + 1 == dataModel.getAmountImages()) {
            Global.globalControlNextImg = 0;
            try{
                setImage(i, items.get(i).getArrayImages().get(0).toString(), viewHolder.imgGallery, viewHolder.imgGalleryLoading);
            } catch (Exception e){
            }

        } else {
            Global.globalControlNextImg++;
            try{
                setImage(i, items.get(i).getArrayImages().get(Global.globalControlNextImg).toString(), viewHolder.imgGallery, viewHolder.imgGalleryLoading);
            } catch (Exception e){
            }
        }
    }

    public void setImgPrev(int i, ViewHolder viewHolder){

        final DataModel dataModel = items.get(i);

        if (Global.globalControlPrevImg < 0 ) {
            Global.globalControlPrevImg = 0;
        }
        if (Global.globalDataModel != items.get(i)) {
            Global.globalDataModel = items.get(i);
            Global.globalControlPrevImg = 0;
        }
        if (Global.globalControlPrevImg == 0) {
            Global.globalControlPrevImg = 0;
            Global.globalControlPrevImg = dataModel.getAmountImages()-1;
            try{
                setImage(i, items.get(i).getArrayImages().get(dataModel.getAmountImages()-1).toString(), viewHolder.imgGallery, viewHolder.imgGalleryLoading);
            } catch (Exception e){
            }

        } else {
            Global.globalControlPrevImg--;
            try{
                setImage(i, items.get(i).getArrayImages().get(Global.globalControlPrevImg).toString(), viewHolder.imgGallery, viewHolder.imgGalleryLoading);
            } catch (Exception e){
            }
        }
    }

    public void goToDetails(){
        Intent intent = new Intent(ctx, DetailsActivity.class);
        ctx.startActivity(intent);
        ((IndexActivity)ctx).finish();
    }

    // set image
    public void setImage(final int i, final String address, final ImageView imageView,final ImageView imageViewLoading){
        try {
            imageViewLoading.setVisibility(View.VISIBLE);
            imageView.setVisibility(View.GONE);
            Picasso
                    .with(ctx)
                    .load(address)
                    .placeholder(R.drawable.progress_animation) // can also be a drawable
                    .into(imageViewLoading, new Callback() {
                        @Override
                        public void onSuccess() {
                            try {
                                imageViewLoading.setVisibility(View.GONE);
                                imageView.setVisibility(View.VISIBLE);
                                Picasso
                                        .with(ctx)
                                        .load(address)
                                        .noPlaceholder()
                                        .into(imageView);

                            } catch (Exception e){
                            }
                        }

                        @Override
                        public void onError() {

                        }
                    });
        } catch (Exception e){
        }
    }
}
