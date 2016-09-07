/**
 * 
 */
package com.llxx.socket.action;

import com.llxx.socket.service.ISocketService;

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
    /**
     * 发送给客户端的结果
     */
    String result = "";

    public AccessibilityAction()
    {

    }

    /**
     * 处理事件
     * @param event
     * @param nodeInfo Node信息，有可能返回为空，需要添加判断
     * @return
     */
    protected abstract boolean processEvent(Context context, AccessibilityEvent event,
            AccessibilityNodeInfo nodeInfo);

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
     * 获取事件对应的Actoin
     * @return
     */
    public abstract int getEventType();
}
