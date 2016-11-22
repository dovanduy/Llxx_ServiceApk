/**
 * 
 */
package com.llxx.socket.action.manager;

import java.util.ArrayList;

import com.llxx.socket.action.AccessibilityAction;
import com.llxx.socket.action.AccessibilityActivityAction;
import com.llxx.socket.action.AccessibilityClickAction;
import com.llxx.socket.action.AccessibilityDialogAction;
import com.llxx.socket.action.AccessibilityNotifyAction;
import com.llxx.socket.action.AccessibilityScrollAction;
import com.llxx.socket.action.AccessibilityTextChangeAction;
import com.llxx.socket.action.AccessibilityWinchageAction;

/**
 * @author fanxin, eachen
 * @describe 类描述
 */
public class AccessibilityActionConfig
{
    public static final ArrayList<AccessibilityAction> ACTIONS = new ArrayList<AccessibilityAction>();

    static
    {

        ACTIONS.add(new AccessibilityClickAction());
        ACTIONS.add(new AccessibilityNotifyAction());
        ACTIONS.add(new AccessibilityActivityAction());
        ACTIONS.add(new AccessibilityDialogAction());
        ACTIONS.add(new AccessibilityWinchageAction());

        ACTIONS.add(new AccessibilityScrollAction());

        ACTIONS.add(new AccessibilityTextChangeAction());

    }
}
