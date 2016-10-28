package com.llxx.capturescreen;

import com.llxx.service.R;
import com.llxx.socket.Llxx_Application;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.projection.MediaProjectionManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class ScreenActivity extends Activity
{
    private String TAG = "CaptureScreenService";
    private int result = 0;
    private Intent intent = null;
    private int REQUEST_MEDIA_PROJECTION = 1;
    private MediaProjectionManager mMediaProjectionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sc_activity_main);
        mMediaProjectionManager = (MediaProjectionManager) getApplication()
                .getSystemService(Context.MEDIA_PROJECTION_SERVICE);
        startIntent();
    }

    private void startIntent()
    {
        if (intent != null && result != 0)
        {
            Log.i(TAG, "user agree the application to capture screen");
            ((Llxx_Application) getApplication()).setResult(result);
            ((Llxx_Application) getApplication()).setIntent(intent);
            Intent intent = new Intent(getApplicationContext(),
                    CaptureScreenService.class);
            startService(intent);
            Log.i(TAG, "start service CaptureScreenService");
        }
        else
        {
            startActivityForResult(
                    mMediaProjectionManager.createScreenCaptureIntent(),
                    REQUEST_MEDIA_PROJECTION);
            ((Llxx_Application) getApplication())
                    .setMediaProjectionManager(mMediaProjectionManager);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == REQUEST_MEDIA_PROJECTION)
        {
            if (resultCode != Activity.RESULT_OK)
            {
                return;
            }
            else if (data != null && resultCode != 0)
            {
                Log.i(TAG, "user agree the application to capture screen");
                result = resultCode;
                intent = data;
                ((Llxx_Application) getApplication())
                        .setResult(resultCode);
                ((Llxx_Application) getApplication()).setIntent(data);
                Intent intent = new Intent(getApplicationContext(),
                        CaptureScreenService.class);
                startService(intent);
                Log.i(TAG, "start service CaptureScreenService");
                finish();
            }
        }
    }
}
