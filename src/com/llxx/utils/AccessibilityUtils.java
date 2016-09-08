package com.llxx.utils;

import android.content.Context;
import android.provider.Settings;
import android.provider.Settings.SettingNotFoundException;
import android.text.TextUtils;
import android.util.Log;

public class AccessibilityUtils
{
    public static final String TAG = "AccessibilityUtils";

    public static final boolean isAccessibilitySettingsOn(Context mContext)
    {
        int accessibilityEnabled = 0;
        final String service = "com.llxx.service/com.llxx.socket.service.Ll_AccessibilityService";
        boolean accessibilityFound = false;
        try
        {
            accessibilityEnabled = Settings.Secure.getInt(mContext.getApplicationContext().getContentResolver(),
                    android.provider.Settings.Secure.ACCESSIBILITY_ENABLED);
            Log.v(TAG, "accessibilityEnabled = " + accessibilityEnabled);
        }
        catch (SettingNotFoundException e)
        {
            Log.e(TAG, "Error finding setting, default accessibility to not found: " + e.getMessage());
        }
        TextUtils.SimpleStringSplitter mStringColonSplitter = new TextUtils.SimpleStringSplitter(':');

        if (accessibilityEnabled == 1)
        {
            Log.v(TAG, "***ACCESSIBILIY IS ENABLED*** -----------------");
            String settingValue = Settings.Secure.getString(mContext.getApplicationContext().getContentResolver(),
                    Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);
            if (settingValue != null)
            {
                TextUtils.SimpleStringSplitter splitter = mStringColonSplitter;
                splitter.setString(settingValue);
                while (splitter.hasNext())
                {
                    String accessabilityService = splitter.next();

                    Log.v(TAG, "-------------- > accessabilityService :: " + accessabilityService);
                    if (accessabilityService.equalsIgnoreCase(service))
                    {
                        Log.v(TAG, "We've found the correct setting - accessibility is switched on!");
                        return true;
                    }
                }
            }
        }
        else
        {
            Log.v(TAG, "***ACCESSIBILIY IS DISABLED***");
        }

        return accessibilityFound;
    }
}
