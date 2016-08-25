package com.llxx.client.command;

import org.json.JSONException;
import org.json.JSONObject;

import com.llxx.command.Command;
import com.llxx.socket.service.Ll_AccessibilityService;

public abstract class CommandRun extends Command
{
    public abstract boolean runCommand(Ll_AccessibilityService accessibilityService);
    
    @Override
    public JSONObject getJsonObject()
    {
        JSONObject object = super.getJsonObject();
        if (object != null)
        {
            try
            {
                object.put("result", false);
                return object;
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
        }
        return null;
    }
}
