package com.joyjettechinterview.api;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class WS {

    Response response = null;
    OkHttpClient client = new OkHttpClient();
    String result = "";

    /* ============== GET, get   ================ */
    public String getData(String url) {
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        try {
            response = client.newCall(request).execute();
            result = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result.toString();
    }
}
