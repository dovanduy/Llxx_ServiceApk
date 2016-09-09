/**
 * 
 */
package com.llxx.socket.action;

import java.util.ArrayList;

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
    }
}
