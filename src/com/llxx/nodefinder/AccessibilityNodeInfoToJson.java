/**
 * 
 */
package com.llxx.nodefinder;

import org.json.JSONException;
import org.json.JSONObject;

import android.view.accessibility.AccessibilityNodeInfo;

/**
 * @author 繁星
 * @describe 将AccessibilityNode解析为Json
 */
public class AccessibilityNodeInfoToJson
{
    public static JSONObject getJson(AccessibilityNodeInfo info)
            throws JSONException
    {
        JSONObject object = new JSONObject();
        object.put("packagename", info.getPackageName());
        return object;
    }
    
    
}
