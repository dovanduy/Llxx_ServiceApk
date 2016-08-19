/**
 * 
 */
package com.llxx.socket.service;

import com.llxx.socket.loger.Ll_Loger;
import com.llxx.utils.BinderUtils;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.app.Notification;
import android.os.Parcelable;
import android.os.RemoteException;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

/**
 * @author 李万隆
 * @date   2016年8月19日
 * @qq 	461051353
 * @describe 类描述
 */
public class Ll_AccessibilityService extends AccessibilityService
{
    static final boolean DEBUG_OUTPUT = true;
    static final String TAG = "Ll_AccessibilityService";
    BinderUtils mBinderUtils;

    @Override
    protected void onServiceConnected()
    {
        super.onServiceConnected();
        mBinderUtils = new BinderUtils(getApplicationContext());
        mBinderUtils.bind();
        AccessibilityServiceInfo accessibilityServiceInfo = new AccessibilityServiceInfo();
        // accessibilityServiceInfo.packageNames = PACKAGES;  
        accessibilityServiceInfo.eventTypes = AccessibilityEvent.TYPES_ALL_MASK;
        accessibilityServiceInfo.feedbackType = AccessibilityServiceInfo.FEEDBACK_SPOKEN;
        accessibilityServiceInfo.notificationTimeout = 1000;
        setServiceInfo(accessibilityServiceInfo);
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event)
    {
        // TODO Auto-generated method stub  
        int eventType = event.getEventType();
        String eventText = "";
        if (DEBUG_OUTPUT)
        {
            Ll_Loger.i(TAG, "==============Start====================");
        }
        switch (eventType)
        {
        case AccessibilityEvent.TYPE_VIEW_CLICKED:
            {
                eventText = "TYPE_VIEW_CLICKED";
                Ll_Loger.i(TAG, "==============Start====================");
                eventText = "TYPE_VIEW_CLICKED";

                AccessibilityNodeInfo noteInfo = event.getSource();

                // Ll_Loger.i(TAG, event.getBeforeText().toString());
                if (event.getClassName() != null)
                    Ll_Loger.i(TAG,
                            "class name: " + event.getClassName().toString());

                // Ll_Loger.i(TAG, event.getContentDescription().toString());
                // Ll_Loger.i(TAG, event.getText().toString());
                if (event.getPackageName() != null)
                    Ll_Loger.i(TAG, "package name: "
                            + event.getPackageName().toString());

                if (noteInfo != null)
                {
                    Ll_Loger.i(TAG, noteInfo.toString());
                    sendMessage("onClick|clicked|" + noteInfo.getPackageName()
                            + "|" + noteInfo.getClassName() + "|"
                            + noteInfo.getText());
                }
                Ll_Loger.i(TAG, "=============END=====================");
            }
            break;
        case AccessibilityEvent.TYPE_VIEW_FOCUSED:
            eventText = "TYPE_VIEW_FOCUSED";
            break;
        case AccessibilityEvent.TYPE_VIEW_LONG_CLICKED:
            eventText = "TYPE_VIEW_LONG_CLICKED";
            break;
        case AccessibilityEvent.TYPE_VIEW_SELECTED:
            eventText = "TYPE_VIEW_SELECTED";
            break;
        case AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED:
            eventText = "TYPE_VIEW_TEXT_CHANGED";
            break;
        case AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED:
            eventText = "TYPE_WINDOW_STATE_CHANGED";
            break;
        case AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED:
            {
                eventText = "TYPE_NOTIFICATION_STATE_CHANGED";
                if (event.getClassName() != null)
                    Ll_Loger.i(TAG,
                            "class name: " + event.getClassName().toString());

                // Ll_Loger.i(TAG, event.getContentDescription().toString());
                // Ll_Loger.i(TAG, event.getText().toString());
                if (event.getPackageName() != null)
                    Ll_Loger.i(TAG, "package name: "
                            + event.getPackageName().toString());
                try
                {
                    if (event.getClassName().toString()
                            .startsWith("android.widget.Toast"))
                    {
                        sendMessage(
                                "notification|toast|" + event.getPackageName()
                                        + "|" + event.getClassName() + "|"
                                        + event.getText().get(0));
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

                AccessibilityNodeInfo noteInfo = event.getSource();

                if (noteInfo != null)
                {
                    Ll_Loger.i(TAG, noteInfo.toString());
                    sendMessage("onClick|clicked|" + noteInfo.getPackageName()
                            + "|" + noteInfo.getClassName() + "|"
                            + noteInfo.getText());
                }
            }

            break;
        case AccessibilityEvent.TYPE_TOUCH_EXPLORATION_GESTURE_END:
            eventText = "TYPE_TOUCH_EXPLORATION_GESTURE_END";
            break;
        case AccessibilityEvent.TYPE_ANNOUNCEMENT:
            eventText = "TYPE_ANNOUNCEMENT";
            break;
        case AccessibilityEvent.TYPE_TOUCH_EXPLORATION_GESTURE_START:
            eventText = "TYPE_TOUCH_EXPLORATION_GESTURE_START";
            break;
        case AccessibilityEvent.TYPE_VIEW_HOVER_ENTER:
            eventText = "TYPE_VIEW_HOVER_ENTER";
            break;
        case AccessibilityEvent.TYPE_VIEW_HOVER_EXIT:
            eventText = "TYPE_VIEW_HOVER_EXIT";
            break;
        case AccessibilityEvent.TYPE_VIEW_SCROLLED:
            eventText = "TYPE_VIEW_SCROLLED";
            break;
        case AccessibilityEvent.TYPE_VIEW_TEXT_SELECTION_CHANGED:
            eventText = "TYPE_VIEW_TEXT_SELECTION_CHANGED";
            break;
        case AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED:
            eventText = "TYPE_WINDOW_CONTENT_CHANGED";
            break;
        }
        eventText = eventText + ":" + eventType;
        if (DEBUG_OUTPUT)

        {
            Ll_Loger.i(TAG, eventText);
            Ll_Loger.i(TAG, "=============END=====================");
        }
    }

    @Override
    public void onInterrupt()
    {

    }

    /**
     * 发送消息
     * @param message
     */
    public void sendMessage(String message)
    {
        if (mBinderUtils.getService() != null)
        {
            try
            {
                mBinderUtils.getService().sendMessage(message);
            }
            catch (Throwable e)
            {
                e.printStackTrace();
            }
        }
    }

}
