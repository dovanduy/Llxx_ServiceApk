/**
 * 
 */
package com.llxx.socket.action;

import android.content.Context;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

/**
 * @author fanxin, eachen
 * @date   2016年8月19日
 * @qq 	461051353
 * @describe 获取点击事件
 */
public class AccessibilityDialogAction extends AccessibilityAction
{
    public static final String TAG = "AccessibilityClickAction";

    @Override
    protected boolean processEvent(Context context, AccessibilityEvent event, AccessibilityNodeInfo nodeInfo)
    {
        try
        {
            if (getAccessibilityResult().getClassname().equals("android.app.AlertDialog"))
            {
                setResult(getAccessibilityResult().getResult());
                return true;
            }
        }
        catch (Throwable e)
        {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public int getEventType()
    {
        return AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED;
    }

    @Override
    protected String getActoin()
    {
        return "start_dialog";
    }
}
