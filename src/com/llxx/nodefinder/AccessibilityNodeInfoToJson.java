/**
 * 
 */
package com.llxx.nodefinder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.annotation.TargetApi;
import android.graphics.Rect;
import android.os.Build;
import android.view.accessibility.AccessibilityNodeInfo;

import static com.llxx.utils.ReflectionHelper.*;

/**
 * @author 繁星
 * @describe 将AccessibilityNode解析为Json
 */
@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
public class AccessibilityNodeInfoToJson
{
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static JSONObject getJson(AccessibilityNodeInfo info) throws JSONException
    {
        JSONObject object = new JSONObject();
        object.put("package", CharSequenceWrap(info.getPackageName()));
        object.put("class", CharSequenceWrap(info.getClassName()));
        object.put("windowId", info.getWindowId());
        object.put("content-desc", info.getContentDescription());
        Rect rect = new Rect();
        info.getBoundsInScreen(rect);
        object.put("bounds", "[" + rect.left + "," + rect.top+ "]" + "[" + rect.right + "," + rect.bottom+ "]");
        
        object.put("text", CharSequenceWrap(info.getText()));
        object.put("checkable", info.isCheckable());
        object.put("checked", info.isChecked());
        object.put("focusable", info.isFocusable());
        object.put("focused", info.isFocused());
        object.put("selected", info.isSelected());
        object.put("clickable", info.isClickable());
        object.put("longClickable", info.isLongClickable());
        object.put("enabled", info.isEnabled());
        object.put("password", info.isPassword());
        object.put("scrollable", info.isScrollable());
        //        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
        //        {
        //            try
        //            {
        //                object.put("collectionInfoCol", info.getCollectionInfo().getColumnCount());
        //                object.put("collectionInfoRow", info.getCollectionInfo().getRowCount());
        //            }
        //            catch (Exception e)
        //            {
        //                e.printStackTrace();
        //            }
        //        }

        // object.put("nodedump", info.toString());
        try
        {
            object.put("id", callIntMethod("getAccessibilityViewId", info));
        }
        catch (Exception e)
        {
        }
        
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2)
        {
            object.put("idname", CharSequenceWrap(info.getViewIdResourceName()));
        }

        JSONArray array = new JSONArray();
        for (int i = 0; i < info.getChildCount(); i++)
        {
            array.put(getJson(info.getChild(i)));
        }
        if (array.length() > 0)
            object.put("node", array);
        return object;
    }

    static CharSequence CharSequenceWrap(CharSequence sequence)
    {
        return sequence == null ? "" : sequence;
    }
}
