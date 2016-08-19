package com.llxx.utils;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

public class ToastUtil
{
    private static Toast mToast;

    /**
     * 弹出制定的消息
     * @param msg
     */
    public static void showToast(Context context, String msg)
    {
        if (mToast == null)
        {
            mToast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
            mToast.setGravity(Gravity.CENTER, 0, 0);
        }
        mToast.setText(msg);
        mToast.show();
    }

    /**
     * 弹出指定的消息
     * @param activity
     * @param msg
     */
    public static void showToast(Activity activity, String msg)
    {
        if (mToast == null)
        {
            mToast = Toast.makeText(activity.getApplicationContext(), msg,
                    Toast.LENGTH_SHORT);
            mToast.setGravity(Gravity.CENTER, 0, 0);
        }
        mToast.setText(msg);
        mToast.show();
    }
}