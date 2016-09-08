/**
 * 
 */
package com.llxx.socket.action;

import com.llxx.socket.loger.Ll_Loger;

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
                System.out.println("AccessibilityActionManager.processEvent()->" + config + "," + config.getEventType()
                        + "," + event.getEventType());
                if (config.getEventType() == event.getEventType())
                {
                    System.out.println("AccessibilityActionManager.processEvent()--> in :" +config);
                    try
                    {
                        boolean isMatch = false;
                        isMatch = config.preProcessEvent(context, event, nodeInfo);
                        if (!isMatch)
                        {
                            continue;
                        }
                        isMatch = config.processEvent(context, event, nodeInfo);
                        System.out.println("AccessibilityActionManager.processEvent()--> in isMatch :" + isMatch);
                        if (isMatch)
                        {
                            System.out.println("AccessibilityActionManager.processEvent()--> in result :" + config.getResult());
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
