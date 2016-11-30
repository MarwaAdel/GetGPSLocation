package com.example.getgpslocation;

import android.util.Log;

import com.onesignal.OneSignal;

import org.json.JSONObject;

/**
 * Created by Eman on 11/29/2016.
 */

public class ExampleNotificationOpenedHandler implements OneSignal.NotificationOpenedHandler {
    @Override
    public void notificationOpened(String message, JSONObject additionalData, boolean isActive) {
        try {
            if (additionalData != null) {
                if (additionalData.has("actionSelected"))
                    Log.d("OneSignalExample", "OneSignal notification button with id " + additionalData.getString("actionSelected") + " pressed");

                Log.d("OneSignalExample", "Full additionalData:\n" + additionalData.toString());
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
}