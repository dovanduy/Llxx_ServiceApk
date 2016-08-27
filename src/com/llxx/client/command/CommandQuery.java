package com.llxx.client.command;

import org.json.JSONObject;

import com.llxx.socket.service.Ll_AccessibilityService;

import android.content.Context;

public class CommandQuery extends CommandRun
{

    int type = 0;

    @Override
    public boolean runCommand(Ll_AccessibilityService accessibilityService)
    {
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
