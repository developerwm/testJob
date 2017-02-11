package com.joyjettechinterview.helper;

import com.joyjettechinterview.model.DataModel;
import com.joyjettechinterview.model.FavoritesModelRealm;

public class Global {

    // variables
    public static int globalControlNextImg = 0;
    public static int globalControlPrevImg = 0;

    //control positon
    public static DataModel globalDataModel;
    public static FavoritesModelRealm globalDataFavoritesModel;

    // model details
    public static DataModel globalDataModelDetails;

    // controlPageFavorite
    public static int globalPageFavorite = 0;

    public static  String imageDetails = "";
    // urls
    // GetData
    public static String globalUrlGetData = "https://cdn.joyjet.com/tech-interview/mobile-test-one.json";
}
