/**
 * 
 */
package com.llxx.socket.action.manager;

import java.util.ArrayList;

import com.llxx.socket.action.AActionNofity;
import com.llxx.socket.action.ActivityNofity;
import com.llxx.socket.action.ClickNofity;
import com.llxx.socket.action.DialogNofity;
import com.llxx.socket.action.NotifyActionNofity;
import com.llxx.socket.action.ScrollActionNofity;
import com.llxx.socket.action.TextChangeNofity;
import com.llxx.socket.action.WindowsChageNofity;

/**
 * @author fanxin, eachen
 * @describe 类描述
 */
public class AccessibilityActionConfig
{
    public static final ArrayList<AActionNofity> ACTIONS = new ArrayList<AActionNofity>();

    static
    {

        ACTIONS.add(new ClickNofity());
        ACTIONS.add(new NotifyActionNofity());
        ACTIONS.add(new ActivityNofity());
        ACTIONS.add(new DialogNofity());
        ACTIONS.add(new WindowsChageNofity());

        ACTIONS.add(new ScrollActionNofity());

        ACTIONS.add(new TextChangeNofity());

    }
}
