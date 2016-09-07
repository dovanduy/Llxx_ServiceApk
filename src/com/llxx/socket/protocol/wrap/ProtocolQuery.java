/**
 * 
 */
package com.llxx.socket.protocol.wrap;

import java.io.File;
import java.util.Collections;
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
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import cn.trinea.android.common.util.ImageUtils;

/**
 * @author 繁星
 * @describe 打开页面
 */
public class ProtocolQuery extends Protocol
{

    /**当前运行的顶部的Acitivity*/
    public static final String TOP_ACTIVITY = "top_activity";

    /**屏幕大小*/
    public static final String SCREENSIZE = "screensize";

    /**指定包名所有的Acitivity*/
    public static final String ALLACTIVITY = "allactivity";

    /**所有APK的信息*/
    public static final String ALLAPPINFO = "allappinfo";

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
        case ALLAPPINFO:
            {
                try
                {
                    JSONObject object = new JSONObject(getMessage().getMessage());
                    String dir = object.optJSONObject(PARAMS).optString("dir");
                    if (!TextUtils.isEmpty(dir))
                    {
                        
                        PackageManager pm = service.getPackageManager(); //获得PackageManager对象
                        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
                        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
                        // 通过查询，获得所有ResolveInfo对象.
                        List<ResolveInfo> resolveInfos = pm.queryIntentActivities(mainIntent,
                                PackageManager.MATCH_DEFAULT_ONLY);
                        // 调用系统排序 ， 根据name排序
                        // 该排序很重要，否则只能显示系统应用，而不能列出第三方应用程序
                        Collections.sort(resolveInfos, new ResolveInfo.DisplayNameComparator(pm));
                        
                        JSONObject result = new JSONObject();
                        JSONArray activitys = new JSONArray();
                        result.put("packages", activitys);
                        File filedir = new File(dir);
                        if (!filedir.exists())
                        {
                            filedir.mkdirs();
                        }
                        for (ResolveInfo reInfo : resolveInfos)
                        {
                            String activityName = reInfo.activityInfo.name; // 获得该应用程序的启动Activity的name
                            String pkgName = reInfo.activityInfo.packageName; // 获得应用程序的包名
                            String appLabel = (String) reInfo.loadLabel(pm); // 获得应用程序的Label
                            Drawable icon = reInfo.loadIcon(pm); // 获得应用程序图标

                            File appdir = new File(dir, pkgName);
                            if (!appdir.exists())
                            {
                                appdir.mkdirs();
                            }

                            File iconpath = new File(appdir, "icon.png");
                            boolean isSucess = ImageUtils.drawableToFile(icon, iconpath);

                            JSONObject activity = new JSONObject();
                            activity.put("activityName", activityName);
                            activity.put("package", pkgName);
                            activity.put("name", appLabel);
                            activity.put("iconpath", iconpath.getAbsolutePath());
                            activity.put("iconsucess", isSucess);
                            activitys.put(activity);
                        }
                        setCommandResult(result);
                        setRunOk(true);
                    }
                }
                catch (Throwable e)
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
