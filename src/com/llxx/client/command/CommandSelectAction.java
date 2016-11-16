package com.llxx.client.command;

import org.json.JSONArray;
import org.json.JSONObject;

import com.llxx.nodefinder.AccessibilityNodeInfoToJson;
import com.llxx.nodefinder.UiSelectParse;
import com.llxx.nodefinder.UiSelector;
import com.llxx.socket.Llxx_Application;
import com.llxx.socket.loger.Ll_Loger;
import com.llxx.socket.service.Ll_AccessibilityService;
import android.annotation.TargetApi;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Build;
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
        AccessibilityNodeInfo info = accessibilityService.getQueryController().findAccessibilityNodeInfo(mSelector);
        if (info != null)
        {
            try
            {
                JSONObject nodes = new JSONObject();
                JSONObject result = AccessibilityNodeInfoToJson.getJson(info, false);
                nodes.put("isfind", true);
                nodes.put("node", result);

                boolean isActionOk = performAction(accessibilityService, info);
                setCommandResult(nodes);
                setRunOk(isActionOk);
                return isActionOk;
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
                setReason(getDescribe() + " node not find");
                return false;
            }
            catch (Throwable e)
            {
                e.printStackTrace();
            }
        }
        setReason("find node and action fali");
        return false;
    }

    /**
     * 执行操作
     * @param node
     * @return
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    private boolean performAction(Ll_AccessibilityService accessibilityService, AccessibilityNodeInfo node)
    {
        boolean isSucess = false;
        if (mActionParams == null)
        {
            isSucess = node.performAction(mActoinCode);
            Ll_Loger.e(TAG,
                    "performAction do action mActoinCode = " + mActoinCode + ", mActionParams = " + mActionParams);
        }
        else
        {
            if (mActoinCode == AccessibilityNodeInfo.ACTION_SET_TEXT)
            {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                {
                    node.performAction(mActoinCode, mActionParams);
                }
                else
                {
                    //先将输入框内容清空
                    Bundle arguments = new Bundle();
                    arguments.putInt(AccessibilityNodeInfo.ACTION_ARGUMENT_SELECTION_START_INT, 0);
                    arguments.putInt(AccessibilityNodeInfo.ACTION_ARGUMENT_SELECTION_END_INT, node.getText().length());
                    node.performAction(AccessibilityNodeInfo.ACTION_SET_SELECTION, arguments);
                    node.performAction(AccessibilityNodeInfo.ACTION_CUT);

                    // 将数据填入
                    ClipboardManager clipboard = Llxx_Application.getApplication().getClipboardManager();
                    ClipData clip = ClipData.newPlainText("text",
                            mActionParams.getString("ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE", ""));
                    clipboard.setPrimaryClip(clip);
                    //焦点（n是AccessibilityNodeInfo对象）  
                    node.performAction(AccessibilityNodeInfo.ACTION_FOCUS);
                    ////粘贴进入内容  
                    node.performAction(AccessibilityNodeInfo.ACTION_PASTE);

                    Ll_Loger.e(TAG, "performAction do action mActoinCode = " + mActoinCode + ", mActionParams = "
                            + mActionParams.getString("ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE", ""));
                }
            }
            else
            {
                node.performAction(mActoinCode, mActionParams);
            }
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
            mSelector = UiSelectParse.parse(select);
            mActoinCode = getCommand().getParams("action", -1);
            JSONArray _actionParams = getCommand().getParams("actionParams", new JSONArray());

            if (_actionParams != null)
            {
                Bundle arguments = new Bundle();
                for (int i = 0; i < _actionParams.length(); i++)
                {
                    putArguments(arguments, _actionParams.optJSONObject(i));
                }
                if (arguments.size() > 0)
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
