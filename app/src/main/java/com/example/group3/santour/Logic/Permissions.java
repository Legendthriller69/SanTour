package com.example.group3.santour.Logic;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;

import static android.os.Build.VERSION_CODES.M;

/**
 * Created by aleks on 21.11.2017.
 */

public class Permissions {

    public static void checkPermissions(Activity activity){
        int PERMISSION_ALL = 1;

        //List of all the permissions needed by the app
        String[] permissions = new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION,
        };

        if (!hasPermissions(activity, permissions)) {
            ActivityCompat.requestPermissions(activity, permissions, PERMISSION_ALL);
        }
    }

    private static boolean hasPermissions(Context context, String... permissions) {
        if (android.os.Build.VERSION.SDK_INT >= M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }
}
