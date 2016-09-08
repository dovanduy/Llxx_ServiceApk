/**
 * 
 */
package com.llxx.socket.action;

import org.json.JSONException;

import com.llxx.nodefinder.AccessibilityNodeInfoToJson;
import com.llxx.socket.loger.Ll_Loger;
import com.llxx.socket.protocol.wrap.ProtocolActivity;
import com.llxx.socket.protocol.wrap.ProtocolClick;
import com.llxx.socket.protocol.wrap.ProtocolConstants;
import com.llxx.socket.protocol.wrap.ProtocolNotify;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

/**
 * @author fanxin, eachen
 * @date   2016年8月19日
 * @qq 	461051353
 * @describe 获取点击事件
 */
public class AccessibilityWinStateChangeAction extends AccessibilityAction
{
    public static final String TAG = "AccessibilityClickAction";

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    protected boolean processEvent(Context context, AccessibilityEvent event,
            AccessibilityNodeInfo nodeInfo)
    {
        setResult("");
        if (event.getClassName() != null)
            Ll_Loger.i(TAG, "class name: " + event.getClassName().toString());

        if (event.getPackageName() != null)
            Ll_Loger.i(TAG,
                    "package name: " + event.getPackageName().toString());

        ProtocolClick activity = new ProtocolClick();
        activity.setClassname(event.getClassName().toString());
        activity.setPackageName(event.getPackageName().toString());

        if (nodeInfo != null)
        {
            activity.setType(activity.getClassname());
            activity.setTitle(nodeInfo.getText() != null
                    ? nodeInfo.getText().toString() : "");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2)
            {
                String id = nodeInfo.getViewIdResourceName();
                activity.setIdname(TextUtils.isEmpty(id) ? "" : id);
            }
            Ll_Loger.i(TAG, "nodeInfo.getViewIdResourceName() "
                    + nodeInfo.getViewIdResourceName());
            try
            {
                activity.setCommandResult(AccessibilityNodeInfoToJson.getJson(nodeInfo));
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
            setResult(activity.getResult(context));
            // Ll_Loger.i(TAG, nodeInfo.toString());
            return true;
        }
        else if (event.getText() != null && event.getText().size() > 0)
        {
            activity.setType(activity.getClassname());
            activity.setTitle(event.getText().get(0).toString());
            setResult(activity.getResult(context));
            // Ll_Loger.i(TAG, "nodeInfo is null" +  event.getText());
            return true;
        }
        return false;
    }

    @Override
    public int getEventType()
    {
        return AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED;
    }
}
