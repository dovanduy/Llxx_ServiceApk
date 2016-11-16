package com.llxx.command;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.llxx.socket.wrap.bean.Ll_Message;

/**
 * 
 * @author fanxin, eachen
 * @qq 461051343
 * @date 2016年9月11日
 * @describe 描述用户输入的命令
 */
public class CommandBean
{
    public static final String KEY_PARAMS = "params";
    public static final String KEY_SUCESS = "sucess";
    public static final String KEY_ACTOIN = "action";
    public static final String KEY_REASON = "reason";
    public static final String KEY_DESCRIBE = "describe";

    JSONObject mParams;

    private boolean sucess;
    private String action = "";
    private String classname = "";
    private String packageName = "";
    private String describe = "";

    Ll_Message message;

    public CommandBean(Ll_Message message)
    {
        mParams = new JSONObject();
        this.message = message;
        try
        {
            JSONObject object = new JSONObject(this.message.getMessage());
            this.action = object.optString(action);
            this.describe = object.optString(KEY_DESCRIBE, "");
            mParams = object.getJSONObject(KEY_PARAMS);
            if (mParams == null)
                mParams = new JSONObject();
            sucess = isSucess();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * 
     * @param name
     * @param value
     * @throws JSONException
     */
    public boolean getParams(String name, boolean value)
    {
        return mParams.optBoolean(name, value);
    }

    /**
     * 
     * @param name
     * @param value
     * @throws JSONException
     */
    public double getParams(String name, double value)
    {
        return mParams.optDouble(name, value);
    }

    /**
     * 
     * @param name
     * @param value
     * @throws JSONException
     */
    public int getParams(String name, int value)
    {
        return mParams.optInt(name, value);
    }

    /**
     * 
     * @param name
     * @param value
     * @throws JSONException
     */
    public String getParams(String name, String value)
    {
        return mParams.optString(name, value);
    }

    /**
     * 
     * @param name
     * @param value
     * @throws JSONException
     */
    public long getParams(String name, long value)
    {
        return mParams.optLong(name, value);
    }

    /**
     * 
     * @param name
     * @param value
     * @throws JSONException
     */
    public JSONObject getParams(String name, JSONObject value)
    {
        return mParams.optJSONObject(name);
    }

    /**
     * 
     * @param name
     * @param value
     * @throws JSONException
     */
    public JSONArray getParams(String name, JSONArray value)
    {
        return mParams.optJSONArray(name);
    }

    /**
     * 是否运行成功
     * @return the sucess
     */
    public boolean isSucess()
    {
        return sucess;
    }

    /**
     * 设置是否运行成功
     * @param true 当前命令运行成功，false 当前命令运行失败
     */
    public void setSucess(boolean sucess)
    {
        this.sucess = sucess;
    }

    /**
     * @return the classname
     */
    public String getClassname()
    {
        return classname;
    }

    /**
     * @param classname the classname to set
     */
    public void setClassname(String classname)
    {
        this.classname = classname;
    }

    /**
     * @return the packageName
     */
    public String getPackageName()
    {
        return packageName;
    }

    /**
     * @param packageName the packageName to set
     */
    public void setPackageName(String packageName)
    {
        this.packageName = packageName;
    }

    /**
     * @return the action
     */
    public String getAction()
    {
        return action;
    }

    /**
     * @return the describe
     */
    public String getDescribe()
    {
        return describe;
    }

    /**
     * @param describe the describe to set
     */
    public void setDescribe(String describe)
    {
        this.describe = describe;
    }

}
