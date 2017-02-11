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
import com.joyjettechinterview.api.WS;
import com.joyjettechinterview.helper.Global;
import com.joyjettechinterview.model.DataModel;
import com.joyjettechinterview.ui.adapter.DataAdapter;
import com.rey.material.widget.ProgressView;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

public class IndexFragment extends Fragment {
    WS ws = new WS();
    DataModel dataModel = new DataModel();
    @BindView(R.id.circular_progress) ProgressView circular_progress;
    @BindView(R.id.recyData) RecyclerView recyData;
    private DataAdapter adapter;
    private RecyclerView.LayoutManager lManager;

    public IndexFragment() {
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

        View rootView = inflater.inflate(R.layout.fragment_index, container, false);
        ButterKnife.bind(this, rootView);
        // getData();
        (new getDataAsync()).execute();
      //  testRecy();
        return rootView;
    }

      // Class Async add establishment
        private class getDataAsync extends android.os.AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... params) {
                return ws.getData(Global.globalUrlGetData);
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                circular_progress.setVisibility(View.VISIBLE);
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                data(result);
                circular_progress.setVisibility(View.GONE);
        }
    }

    public void data(String data) {
        String category, items = "";
        List listItems = new ArrayList();
        String[] images = new String[2];
        images[0] = "Hello";
        images[1] = "World";
        // read json
        try {
            JSONArray jsonarray = new JSONArray(data);
            for (int i = 0; i < jsonarray.length(); i++) {
                JSONObject jsonobject = jsonarray.getJSONObject(i);
                category = jsonobject.getString("category");
                items = jsonobject.getString("items");
                int id = 0;
                // read itens
                try {
                    JSONArray jsonArrayItems = new JSONArray(items);
                    for (int i2 = 0; i2 < jsonArrayItems.length(); i2++) {
                        id++;
                        JSONObject jsonobject2 = jsonArrayItems.getJSONObject(i2);
                        String galery88 = jsonobject2.getString("galery");
                        JSONArray jsonArray = new JSONArray(galery88);
                        String[] strArr = new String[jsonArray.length()];
                        // set size vector
                        dataModel.setAmountImages(jsonArray.length());
                        // add items
                        listItems.add(new DataModel(jsonobject2.getString("title"),
                                category, jsonobject2.getString("description"),
                                dataModel.getAmountImages(), images, jsonArray, id));
                    }
                } catch (Exception e) {
                }
            }
        } catch (Exception e) {
        }

        recyData.setHasFixedSize(true);
        lManager = new LinearLayoutManager(getActivity());
        recyData.setLayoutManager(lManager);

        adapter = new DataAdapter(listItems, getActivity());
        recyData.setAdapter(adapter);
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

