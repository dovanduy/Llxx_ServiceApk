/**
 * 
 */
package com.llxx.socket.action;

import android.content.Context;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

/**
 * @author fanxin, eachen
 * @date   2016年9月9日
 * @qq 	461051353
 * @describe 获取滚动事件
 */
public class AccessibilityScrollAction extends AccessibilityAction
{
    public static final String TAG = "AccessibilityToastAction";

    @Override
    protected boolean processEvent(Context context, AccessibilityEvent event, AccessibilityNodeInfo nodeInfo)
    {
        try
        {
            setResult(getAccessibilityResult().getResult());
            return true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public int getEventType()
    {
        return AccessibilityEvent.TYPE_VIEW_SCROLLED;
    }

    @Override
    protected String getActoin()
    {
        return "scrolled";
    }
}
