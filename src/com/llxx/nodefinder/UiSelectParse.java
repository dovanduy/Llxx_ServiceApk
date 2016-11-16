/**
 * 
 */
package com.llxx.nodefinder;

import org.json.JSONObject;

import com.llxx.socket.loger.Ll_Loger;

import android.util.SparseArray;

/**
 * @author 繁星
 * @date   2016年11月16日
 * @describe 解析客户端传过来的选择数据
 */
public class UiSelectParse
{
    static final String TAG = "UiSelectParse";

    public static UiSelector parse(JSONObject select)
    {
        return parseJson(select);
    }

    static UiSelector parseJson(JSONObject select)
    {
        SparseArray<Object> mSelectorAttributes = new SparseArray<Object>();
        UiSelector selector = new UiSelector(mSelectorAttributes);
        for (int i = 0; i < UiSelector.SELECTOR_MAX; i++)
        {
            if (select.has(String.valueOf(i)))
            {
                if (i == UiSelector.SELECTOR_CHILD)
                {
                    JSONObject child = select.optJSONObject(String.valueOf(i));
                    if (child != null)
                    {
                        UiSelector childSelector = parseJson(child);
                        selector.childSelector(childSelector);
                    }
                    continue;
                }

                if (i == UiSelector.SELECTOR_PARENT)
                {
                    JSONObject child = select.optJSONObject(String.valueOf(i));
                    if (child != null)
                    {
                        UiSelector childSelector = parseJson(child);
                        selector.fromParent(childSelector);
                    }
                    continue;
                }
                String text = select.optString(String.valueOf(i), "");
                mSelectorAttributes.put(i, text);
                Ll_Loger.i(TAG, "mSelectorAttributes put " + i + ", " + text);
            }
        }
        return selector;
    }
}
