/**
 * 
 */
package com.llxx.socket.service;

import com.llxx.socket.loger.Ll_Loger;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.view.accessibility.AccessibilityEvent;

/**
 * @author 李万隆
 * @date   2016年8月19日
 * @qq 	461051353
 * @describe 类描述
 */
public class Ll_AccessibilityService extends AccessibilityService
{
    static final boolean DEBUG_OUTPUT = false;
    static final String TAG = "Ll_AccessibilityService";

    @Override
    protected void onServiceConnected()
    {
        super.onServiceConnected();
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
            eventText = "TYPE_VIEW_CLICKED";
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
            eventText = "TYPE_NOTIFICATION_STATE_CHANGED";
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

}
