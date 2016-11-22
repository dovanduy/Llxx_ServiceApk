/**
 * 
 */
package com.llxx.socket.action;

import android.view.accessibility.AccessibilityEvent;

/**
 * @author fanxin, eachen
 * @date   2016年9月9日
 * @qq 	461051353
 * @describe 获取滚动事件
 */
public class ScrollActionNofity extends AActionNofity
{
    public static final String TAG = "AccessibilityScrollAction";

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
