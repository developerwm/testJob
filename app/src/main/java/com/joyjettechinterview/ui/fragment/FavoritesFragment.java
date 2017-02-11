package com.joyjettechinterview.ui.fragment;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.joyjettechinterview.R;
import com.joyjettechinterview.db.RealmHelper;
import com.joyjettechinterview.helper.Funcitons;
import com.joyjettechinterview.model.FavoritesModelRealm;
import com.joyjettechinterview.ui.adapter.DataFavoritesAdapter;
import com.rey.material.widget.ProgressView;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

public class FavoritesFragment extends Fragment {

    @BindView(R.id.recyFavorites) RecyclerView recyFavorites;
    @BindView(R.id.circular_progress) ProgressView circular_progress;
    RealmHelper realmHelper = new RealmHelper();
    private DataFavoritesAdapter adapter;
    private RecyclerView.LayoutManager lManager;
    Funcitons fun = new Funcitons();
    public FavoritesFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        WifiManager wm = (WifiManager) getActivity().getSystemService(Context.WIFI_SERVICE);
        WifiManager.WifiLock wifiLock = wm.createWifiLock(WifiManager.WIFI_MODE_FULL, "MyWifiLock");
        wifiLock.acquire();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_favorites, container, false);
        ButterKnife.bind(this, rootView);
        circular_progress.setVisibility(View.GONE);
        readData();

        return rootView;
    }

    public void readData(){

        if(realmHelper.qtdFavorites() == 0){
            fun.snackbar(getResources().getString(R.string.you_now_favorites_yet), getActivity());
            return;
        }

        List items = new ArrayList();
        try {
            JSONArray jsonarray = new JSONArray(realmHelper.getJsonFvorites());
            for (int i = 0; i < jsonarray.length(); i++) {
                JSONObject jsonobject = jsonarray.getJSONObject(i);

                items.add(new FavoritesModelRealm(jsonobject.getString("id"),
                        jsonobject.getString("title"),
                        jsonobject.getString("category"),
                        jsonobject.getString("images"),
                        jsonobject.getString("description"),
                        jsonobject.getString("amount_images"),
                        jsonobject.getString("images")));
            }
        } catch (Exception e){
        }

        recyFavorites.setHasFixedSize(true);
        lManager = new LinearLayoutManager(getActivity());
        recyFavorites.setLayoutManager(lManager);

        adapter = new DataFavoritesAdapter(items, getActivity());
        recyFavorites.setAdapter(adapter);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}
