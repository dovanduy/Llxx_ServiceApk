package com.llxx.client.command;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.llxx.socket.loger.Ll_Loger;
import com.llxx.socket.service.Ll_AccessibilityService;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.view.accessibility.AccessibilityNodeInfo;

public class CommandClick extends CommandRun
{
    public static final String TAG = "CommandClick";

    public static final int CLICK_TYPE_BY_NONE = 0x00;
    public static final int CLICK_TYPE_BY_ID = 0x01;
    public static final int CLICK_TYPE_BY_NAME = 0x02;

    /**
     * 根据ID + 索引的方式点击
     */
    public static final int CLICK_TYPE_BY_ID_INDEX = 0x03;

    /**
     * Name + 索引的方式点击
     */
    public static final int CLICK_TYPE_BY_NAME_INDEX = 0x04;

    int clicktype = CLICK_TYPE_BY_NONE;
    int index = 0;
    String clickName = "";

    @Override
    public boolean prase()
    {
        try
        {
            JSONObject object = new JSONObject(getMessage().getMessage());
            clicktype = object.optInt("clicktype");
            if (clicktype == CLICK_TYPE_BY_ID_INDEX || clicktype == CLICK_TYPE_BY_NAME_INDEX)
            {
                index = object.optInt("index", -1);
            }
            clickName = object.optString("name", "");
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
        return "preformClick";
    }

    @Override
    public String getResult(Context context)
    {
        return "";
    }

    @Override
    public JSONObject getJsonObject()
    {
        JSONObject object = super.getJsonObject();
        if (object != null)
        {
            try
            {
                object.put("clicktype", clicktype);
                if (clicktype == CLICK_TYPE_BY_ID_INDEX || clicktype == CLICK_TYPE_BY_NAME_INDEX)
                {
                    object.put("index", index);
                }
                object.put("name", clickName);
                return object;
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }

        }
        return object;
    }

    /**
     * @return the clicktype
     */
    public int getClicktype()
    {
        return clicktype;
    }

    /**
     * @param clicktype the clicktype to set
     */
    public void setClicktype(int clicktype)
    {
        this.clicktype = clicktype;
    }

    /**
     * 通过id点击
     * @param idName
     */
    public CommandClick performClickById(String idName)
    {
        clickName = idName;
        setClicktype(CLICK_TYPE_BY_ID);
        return this;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    public boolean runCommand(Ll_AccessibilityService accessibilityService)
    {
        Ll_Loger.d(TAG, "click " + clickName);
        AccessibilityNodeInfo info = accessibilityService.getRootInActiveWindow();
        if (accessibilityService.getRootInActiveWindow() == null)//取得当前激活窗体的根节点
        {
            Ll_Loger.e(TAG, "click " + clickName + " but getRootInActiveWindow() is null");
            return false;
        }

        boolean result = false;
        //通过文字找到当前的节点
        List<AccessibilityNodeInfo> nodes = null;
        if (getClicktype() == CLICK_TYPE_BY_ID)
        {
            nodes = info.findAccessibilityNodeInfosByViewId(clickName);
        }
        else if (getClicktype() == CLICK_TYPE_BY_NAME)
        {
            nodes = info.findAccessibilityNodeInfosByText(clickName);
        }

        Ll_Loger.e(TAG, "click " + clickName + " nodes size is " + nodes.size());
        for (int i = 0; nodes != null && i < nodes.size(); i++)
        {
            AccessibilityNodeInfo node = nodes.get(i);
            // 执行按钮点击行为
            // node.getClassName().equals("android.widget.Button")
            try
            {
                if (node.isEnabled())
                {
                    result = node.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                }
            }
            catch (Throwable e)
            {
                e.printStackTrace();
            }
        }
        return result;
    }

}
