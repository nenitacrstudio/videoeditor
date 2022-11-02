package com.video_editor.pro.ActivitySplash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.RelativeLayout;


import com.appodeal.ads.Appodeal;
import com.appodeal.ads.InterstitialCallbacks;
import com.video_editor.pro.ActivityAudioCompress.ActivityAudioCompressor;
import com.video_editor.pro.ActivityMain.ActivityMain;
import com.video_editor.pro.ActivityNoInternet.ActivityNoInternet;
import com.video_editor.pro.R;

public class ActivitySplash extends AppCompatActivity {
    int value = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        isConnected(this);
        Appodeal.initialize(this, getString(R.string.appodeal_appkey), Appodeal.BANNER|Appodeal.INTERSTITIAL|Appodeal.MREC, false);
        Appodeal.setInterstitialCallbacks(new InterstitialCallbacks() {
            @Override
            public void onInterstitialLoaded(boolean isPrecache) {
                // Called when interstitial is loaded
            }
            @Override
            public void onInterstitialFailedToLoad() {
                // Called when interstitial failed to load
            }
            @Override
            public void onInterstitialShown() {
                // Called when interstitial is shown
            }
            @Override
            public void onInterstitialShowFailed() {
                // Called when interstitial show failed
            }
            @Override
            public void onInterstitialClicked() {
                // Called when interstitial is clicked
            }
            @Override
            public void onInterstitialClosed() {
                // Called when interstitial is closed
                if (value == 1) {

                    if (isConnected(ActivitySplash.this)){
                        Intent intent = new Intent(ActivitySplash.this, ActivityMain.class);
                        startActivity(intent);
                    } else {
                        Intent n = new Intent(ActivitySplash.this, ActivityNoInternet.class);
                        startActivity(n);
                    }

                }
            }
            @Override
            public void onInterstitialExpired()  {
                // Called when interstitial is expired
            }
        });

        int SPLASH_TIME_OUT = 2500;
        new Handler().postDelayed(new Runnable() {


            @Override
            public void run() {

                if (Appodeal.isLoaded(Appodeal.INTERSTITIAL)) {
                    Appodeal.show(ActivitySplash.this, Appodeal.INTERSTITIAL);
                    value = 1;
                }
                else {
                    if (isConnected(ActivitySplash.this)){
                        Intent intent = new Intent(ActivitySplash.this, ActivityMain.class);
                        startActivity(intent);
                    } else {
                        Intent n = new Intent(ActivitySplash.this, ActivityNoInternet.class);
                        startActivity(n);
                    }
                }
                finish();
            }
        }, SPLASH_TIME_OUT);

    }



    public boolean isConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if ((wifiInfo != null && wifiInfo.isConnected()) || (mobileInfo != null && mobileInfo.isConnected())) {
            return true;
        } else {

            return false;
        }


    }


}