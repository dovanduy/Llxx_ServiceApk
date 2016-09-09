/**
 * 
 */
package com.llxx.socket.action;

import com.llxx.nodefinder.AccessibilityNodeInfoToJson;
import com.llxx.socket.action.result.AccessibilityResult;

import android.content.Context;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

/**
 * @author fanxin, eachen
 * @date   2016年8月19日
 * @qq 	461051353
 * @describe 辅助服务对应的Action
 */
public abstract class AccessibilityAction
{
    static final String TAG = "AccessibilityAction";

    /**
     * 发送给客户端的结果
     */
    String result = "";
    AccessibilityResult mAccessibilityResult;

    public AccessibilityAction()
    {
        mAccessibilityResult = new AccessibilityResult(getActoin());
    }

    /**
     * 预处理命令
     * @param context
     * @param event
     * @param nodeInfo
     * @return
     */
    protected boolean preProcessEvent(Context context, AccessibilityEvent event, AccessibilityNodeInfo nodeInfo)
    {
        setResult("");
        try
        {
            mAccessibilityResult.setPackageName(event.getPackageName().toString());
            mAccessibilityResult.setClassname(event.getClassName().toString());
            AccessibilityNodeInfo info = event.getSource();
            if (info != null)
            {
                mAccessibilityResult.putParams("node", AccessibilityNodeInfoToJson.getJson(info));
            }
            mAccessibilityResult.setSucess(true);
            return true;
        }
        catch (Throwable e)
        {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 处理事件
     * @param event
     * @param nodeInfo Node信息，有可能返回为空，需要添加判断
     * @return
     */
    protected boolean processEvent(Context context, AccessibilityEvent event, AccessibilityNodeInfo nodeInfo)
    {
        try
        {
            setResult(getAccessibilityResult().getResult());
            return true;
        }
        catch (Throwable e)
        {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 
     * @return
     */
    protected abstract String getActoin();

    /**
     * @return 返回给服务的结果
     */
    public String getResult()
    {
        return result;
    }

    /**
     * 设置返回给服务的结果
     * @param result 
     */
    public void setResult(String result)
    {
        this.result = result;
    }

    /**
     * @return the mAccessibilityResult
     */
    public AccessibilityResult getAccessibilityResult()
    {
        return mAccessibilityResult;
    }

    /**
     * 获取事件对应的Actoin
     * @return
     */
    public abstract int getEventType();
}
