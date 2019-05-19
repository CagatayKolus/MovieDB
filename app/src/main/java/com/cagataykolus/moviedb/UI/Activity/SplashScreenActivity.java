package com.cagataykolus.moviedb.UI.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.cagataykolus.moviedb.BuildConfig;
import com.cagataykolus.moviedb.R;
import com.cagataykolus.moviedb.UI.Util.Util;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!Util.isOnline(SplashScreenActivity.this)) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertDialogTheme);
                builder.setTitle(getResources().getString(R.string.errorConnectionTitle));
                builder.setMessage(getResources().getString(R.string.errorConnectionMessage));
                builder.setPositiveButton(getResources().getString(R.string.errorConnectionTryAgain), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        restartApp();
                    }
                });
                builder.setCancelable(false);
                builder.show();
        } else {
            if(BuildConfig.BUILD_TYPE.equals("debug")){
                Toast.makeText(this, getResources().getString(R.string.infoBuildTypeDebug), Toast.LENGTH_SHORT).show();
            }
            Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void restartApp(){
        Intent intent = getBaseContext().getPackageManager().
                getLaunchIntentForPackage(getBaseContext().getPackageName());
        assert intent != null;
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
