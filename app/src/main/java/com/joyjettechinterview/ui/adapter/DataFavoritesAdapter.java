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

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.joyjettechinterview.R;
import com.joyjettechinterview.helper.Global;
import com.joyjettechinterview.model.DataModel;
import com.joyjettechinterview.model.FavoritesModelRealm;
import com.joyjettechinterview.ui.activity.DetailsActivity;
import com.joyjettechinterview.ui.activity.IndexActivity;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;

import java.util.List;

public class DataFavoritesAdapter extends RecyclerView.Adapter<DataFavoritesAdapter.ViewHolder> {

    private List<FavoritesModelRealm> items;
    private Context ctx;
    int controlNextImg = 0, controlPrevImg = 0;

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

    public DataFavoritesAdapter(List<FavoritesModelRealm> items, Context ctx) {
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
                .inflate(R.layout.item_data_favorites, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder,final int i) {
        viewHolder.txtTitle.setText(items.get(i).getTitle());
        viewHolder.txtDescription.setText(items.get(i).getDescription());
        viewHolder.txtCategory.setText(items.get(i).getCategory());
        viewHolder.txtId.setText(""+items.get(i).getId());

        String data= items.get(i).getImages();
        JsonParser jsonParser = new JsonParser();
        final JsonArray jsonArray = (JsonArray) jsonParser.parse(data);

        // set image
        try{
            setImage(i, preparesUrl(jsonArray.get(0).toString().substring(1)), viewHolder.imgGallery, viewHolder.imgGalleryLoading);
        } catch (Exception e){
        }

        viewHolder.imgGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                final FavoritesModelRealm favoritesModelRealm = items.get(i);
                Global.globalDataFavoritesModel = favoritesModelRealm;
                Global.imageDetails = preparesUrl(jsonArray.get(0).toString().substring(1));
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

        final FavoritesModelRealm favoritesModelRealm = items.get(i);
        String data= items.get(i).getImages();
        JsonParser jsonParser = new JsonParser();
        JsonArray jsonArray = (JsonArray) jsonParser.parse(data);

        if (Global.globalDataFavoritesModel != items.get(i)) {
            Global.globalDataFavoritesModel = items.get(i);
            controlNextImg = 0;
        }

        if (controlNextImg + 1 == getInt(favoritesModelRealm.getAmountImages())){
            controlNextImg = 0;
            try{
                setImage(i, preparesUrl(jsonArray.get(0).toString().substring(1)), viewHolder.imgGallery, viewHolder.imgGalleryLoading);
            } catch (Exception e){
            }
        } else {
            controlNextImg++;
            try{
                setImage(i, preparesUrl(jsonArray.get(controlNextImg).toString().substring(1)), viewHolder.imgGallery, viewHolder.imgGalleryLoading);
            } catch (Exception e){
            }
        }

    }

    public void setImgPrev(int i, ViewHolder viewHolder){

        final FavoritesModelRealm favoritesModelRealm = items.get(i);
        String data= items.get(i).getImages();
        JsonParser jsonParser = new JsonParser();
        JsonArray jsonArray = (JsonArray) jsonParser.parse(data);

        if (controlPrevImg < 0 ) {
            controlPrevImg = 0;
        }
        if (Global.globalDataFavoritesModel != items.get(i)) {
            Global.globalDataFavoritesModel = items.get(i);
            controlPrevImg = 0;
        }
        if (controlPrevImg == 0) {
            controlPrevImg = 0;
            controlPrevImg  = getInt(favoritesModelRealm.getAmountImages())-1;
            try{
                setImage(i, preparesUrl(jsonArray.get(0).toString().substring(1)), viewHolder.imgGallery, viewHolder.imgGalleryLoading);
            } catch (Exception e){
            }
        } else {
                controlPrevImg--;
            try{
                setImage(i, preparesUrl(jsonArray.get(controlPrevImg).toString().substring(1)), viewHolder.imgGallery, viewHolder.imgGalleryLoading);
            } catch (Exception e){
            }
        }
    }

    public void goToDetails(){
        Global.globalPageFavorite = 1;
        Intent intent = new Intent(ctx, DetailsActivity.class);
        ctx.startActivity(intent);
        ((IndexActivity)ctx).finish();
    }

    private static String preparesUrl(String str) {
        return str.substring(0,str.length()-1);
    }

    public int getInt(String s){
        return Integer.parseInt(s.replaceAll("[\\D]", ""));
    }

    // set image
    public void setImage(final int i, final String address, final ImageView imageView,final ImageView imageViewLoading){
        try {
            imageViewLoading.setVisibility(View.VISIBLE);
            imageView.setVisibility(View.GONE);
            Picasso
                    .with(ctx)
                    .load(address)
                    .placeholder(R.drawable.progress_animation)
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
