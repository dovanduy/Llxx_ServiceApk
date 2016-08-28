package com.llxx.client.command;

import com.llxx.socket.service.Ll_AccessibilityService;

import android.content.Context;

public class CommandEvent extends CommandRun
{

    @Override
    public boolean runCommand(Ll_AccessibilityService accessibilityService)
    {
        return false;
    }

    @Override
    public String action()
    {
        return "preformEvent";
    }

    @Override
    public String getResult(Context context)
    {
        return null;
    }
    
}
