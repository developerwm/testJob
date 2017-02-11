package com.joyjettechinterview.model;

import com.joyjettechinterview.ui.fragment.FavoritesFragment;
import org.json.JSONArray;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class FavoritesModelRealm extends RealmObject {

    @PrimaryKey
    private String id;
    private String title;
    private String category;
    private String jsonArray;
    private String description;
    private String amountImages;
    private String images;

    public FavoritesModelRealm(){
    }

    public FavoritesModelRealm(String id, String title, String category, String jsonArray,
                               String description, String amountImages, String images){

        this.id = id;
        this.title = title;
        this.category = category;
        this.jsonArray = jsonArray;
        this.description = description;
        this.amountImages = amountImages;
        this.images = images;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getAmountImages() {
        return amountImages;
    }
    public void setAmountImages(String amountImages) {
        this.amountImages = amountImages;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getJsonArray() {
        return jsonArray;
    }

    public void setJsonArray(String jsonArray) {
        this.jsonArray = jsonArray;
    }
}
