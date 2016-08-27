package com.llxx.client.command;

import org.json.JSONObject;

import com.llxx.socket.loger.Ll_Loger;
import com.llxx.socket.service.Ll_AccessibilityService;

import android.content.Context;
import android.view.accessibility.AccessibilityNodeInfo;

public class CommandQuery extends CommandRun
{
    public static final String TAG = "CommandQuery";
    public static final int TYPE_NONE = 0x00;
    public static final int TYPE_QUERY_LISTVIEW = 0x01;
    int type = 0;

    @Override
    public boolean runCommand(Ll_AccessibilityService accessibilityService)
    {
        if(type == TYPE_NONE)
        {
            return true;
        }
        else if(type == TYPE_QUERY_LISTVIEW)
        {
            AccessibilityNodeInfo info = accessibilityService.getRootInActiveWindow();
            if (accessibilityService.getRootInActiveWindow() == null)//取得当前激活窗体的根节点
            {
                Ll_Loger.e(TAG, "TYPE_QUERY_LISTVIEW "  + " but getRootInActiveWindow() is null");
                return false;
            }
            
            
            return true;
        }
        return false;
    }

    @Override
    public boolean prase()
    {
        super.prase();
        try
        {
            JSONObject object = new JSONObject(getMessage().getMessage());
            type = object.optInt("type");
            return true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public String action()
    {
        return "queryAccessibility";
    }

    @Override
    public String getResult(Context context)
    {
        JSONObject object = getJsonObject();
        if(object != null)
        {
            try
            {
                object.put("isToClient", true);
                return object.toString();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * @return the type
     */
    public int getType()
    {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(int type)
    {
        this.type = type;
    }

}
