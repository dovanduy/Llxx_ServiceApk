package com.llxx.client.command;

import org.json.JSONArray;
import org.json.JSONObject;

import com.llxx.nodefinder.AccessibilityNodeInfoToJson;
import com.llxx.nodefinder.UiSelector;
import com.llxx.socket.loger.Ll_Loger;
import com.llxx.socket.service.Ll_AccessibilityService;

import android.content.Context;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.accessibility.AccessibilityNodeInfo;

public class CommandSelectAction extends CommandRun
{
    public static final String TAG = "CommandSelectAction";

    private SparseArray<Object> mSelectorAttributes = new SparseArray<Object>();

    int mType = 0;
    int mActoinCode = -1;
    Bundle mActionParams = null;

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

                setRunOk(performAction(info));
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
                setRunOk(false);
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

    /**
     * 执行操作
     * @param node
     * @return
     */
    private boolean performAction(AccessibilityNodeInfo node)
    {
        boolean isSucess = false;
        if (mActionParams == null)
        {
            isSucess = node.performAction(mActoinCode);
        }
        else
        {
            node.performAction(mActoinCode, mActionParams);
        }
        return isSucess;
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
            mActoinCode = getCommand().getParams("action", -1);
            JSONArray _actionParams = getCommand().getParams("actionParams", new JSONArray());

            if (_actionParams != null)
            {
                Bundle arguments = new Bundle();
                for (int i = 0; i < _actionParams.length(); i++)
                {
                    putArguments(arguments, _actionParams.optJSONObject(i));
                }
                mActionParams = arguments;
            }
            return true;
        }
        return false;
    }

    /**
     * 解析传入的Action
     * @param arguments
     * @param actionParamsItem
     */
    void putArguments(Bundle arguments, JSONObject actionParamsItem)
    {
        String actionType = actionParamsItem.optString("type");
        String key = actionParamsItem.optString("key");

        switch (actionType)
        {
        case "putBoolean":
            arguments.putBoolean(key, actionParamsItem.optBoolean("value"));
            break;

        case "putInt":
            arguments.putInt(key, actionParamsItem.optInt("value"));
            break;

        case "putLong":
            arguments.putLong(key, actionParamsItem.optLong("value"));
            break;

        case "putDouble":
            arguments.putDouble(key, actionParamsItem.optDouble("value"));
            break;

        case "putString":
            arguments.putString(key, actionParamsItem.optString("value"));
            break;

        case "putCharSequence":
            arguments.putCharSequence(key, actionParamsItem.optString("value"));
            break;
        default:
            break;
        }

    }

    @Override
    public String action()
    {
        return "uiSecletAction";
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
        return mType;
    }

    /**
     * @param type the type to set
     */
    public void setType(int type)
    {
        this.mType = type;
    }

}
