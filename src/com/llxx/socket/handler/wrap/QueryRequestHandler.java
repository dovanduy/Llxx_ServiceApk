/**
 * 
 */
package com.llxx.socket.handler.wrap;

import java.io.File;
import java.util.Collections;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.ihongqiqu.util.AppUtils;
import com.ihongqiqu.util.DisplayUtils;
import com.llxx.socket.handler.RequestHandler;
import com.llxx.socket.service.Ll_SocketService;
import com.llxx.socket.wrap.Ll_ClientSocketWrap;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import cn.trinea.android.common.util.ImageUtils;

/**
 * @author 繁星
 * @describe 打开页面
 */
public class QueryRequestHandler extends RequestHandler
{

    /**屏幕大小*/
    public static final String SCREENSIZE = "screensize";

    /**指定包名所有的Acitivity*/
    public static final String ALLACTIVITY = "allactivity";

    /**指定包名所有的Service*/
    public static final String ALLSERVICE = "allservice";

    /**所有APK的信息*/
    public static final String ALLAPPINFO = "allappinfo";

    @Override
    public void doAction(Ll_ClientSocketWrap wrap, Ll_SocketService service)
    {
        String type = getCommand().getParams("type", "");
        switch (type)
        {
        case SCREENSIZE:
            {
                try
                {
                    getResultObject().putParams("width", DisplayUtils.getScreenW(service));
                    getResultObject().putParams("height", DisplayUtils.getScreenH(service));
                    getResultObject().setSucess(true);
                    return;
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
                    String packagename = getCommand().getParams("packagename", "");
                    if (!TextUtils.isEmpty(packagename))
                    {
                        // Retrieve all services that can match the given intent
                        PackageManager pm = service.getPackageManager();
                        Intent intent = new Intent();
                        intent.setPackage(packagename);
                        PackageInfo infos = pm.getPackageInfo(packagename,
                                android.content.pm.PackageManager.GET_ACTIVITIES);
                        JSONArray activitys = new JSONArray();
                        getResultObject().putParams("activitys", activitys);
                        for (ActivityInfo info : infos.activities)
                        {
                            JSONObject activity = new JSONObject();
                            activity.put("name", info.name);
                            activity.put("parentActivityName", info.parentActivityName);
                            activity.put("targetActivity", info.targetActivity);
                            activity.put("permission", info.permission);
                            activitys.put(activity);
                        }
                        getResultObject().setSucess(true);
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
            break;
        case ALLSERVICE:
            {
                try
                {
                    String packagename = getCommand().getParams("packagename", "");
                    if (!TextUtils.isEmpty(packagename))
                    {
                        // Retrieve all services that can match the given intent
                        PackageManager pm = service.getPackageManager();
                        Intent intent = new Intent();
                        intent.setPackage(packagename);
                        PackageInfo infos = pm.getPackageInfo(packagename,
                                android.content.pm.PackageManager.GET_SERVICES);
                        JSONObject result = new JSONObject();
                        JSONArray activitys = new JSONArray();
                        getResultObject().putParams("services", activitys);
                        for (ServiceInfo info : infos.services)
                        {
                            JSONObject activity = new JSONObject();
                            activity.put("name", info.name);
                            activity.put("exported", info.exported);
                            activity.put("permission", info.permission);
                            activity.put("isRunning", AppUtils.isServiceRunning(service, info.name));
                            activitys.put(activity);
                        }
                        getResultObject().setSucess(true);
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
                    String dir = getCommand().getParams("dir", "");
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

                        JSONArray activitys = new JSONArray();
                        getResultObject().putParams("packages", activitys);
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
                        getResultObject().setSucess(true);
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
    public String action()
    {
        return "query";
    }
}
