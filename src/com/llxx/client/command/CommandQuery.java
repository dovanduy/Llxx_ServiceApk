package com.llxx.client.command;

import org.json.JSONObject;

import com.llxx.nodefinder.AccessibilityNodeInfoToJson;
import com.llxx.nodefinder.UiSelector;
import com.llxx.socket.loger.Ll_Loger;
import com.llxx.socket.service.Ll_AccessibilityService;

import android.content.Context;
import android.util.SparseArray;
import android.view.accessibility.AccessibilityNodeInfo;

public class CommandQuery extends CommandRun
{
    public static final String TAG = "CommandQuery";

    private SparseArray<Object> mSelectorAttributes = new SparseArray<Object>();

    int type = 0;

    @Override
    public boolean runCommand(Ll_AccessibilityService accessibilityService)
    {
        UiSelector selector = null;
        selector = new UiSelector(mSelectorAttributes);
        AccessibilityNodeInfo info = accessibilityService.getQueryController().findAccessibilityNodeInfo(selector);
        if (info != null)
        {
            try
            {
                JSONObject nodes = new JSONObject();
                JSONObject result = AccessibilityNodeInfoToJson.getJson(info, false);
                nodes.put("isfind", true);
                nodes.put("node", result);
                setCommandResult(nodes);
                return true;
            }
            catch (Throwable e)
            {
                e.printStackTrace();
            }
        }
        else
        {
            try
            {
                JSONObject nodes = new JSONObject();
                nodes.put("isfind", false);
                setReason("node not find");
                return true;
            }
            catch (Throwable e)
            {
                e.printStackTrace();
            }
        }
        Ll_Loger.e(TAG, "findAccessibilityNodeInfo is null");
        return false;
    }

    @Override
    public boolean prase()
    {
        boolean isSucess = super.prase();
        if (isSucess)
        {
            JSONObject select = getCommand().getParams("select", new JSONObject());
            for (int i = 0; i < UiSelector.SELECTOR_MAX; i++)
            {
                if (select.has(String.valueOf(i)))
                {
                    String text = select.optString(String.valueOf(i), "");
                    mSelectorAttributes.put(i, text);
                    Ll_Loger.i(TAG, "mSelectorAttributes put " + i + ", " + text);
                }
            }
        }
        return false;
    }

    @Override
    public String action()
    {
        return "queryAccessibility";
    }

    @Override
    public String getResult(Context context)
    {
        JSONObject object = getJsonObject();
        if (object != null)
        {
            try
            {
                object.put("isToClient", true);
                return object.toString();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * @return the type
     */
    public int getType()
    {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(int type)
    {
        this.type = type;
    }

}
