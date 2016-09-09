/**
 * 
 */
package com.llxx.socket.action;

import android.view.accessibility.AccessibilityEvent;

/**
 * @author fanxin, eachen
 * @date   2016年8月19日
 * @qq 	461051353
 * @describe 获取点击事件
 */
public class AccessibilityWinchageAction extends AccessibilityAction
{
    public static final String TAG = "AccessibilityClickAction";

    @Override
    public int getEventType()
    {
        return AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED;
    }

    @Override
    protected String getActoin()
    {
        return "window_state";
    }
}
