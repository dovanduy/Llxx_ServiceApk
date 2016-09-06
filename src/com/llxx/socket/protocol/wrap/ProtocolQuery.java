/**
 * 
 */
package com.llxx.socket.protocol.wrap;

import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.ihongqiqu.util.DisplayUtils;
import com.llxx.socket.protocol.Protocol;
import com.llxx.socket.service.Ll_SocketService;
import com.llxx.socket.wrap.Ll_ClientSocketWrap;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.text.TextUtils;

/**
 * @author 繁星
 * @describe 打开页面
 */
public class ProtocolQuery extends Protocol
{

    public static final String TOP_ACTIVITY = "top_activity";
    public static final String SCREENSIZE = "screensize";
    public static final String ALLACTIVITY = "allactivity";
    private String type = "";
    private String packagename = "";

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
        case SCREENSIZE:
            {
                try
                {
                    JSONObject result = new JSONObject();
                    result.put("width", DisplayUtils.getScreenW(service));
                    result.put("height", DisplayUtils.getScreenH(service));
                    setCommandResult(result);
                    setRunOk(true);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
            break;
        case ALLACTIVITY:
            {
                try
                {
                    JSONObject object = new JSONObject(getMessage().getMessage());
                    packagename = object.optJSONObject(PARAMS).optString("package");
                    if (!TextUtils.isEmpty(packagename))
                    {
                        // Retrieve all services that can match the given intent
                        PackageManager pm = service.getPackageManager();
                        Intent intent = new Intent();
                        intent.setPackage(packagename);
                        PackageInfo infos = pm.getPackageInfo(packagename,
                                android.content.pm.PackageManager.GET_ACTIVITIES);
                        JSONObject result = new JSONObject();
                        JSONArray activitys = new JSONArray();
                        result.put("activitys", activitys);
                        for (ActivityInfo info : infos.activities)
                        {
                            JSONObject activity = new JSONObject();
                            activity.put("name", info.name);
                            activity.put("parentActivityName", info.parentActivityName);
                            activity.put("targetActivity", info.targetActivity);
                            activity.put("permission", info.permission);
                            activitys.put(activity);
                        }
                        setCommandResult(result);
                        setRunOk(true);
                    }
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
