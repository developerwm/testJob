package com.joyjettechinterview;

import android.app.Application;
import android.content.Context;

import io.realm.Realm;

public class JoyjetTechInterviewApplication extends Application {

    private static JoyjetTechInterviewApplication instance;

        @Override
        public void onCreate() {
            super.onCreate();
             Realm.init(this);
        }

        @Override
        protected void attachBaseContext(Context base) {
            super.attachBaseContext(base);
        }
}
