package com.llxx.socket;

import android.app.Application;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.media.projection.MediaProjectionManager;

public class Llxx_Application extends Application
{

    private static Llxx_Application application = null;

    private int result;
    private Intent intent;
    private MediaProjectionManager mMediaProjectionManager;
    ClipboardManager clipboard;

    @Override
    public void onCreate()
    {
        application = this;
        super.onCreate();
        clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
    }

    public ClipboardManager getClipboardManager()
    {
        return clipboard;
    }

    public static Llxx_Application getApplication()
    {
        return application;
    }

    public int getResult()
    {
        return result;
    }

    public Intent getIntent()
    {
        return intent;
    }

    public MediaProjectionManager getMediaProjectionManager()
    {
        return mMediaProjectionManager;
    }

    public void setResult(int result1)
    {
        this.result = result1;
    }

    public void setIntent(Intent intent1)
    {
        this.intent = intent1;
    }

    public void setMediaProjectionManager(MediaProjectionManager mMediaProjectionManager)
    {
        this.mMediaProjectionManager = mMediaProjectionManager;
    }
}
