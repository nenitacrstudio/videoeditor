package com.video_editor.pro.ActivityMain;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import com.appodeal.ads.Appodeal;
import com.appodeal.ads.native_ad.views.NativeAdViewNewsFeed;
import com.video_editor.pro.ActivityMusicList.ActivityMusicList;
import com.video_editor.pro.ActivityPhotoVideo.ActivitySelectImageAndVideo;
import com.video_editor.pro.ActivityVideoList.ActivityVideoList;
import com.video_editor.pro.ActivityVideoMusicList.ActivityVideoMusicList;
import com.video_editor.pro.R;
import com.video_editor.pro.UtilsAndAdapters.AppodealConstant;
import com.video_editor.pro.UtilsAndAdapters.EditorHelper;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

public class ActivityMain extends AppCompatActivity {


    static final boolean a = true;
    private Toolbar toolbar;
    RelativeLayout samplelayout;
    private LinearLayout adviewthree;
    boolean doubleBackToExitPressedOnce = false;



    public ActivityMain() {
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= 23) {
            requestPermissions(new String[]{"android.permission.READ_EXTERNAL_STORAGE",
                    "android.permission.WRITE_EXTERNAL_STORAGE"}, 101);
        }
        Appodeal.initialize(this, getString(R.string.appodeal_appkey), Appodeal.BANNER|Appodeal.INTERSTITIAL|Appodeal.MREC|Appodeal.REWARDED_VIDEO, false);

        Appodeal.setMrecViewId(R.id.appodealMrecView);
        Appodeal.show(this, Appodeal.INTERSTITIAL);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);





        this.samplelayout = findViewById(R.id.samplelayout);

        RelativeLayout exit = findViewById(R.id.exit_now);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAffinity();
            }
        });


    }




    @Override
    protected void onResume() {


        super.onResume();
    }







    @Override
    public void onBackPressed() {

        if (doubleBackToExitPressedOnce) {
            finishAffinity();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        samplelayout.setVisibility(View.VISIBLE);

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }





    @Override
    protected void onDestroy() {

        super.onDestroy();
    }

    public void videocutter(View view) {
        EditorHelper.ModuleId = 1;
        final Intent intent = new Intent(this, ActivityVideoList.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
        getads();




    }

    private void getads() {
       if(AppodealConstant.rewardads==2){
           AppodealConstant.rewardads = 0;
           Appodeal.show(this, Appodeal.REWARDED_VIDEO);
       }else {
           AppodealConstant.rewardads=AppodealConstant.rewardads+1;
       }
    }

    public void videocompress(View view) {
        EditorHelper.ModuleId = 2;
        final Intent intent = new Intent(this, ActivityVideoList.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
        getads();

    }

    public void videotomp3(View view) {
        EditorHelper.ModuleId = 3;
        final Intent intent = new Intent(this, ActivityVideoMusicList.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
        getads();

    }


    public void audiovideomixer(View view) {
        EditorHelper.ModuleId = 4;
        final Intent intent = new Intent(this, ActivityVideoList.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
        getads();

    }

    public void videomute(View view) {
        EditorHelper.ModuleId = 5;
        final Intent intent = new Intent(this, ActivityVideoList.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
        getads();

    }

    public void videojoin(View view) {
        EditorHelper.ModuleId = 6;
        final Intent intent = new Intent(this, com.video_editor.pro.ActivityVideoJoiner.ActivityVideoList.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
        getads();

    }

    public void videotoimg(View view) {
        EditorHelper.ModuleId = 7;
        final Intent intent = new Intent(this, com.video_editor.pro.ActivityVideoGIF.ActivityVideoList.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
        getads();

    }

    public void videoformatchange(View view) {
        EditorHelper.ModuleId = 8;
        final Intent intent = new Intent(this, ActivityVideoList.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
        getads();

    }

    public void fastmotion(View view) {
        EditorHelper.ModuleId = 9;
        final Intent intent = new Intent(this, ActivityVideoList.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
        getads();

    }

    public void slowmotion(View view) {
        EditorHelper.ModuleId = 10;
        final Intent intent = new Intent(this, ActivityVideoList.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
        getads();

    }

    public void videocrop(View view) {
        EditorHelper.ModuleId = 11;
        final Intent intent = new Intent(this, ActivityVideoList.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
        getads();

    }

    public void videotogif(View view) {
        EditorHelper.ModuleId = 12;
        final Intent intent = new Intent(this, com.video_editor.pro.ActivityVideoGIF.ActivityVideoList.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
        getads();

    }

    public void videorotate(View view) {
        EditorHelper.ModuleId = 13;
        final Intent intent = new Intent(this, ActivityVideoList.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
        getads();

    }


    public void videomirror(View view) {
        EditorHelper.ModuleId = 14;
        final Intent intent = new Intent(this, ActivityVideoList.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
        getads();

    }

    public void videosplit(View view) {
        EditorHelper.ModuleId = 15;
        final Intent intent = new Intent(this, ActivityVideoList.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
        getads();

    }

    public void videoreverse(View view) {
        EditorHelper.ModuleId = 16;
        final Intent intent = new Intent(this, ActivityVideoList.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
        getads();

    }


    public void audiocompress(View view) {
        EditorHelper.ModuleId = 18;
        final Intent intent = new Intent(this, ActivityMusicList.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
        getads();

    }

    public void audiojoiner(View view) {
        EditorHelper.ModuleId = 19;
        final Intent intent = new Intent(this, ActivityMusicList.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
        getads();

    }

    public void audiocutter(View view) {
        EditorHelper.ModuleId = 20;
        final Intent intent = new Intent(this, ActivityMusicList.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
        getads();

    }

    public void phototovideo(View view) {
        EditorHelper.ModuleId = 21;
        final Intent intent = new Intent(this, ActivitySelectImageAndVideo.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
        getads();

    }

    public void videowatermark(View view) {
        EditorHelper.ModuleId = 22;
        final Intent intent = new Intent(this, ActivityVideoList.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
        getads();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return a;
    }

    public void DisappearExitDialog(View view) {
        samplelayout.setVisibility(INVISIBLE);
    }














    }

