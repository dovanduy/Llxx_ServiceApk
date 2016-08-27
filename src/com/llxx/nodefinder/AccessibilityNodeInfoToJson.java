/**
 * 
 */
package com.llxx.nodefinder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.annotation.TargetApi;
import android.os.Build;
import android.view.accessibility.AccessibilityNodeInfo;

/**
 * @author 繁星
 * @describe 将AccessibilityNode解析为Json
 */
@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
public class AccessibilityNodeInfoToJson
{
    public static JSONObject getJson(AccessibilityNodeInfo info)
            throws JSONException
    {
        JSONObject object = new JSONObject();
        object.put("packagename", CharSequenceWrap(info.getPackageName()));
        object.put("classname", CharSequenceWrap(info.getClassName()));
        object.put("text", CharSequenceWrap(info.getText()));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2)
        {
            object.put("id", CharSequenceWrap(info.getViewIdResourceName()));
        }

        JSONArray array = new JSONArray();
        for (int i = 0; i < info.getChildCount(); i++)
        {
            array.put(getJson(info.getChild(i)));
        }
        if (array.length() > 0)
            object.put("childs", array);
        return object;
    }

    static CharSequence CharSequenceWrap(CharSequence sequence)
    {
        return sequence == null ? "" : sequence;
    }
}
