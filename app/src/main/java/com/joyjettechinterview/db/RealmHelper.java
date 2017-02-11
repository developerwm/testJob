package com.joyjettechinterview.db;

import android.content.Context;
import android.util.Log;
import com.joyjettechinterview.model.FavoritesModelRealm;
import org.json.JSONArray;
import org.json.JSONObject;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class RealmHelper {

    public Context context;

    private RealmConfiguration realmConfiguration = new RealmConfiguration.Builder()
            .name("default1")
            .schemaVersion(1)
            .deleteRealmIfMigrationNeeded()
            .build();

    private Realm realm = Realm.getInstance(realmConfiguration);

    /* =============== Favorites  ============== */
    public void addFavorite(FavoritesModelRealm favoritesModelRealm) {
        realm.beginTransaction();
        realm.insert(favoritesModelRealm);
        realm.commitTransaction();
    }

    public void removeFavorite(String id) {
        RealmResults<FavoritesModelRealm> dataDesults = realm.where(FavoritesModelRealm.class).equalTo("id", id).findAll();
        realm.beginTransaction();
        dataDesults.deleteAllFromRealm();
        realm.commitTransaction();
    }

    public long coutFavorite(String id) {
        RealmQuery<FavoritesModelRealm> query = realm.where(FavoritesModelRealm.class).equalTo("id", id);
        return query.count();
    }

    public void getFavorite() {
        for (FavoritesModelRealm favoritesModelRealm : realm.where(FavoritesModelRealm.class).findAll()) {
            Log.e("favorites",favoritesModelRealm .toString());
        }
    }

    public String getJsonFvorites() {
        JSONArray req = new JSONArray();
        for (FavoritesModelRealm favoritesModelRealm : realm.where(FavoritesModelRealm.class).findAll()) {
            JSONObject obj = new JSONObject();
            try {
                obj.put("id", favoritesModelRealm.getId());
                obj.put("category", favoritesModelRealm.getCategory());
                obj.put("title", favoritesModelRealm.getTitle());
                obj.put("images", favoritesModelRealm.getJsonArray());
                obj.put("description", favoritesModelRealm.getDescription());
                obj.put("amount_images", favoritesModelRealm.getAmountImages());
                req.put(obj);
            } catch (Exception ignored) {
            }
        }
        return req.toString();
    }

    public long qtdFavorites() {
        return realm.where(FavoritesModelRealm.class).count();
    }


}
