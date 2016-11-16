package com.llxx.client.command;

import org.json.JSONArray;
import org.json.JSONObject;

import com.llxx.nodefinder.AccessibilityNodeInfoToJson;
import com.llxx.nodefinder.UiSelectParse;
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

    UiSelector mSelector = null;
    int mType = 0;
    int mActoinCode = -1;
    Bundle mActionParams = null;

    @Override
    public boolean runCommand(Ll_AccessibilityService accessibilityService)
    {
        AccessibilityNodeInfo info = accessibilityService.getQueryController()
                .findAccessibilityNodeInfo(mSelector);
        if (info != null)
        {
            try
            {
                JSONObject nodes = new JSONObject();
                JSONObject result = AccessibilityNodeInfoToJson.getJson(info,
                        false);
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
            Bundle arguments = new Bundle();
            arguments.putCharSequence(
                    AccessibilityNodeInfo.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE,
                    "android");
            node.performAction(AccessibilityNodeInfo.ACTION_SET_TEXT,
                    arguments);
            Ll_Loger.e(TAG, "performAction do action mActoinCode = "
                    + mActoinCode + ", mActionParams = " + mActionParams);
            //node.performAction(AccessibilityNodeInfo.ACTION_SET_TEXT, )
            //node.performAction(mActoinCode, mActionParams);
        }
        return isSucess;
    }

    @Override
    public boolean prase()
    {
        boolean isSucess = super.prase();
        if (isSucess)
        {
            JSONObject select = getCommand().getParams("select",
                    new JSONObject());
            mSelector = UiSelectParse.parse(select);
            mActoinCode = getCommand().getParams("action", -1);
            JSONArray _actionParams = getCommand().getParams("actionParams",
                    new JSONArray());

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
