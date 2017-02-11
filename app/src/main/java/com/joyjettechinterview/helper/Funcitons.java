package com.joyjettechinterview.helper;

import android.app.Activity;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Toast;

import com.joyjettechinterview.R;

public class Funcitons {


    public  void snackbar(String message,final Activity activity){

        Snackbar snackbar = Snackbar
                .make(activity.findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG)
                .setAction(R.string.close, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Snackbar snackbar1 = Snackbar.make(activity.findViewById(android.R.id.content),
                                R.string.restored_message, Snackbar.LENGTH_SHORT);
                        snackbar1.show();
                    }
                });
        snackbar.setActionTextColor(activity.getResources().getColor(R.color.colorPrimary));
        snackbar.show();

    }

    /* ============== Toast ================ */
    public void toast(Activity activity, String message){
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
    }

}
