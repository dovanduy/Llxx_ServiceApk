/**
 * 
 */
package com.llxx.socket.action;

import android.view.accessibility.AccessibilityEvent;

/**
 * @author fanxin, eachen
 * @date   2016年9月9日
 * @qq 	461051353
 * @describe 文本改变事件监听
 */
public class TextChangeNofity extends AActionNofity
{
    public static final String TAG = "AccessibilityTextChangeAction";

    @Override
    public int getEventType()
    {
        return AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED;
    }

    @Override
    protected String getActoin()
    {
        return "textchange";
    }
}
