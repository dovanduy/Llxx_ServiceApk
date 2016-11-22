/**
 * 
 */
package com.llxx.socket.action.manager;

import com.llxx.socket.action.AccessibilityAction;

import android.content.Context;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

/**
 * @author fanxin, eachen
 * @date   2016年8月19日
 * @qq 	461051353
 * @describe 类描述
 */
public class AccessibilityActionManager
{
    public static boolean DEBUG = false;
    /**
     * 处理辅助功能返回的事件
     * @param event
     * @param nodeInfo
     * @return
     */
    public static final String processEvent(Context context, AccessibilityEvent event, AccessibilityNodeInfo nodeInfo)
    {
        for (AccessibilityAction config : AccessibilityActionConfig.ACTIONS)
        {
            try
            {
                if (config.getEventType() == event.getEventType())
                {
                    try
                    {
                        boolean isMatch = false;
                        isMatch = config.preProcessEvent(context, event, nodeInfo);
                        if (!isMatch)
                        {
                            continue;
                        }
                        isMatch = config.processEvent(context, event, nodeInfo);
                        if (isMatch)
                        {
                            return config.getResult();
                        }
                    }
                    catch (Throwable e)
                    {
                        e.printStackTrace();
                    }
                }
            }
            catch (Throwable e)
            {
                e.printStackTrace();
            }
        }
        return "";
    }
}
