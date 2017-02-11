package com.joyjettechinterview.model;


import android.os.Parcelable;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.widget.LinearLayout;

import org.json.JSONArray;

public class DataModel {

    private String category;
    private String itens;
    private String title;
    private String description;
    private String galery;
    private LinearLayout linearLayout;
    private FragmentStatePagerAdapter adapter;
    private int amountImages;
    private String[] images;
    private JSONArray arrayImages;
    private int controlImgNext = 0;
    private int controlImgPrev = 0;
    private int id;

    public DataModel() {
    }

    public DataModel(String title, String category, String description,
                     int amountImages, String[] images, JSONArray arrayImages, int id){

        this.title = title;
        this.category = category;
        this.description = description;
        this.amountImages = amountImages;
        this.images = images;
        this.arrayImages = arrayImages;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getControlImgNext() {
        return controlImgNext;
    }

    public void setControlImgNext(int controlImgNext) {
        this.controlImgNext = controlImgNext;
    }

    public int getControlImgPrev() {
        return controlImgPrev;
    }

    public void setControlImgPrev(int controlImgPrev) {
        this.controlImgPrev = controlImgPrev;
    }

    public JSONArray getArrayImages() {
        return arrayImages;
    }

    public void setArrayImages(JSONArray arrayImages) {
        this.arrayImages = arrayImages;
    }

    public String[] getImages() {
        return images;
    }

    public void setImages(String[] images) {
        this.images = images;
    }

    public int getAmountImages() {
        return amountImages;
    }

    public void setAmountImages(int amountImages) {
        this.amountImages = amountImages;
    }

    public FragmentStatePagerAdapter getAdapter() {
        return adapter;
    }

    public void setAdapter(FragmentStatePagerAdapter adapter) {
        this.adapter = adapter;
    }

    public LinearLayout getLinearLayout() {
        return linearLayout;
    }

    public void setLinearLayout(LinearLayout linearLayout) {
        this.linearLayout = linearLayout;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getItens() {
        return itens;
    }

    public void setItens(String itens) {
        this.itens = itens;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGalery() {
        return galery;
    }

    public void setGalery(String galery) {
        this.galery = galery;
    }
}
