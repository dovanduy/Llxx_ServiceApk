package com.llxx.client.action;

import org.json.JSONObject;

import com.llxx.command.Command;

import android.content.Context;

/**
 * 
 * @author 繁星
 * @describe 连接到服务端，为服务端提供控件的点击等事件
 *
 */
public class ActionConnectService extends Command
{

    public ActionConnectService()
    {
        setAction(action());
    }

    @Override
    public boolean prase()
    {
        return false;
    }

    @Override
    public String action()
    {
        return "accessibility_connect_service";
    }

    @Override
    public String getResult(Context context)
    {
        JSONObject msg = getJsonObject();
        if (msg != null)
        {
            return msg.toString();
        }
        return "";
    }

}
