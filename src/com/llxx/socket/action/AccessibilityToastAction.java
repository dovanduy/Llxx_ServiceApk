/**
 * 
 */
package com.llxx.socket.action;

import com.llxx.socket.loger.Ll_Loger;
import com.llxx.socket.protocol.wrap.ProtocolConstants;
import com.llxx.socket.protocol.wrap.ProtocolNotify;

import android.content.Context;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

/**
 * @author 李万隆
 * @date   2016年8月19日
 * @qq 	461051353
 * @describe 获取Toast事件
 */
public class AccessibilityToastAction extends AccessibilityAction
{
    public static final String TAG = "AccessibilityToastAction";

    @Override
    protected boolean processEvent(Context context, AccessibilityEvent event,
            AccessibilityNodeInfo nodeInfo)
    {
        setResult("");
        // Ll_Loger.i(TAG, event.getBeforeText().toString());
        if (event.getClassName() != null)
            Ll_Loger.i(TAG, "class name: " + event.getClassName().toString());

        // Ll_Loger.i(TAG, event.getContentDescription().toString());
        // Ll_Loger.i(TAG, event.getText().toString());
        if (event.getPackageName() != null)
            Ll_Loger.i(TAG,
                    "package name: " + event.getPackageName().toString());
        try
        {
            if (event.getClassName().toString()
                    .startsWith("android.widget.Toast"))
            {
                ProtocolNotify activity = new ProtocolNotify();
                activity.setClassname(event.getClassName().toString());
                activity.setPackageName(event.getPackageName().toString());
                activity.setType(ProtocolConstants.NOTIF_TYPE_TOAST);
                activity.setTitle(event.getText().get(0).toString());
                setResult(activity.getResult(context));
                // Ll_Loger.i(TAG, nodeInfo.toString());
                // setResult("notification|toast|" + event.getPackageName() + "|"
                //         + event.getClassName() + "|" + event.getText().get(0));
            }
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
        return AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED;
    }
}
