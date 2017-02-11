package com.joyjettechinterview.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import com.joyjettechinterview.R;
import com.joyjettechinterview.db.RealmHelper;
import com.joyjettechinterview.helper.Funcitons;
import com.joyjettechinterview.helper.Global;
import com.joyjettechinterview.model.FavoritesModelRealm;
import com.squareup.picasso.Picasso;
import butterknife.BindView;
import butterknife.ButterKnife;
import dyanamitechetan.vusikview.VusikView;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class DetailsActivity extends AppCompatActivity {

    RealmHelper realmHelper = new RealmHelper();
    Funcitons fun = new Funcitons();
    FavoritesModelRealm favoritesModelRealm = new FavoritesModelRealm();
    @BindView(R.id.backdrop) ImageView backdrop;
    @BindView(R.id.category) TextView category;
    @BindView(R.id.title) TextView title;
    @BindView(R.id.description) TextView description;
    @BindView(R.id.id) TextView id;
    @BindView(R.id.jsonArray) TextView jsonArray;
    @BindView(R.id.amountImages) TextView amountImages;
    @BindView(R.id.vusik) VusikView vusikView;
    MenuItem star;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationIcon(R.drawable.icon_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // onBackPressed();
                Intent intent = new Intent(DetailsActivity.this, IndexActivity.class);
                startActivity(intent);
                finish();
            }
        });

        setDataViews();
        setupCustomFont();
        hidebar();
    }

    public void hidebar() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        w.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
         }
    }

    public void setDataViews(){
        if (Global.globalPageFavorite == 1 ){
            title.setText(Global.globalDataFavoritesModel.getTitle());
            description.setText(Global.globalDataFavoritesModel.getDescription());
            category.setText(Global.globalDataFavoritesModel.getCategory());
            id.setText("" + Global.globalDataFavoritesModel.getId());
            jsonArray.setText(Global.globalDataFavoritesModel.getImages().toString());
            amountImages.setText(""+ Global.globalDataFavoritesModel.getAmountImages());
            Global.globalPageFavorite = 0;
            try{
                Picasso.with(this).load(Global.imageDetails).into(backdrop);
            } catch (Exception e){
            }
        } else {
            // set image
            try{
                Picasso.with(this).load(Global.globalDataModelDetails.getArrayImages().get(0).toString()).into(backdrop);
            } catch (Exception e){
            }
            title.setText(Global.globalDataModelDetails.getTitle());
            description.setText(Global.globalDataModelDetails.getDescription());
            category.setText(Global.globalDataModelDetails.getCategory());
            id.setText("" + Global.globalDataModelDetails.getId());
            jsonArray.setText(Global.globalDataModelDetails.getArrayImages().toString());
            amountImages.setText("" + Global.globalDataModelDetails.getAmountImages());
        }
    }

    public  void setupCustomFont(){
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Montserrat-Regular.otf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );

    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_details, menu);
        star = menu.findItem(R.id.action_star);

        if (realmHelper.coutFavorite(id.getText().toString()) > 0){
            star.setIcon(R.drawable.ic_star_yellow);
        } else {
            star.setIcon(R.drawable.ic_star);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){

            case R.id.action_star:
                if (realmHelper.coutFavorite(id.getText().toString()) > 0) {
                    realmHelper.removeFavorite(id.getText().toString());
                    fun.toast(DetailsActivity.this, getResources().getString(R.string.deleted_item));
                    star.setIcon(R.drawable.ic_star);
                    realmHelper.getFavorite();
                } else {
                    star.setIcon(R.drawable.ic_star_yellow);
                    favoritesModelRealm.setId(id.getText().toString());
                    favoritesModelRealm.setTitle(title.getText().toString());
                    favoritesModelRealm.setCategory(category.getText().toString());
                    favoritesModelRealm.setDescription(description.getText().toString());
                    favoritesModelRealm.setJsonArray(jsonArray.getText().toString());
                    favoritesModelRealm.setAmountImages(amountImages.getText().toString());
                    realmHelper.addFavorite(favoritesModelRealm);
                    realmHelper.getFavorite();
                    fun.toast(DetailsActivity.this, getResources().getString(R.string.save_favorite));
                    animationStar();
                    break;
                }
        }
        return true;
    }

    public  void animationStar(){
        int[]  myImageList = new int[]{R.drawable.ic_star_yellow,R.drawable.ic_star_yellow,R.drawable.ic_star_yellow};
        vusikView
                .setImages(myImageList)
                .start();
    }
}
