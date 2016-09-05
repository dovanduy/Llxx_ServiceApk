/**
 * 
 */
package com.llxx.socket.protocol.wrap;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.llxx.socket.protocol.Protocol;
import com.llxx.socket.service.Ll_SocketService;
import com.llxx.socket.wrap.Ll_ClientSocketWrap;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;

/**
 * @author 繁星
 * @describe 打开页面
 */
public class ProtocolQuery extends Protocol
{

    public static final String TOP_ACTIVITY = "top_activity";
    private String type = "";

    @Override
    public void doAction(Ll_ClientSocketWrap wrap, Ll_SocketService service)
    {
        switch (type)
        {
        case TOP_ACTIVITY:
            {
                try
                {
                    ActivityManager am = (ActivityManager) service.getSystemService(Context.ACTIVITY_SERVICE);
                    // get the info from the currently running task  
                    List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
                    ComponentName componentInfo = taskInfo.get(0).topActivity;
                    JSONObject result = new JSONObject();
                    result.put("package", componentInfo.getPackageName());
                    result.put("class", componentInfo.getClassName());
                    setCommandResult(result);
                    setRunOk(true);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
            break;

        default:
            break;
        }
    }

    @Override
    public boolean prase()
    {
        try
        {
            JSONObject object = new JSONObject(getMessage().getMessage());
            type = object.optString("type", "");
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
        return "query";
    }

    @Override
    public String getResult(Context context)
    {
        JSONObject object = getJsonObject();
        if (object != null)
        {
            try
            {
                object.put("type", type);
                return object.toString();
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * @return the type
     */
    public String getType()
    {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type)
    {
        this.type = type;
    }

}
