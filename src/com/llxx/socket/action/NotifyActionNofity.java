/**
 * 
 */
package com.llxx.socket.action;

import android.view.accessibility.AccessibilityEvent;

/**
 * @author fanxin, eachen
 * @date   2016年8月19日
 * @qq 	461051353
 * @describe 获取Toast事件
 */
public class NotifyActionNofity extends AActionNofity
{
    public static final String TAG = "AccessibilityToastAction";

    @Override
    public int getEventType()
    {
        return AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED;
    }

    @Override
    protected String getActoin()
    {
        return "notify";
    }
}
