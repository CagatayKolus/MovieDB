package com.cagataykolus.moviedb.UI.Util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class Util {
    public static final String API_KEY = "6fadac3923b89b56f2427b0927fdd739";
    public static final String BASE_URL_IMG_W200 = "https://image.tmdb.org/t/p/w200";
    public static final String BASE_URL_IMG_W500 = "https://image.tmdb.org/t/p/w500";
    public static final String PREFS_NAME = "myPrefsFile";
    public static final int MOVIES_FIRST_PAGE = 1;
    public static final int MOVIES_TOTAL_PAGES = 5;

    public static boolean isOnline(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        assert cm != null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {

            Network[] activeNetworks = cm.getAllNetworks();
            for (Network n : activeNetworks) {
                NetworkInfo nInfo = cm.getNetworkInfo(n);
                if (nInfo.isConnected())
                    return true;
            }
        } else {
            NetworkInfo[] info = cm.getAllNetworkInfo();
            if (info != null)
                for (NetworkInfo anInfo : info)
                    if (anInfo.getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
        }
        return false;
    }

    public static void closeKeyboard(View v) {
        InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        assert imm != null;
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }
}
