/**
 * 
 */
package com.llxx.utils;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.content.pm.Signature;

/**
 * @author 李万隆
 * @date   2015年5月13日
 */
public class AppUtils
{
    /**
     * 打开指定包名的App
     * @param openPackageName
     * @param context
     */
    public static final void openApp(String openPackageName, Activity context)
    {
        openApp(openPackageName, context, null);
    }

    public static final void openApp(String openPackageName, Activity context, Map<String, String> values)
    {
        if (openPackageName == null)
            return;
        PackageManager pm = context.getPackageManager();
        try
        {
            PackageInfo pi = context.getPackageManager().getPackageInfo(openPackageName, 0);

            Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);
            resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER);
            resolveIntent.setPackage(pi.packageName);
            List<ResolveInfo> apps = pm.queryIntentActivities(resolveIntent, 0);
            ResolveInfo ri = apps.iterator().next();
            if (ri != null)
            {
                String packageName = ri.activityInfo.packageName;
                String className = ri.activityInfo.name;
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_LAUNCHER);
                ComponentName cn = new ComponentName(packageName, className);
                intent.setComponent(cn);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                if (values != null)
                {
                    for (String key : values.keySet())
                    {
                        intent.putExtra(key, values.get(key));
                    }
                }
                context.startActivity(intent);
            }
        }
        catch (PackageManager.NameNotFoundException localNameNotFoundException)
        {
            localNameNotFoundException.printStackTrace();
        }
    }

    /**
     * 打开APP
     * @param openPackageName
     * @param context
     * @throws NameNotFoundException 
     */
    public static final void openApp(String openPackageName, Context context) throws NameNotFoundException
    {
        openApp(openPackageName, context, null);
    }

    /**
     * 打开指定包名的App
     * @param openPackageName
     * @param context
     * @throws NameNotFoundException 
     */
    public static final void openApp(String openPackageName, Context context, Map<String, String> values)
            throws NameNotFoundException
    {
        if (openPackageName == null)
            return;
        PackageManager pm = context.getPackageManager();
        PackageInfo pi = context.getPackageManager().getPackageInfo(openPackageName, 0);

        Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);
        resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        resolveIntent.setPackage(pi.packageName);
        List<ResolveInfo> apps = pm.queryIntentActivities(resolveIntent, 0);
        ResolveInfo ri = apps.iterator().next();
        if (ri != null)
        {
            String packageName = ri.activityInfo.packageName;
            String className = ri.activityInfo.name;
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            ComponentName cn = new ComponentName(packageName, className);
            intent.setComponent(cn);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
            if (values != null)
            {
                for (String key : values.keySet())
                {
                    intent.putExtra(key, values.get(key));
                }
            }
            context.startActivity(intent);
        }
    }

    /**
     * 是否安装了指定的程序，如果安装了的话则返回true，否则返回false
     * @param context
     * @param packageName
     * @return
     */
    public static final boolean isInstallApp(Context context, String packageName)
    {
        final PackageManager packageManager = context.getPackageManager();
        // 获取所有已安装程序的包信息
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        for (int i = 0; i < pinfo.size(); i++)
        {
            if (pinfo.get(i).packageName.equalsIgnoreCase(packageName))
                return true;
        }
        return false;
    }

    /**
     * 判断系统中某个Activity是否存在
     * @param activity
     * @param packageName
     * @param className
     * @return
     */
    public static final boolean isExsitAcivity(Activity activity, String packageName, String className)
    {
        Intent intent = new Intent();
        intent.setClassName(packageName, className);

        if (activity.getPackageManager().resolveActivity(intent, 0) == null)
        {
            return false;
        }
        return true;
    }

    /**
     * 杀掉制定的package进程
     * @param context
     * @param packageName
     */
    public static final void killPackage(Context context, String packageName)
    {
        try
        {
            ActivityManager mAm = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            mAm.killBackgroundProcesses(packageName);
        }
        catch (Exception e)
        {
        }
    }

    /**
     * 强行停止APP
     * @param context
     * @param packageName
     */
    public static final void force_stopPackage(Context context, String packageName)
    {
        try
        {
            ActivityManager mAm = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            Method method = Class.forName("android.app.ActivityManager").getMethod("forceStopPackage", String.class);
            method.invoke(mAm, packageName);
        }
        catch (Exception e)
        {
        }
    }

    /**
     * 获取系统的VersionName
     * @param context
     * @return
     */
    public static String getVersionName(Context context)
    {
        String versionCode = "";
        try
        {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            versionCode = info.versionName + "";
        }
        catch (NameNotFoundException e)
        {
            e.printStackTrace();
        }
        return versionCode;

    }

    /**
     * 获取程序的签名
     * @param context
     * @param packegeName
     * @return
     */
    public static final String getSignature(Context context, String packegeName)
    {
        try
        {
            PackageManager manager = context.getPackageManager();
            /** 通过包管理器获得指定包名包含签名的包信息 **/
            PackageInfo packageInfo = manager.getPackageInfo(packegeName, PackageManager.GET_SIGNATURES);
            /******* 通过返回的包信息获得签名数组 *******/
            Signature[] signatures = packageInfo.signatures;
            StringBuilder builder = new StringBuilder();
            /******* 循环遍历签名数组拼接应用签名 *******/
            for (Signature signature : signatures)
            {
                builder.append(signature.toCharsString());
            }
            /************** 得到应用签名 **************/
            String signature = builder.toString();
            return signature;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 是否共享系统UID
     * @return
     */
    public static boolean isSharedUserIdSystem(Context context, String packagename)
    {
        PackageManager manager = context.getPackageManager();
        boolean IS_SHARED_USER_ID = false;
        try
        {
            PackageInfo pi = manager.getPackageInfo(packagename, 0);
            if ("android.uid.system".equals(pi.sharedUserId))
            {
                IS_SHARED_USER_ID = true;
            }
        }
        catch (NameNotFoundException e)
        {
            e.printStackTrace();
        }
        return IS_SHARED_USER_ID;
    }
}
