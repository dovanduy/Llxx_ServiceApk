package com.llxx.client.command;

import org.json.JSONObject;

import com.llxx.command.Command;
import com.llxx.socket.service.Ll_AccessibilityService;

public abstract class CommandRun extends Command
{
    public abstract boolean runCommand(Ll_AccessibilityService accessibilityService);

    @Override
    public boolean prase()
    {
        super.prase();
        try
        {
            JSONObject object = new JSONObject(getMessage().getMessage());
            setClientHash(object.optInt("clientHash", 0));
            return true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return false;
    }
}
