package com.llxx.client.command;

import org.json.JSONArray;

import com.llxx.socket.service.Ll_AccessibilityService;

public class CommandRegPackage extends CommandRun
{
    public static final String TAG = "CommandRegPackage";

    @Override
    public boolean runCommand(Ll_AccessibilityService accessibilityService)
    {
        {
            try
            {
                JSONArray array = new JSONArray();
                array = getCommand().getParams("packages", array);
                String[] stirngs = new String[array.length()];
                for (int i = 0; i < stirngs.length; i++)
                {
                    stirngs[i] = array.getString(i);
                }
                accessibilityService.setPackage(stirngs);
                getResult().setSucess(true);
                return true;
            }
            catch (Throwable e)
            {
                e.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public String action()
    {
        return "regPackage";
    }

}
