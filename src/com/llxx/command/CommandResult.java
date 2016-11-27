package com.llxx.command;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 
 * @author fanxin, eachen
 * @qq 461051343
 * @date 2016年9月8日
 * @describe 
 *
 */
public class CommandResult
{
    public static final String KEY_PARAMS = "params";
    public static final String KEY_SUCESS = "sucess";
    public static final String KEY_ACTOIN = "action";
    public static final String KEY_REASON = "reason";
    public static final String KEY_ID = "id";

    JSONObject mResult;
    JSONObject mParams;

    private boolean sucess;
    private String action = "";
    private String classname = "";
    private String packageName = "";

    private String reason = "";
    private String id = "";

    public CommandResult(String action)
    {
        mResult = new JSONObject();
        mParams = new JSONObject();
        this.action = action;
    }

    /**
     * 返回结果
     * @return 给客户端返回数据结果
     */
    public String getResult()
    {
        try
        {
            mResult.put(KEY_SUCESS, isSucess());
            mResult.put(KEY_ACTOIN, action);
            mResult.put(KEY_REASON, reason);
            mResult.put(KEY_ID, id);
            mResult.put(KEY_PARAMS, mParams);

            mParams.put("classname", getClassname());
            mParams.put("packagename", getPackageName());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return mResult.toString();
    }

    /**
     * 
     * @param name
     * @param value
     * @throws JSONException
     */
    public void putParams(String name, boolean value) throws JSONException
    {
        mParams.put(name, value);
    }

    /**
     * 
     * @param name
     * @param value
     * @throws JSONException
     */
    public void putParams(String name, double value) throws JSONException
    {
        mParams.put(name, value);
    }

    /**
     * 
     * @param name
     * @param value
     * @throws JSONException
     */
    public void putParams(String name, int value) throws JSONException
    {
        mParams.put(name, value);
    }

    /**
     * 
     * @param name
     * @param value
     * @throws JSONException
     */
    public void putParams(String name, long value) throws JSONException
    {
        mParams.put(name, value);
    }

    /**
     * 
     * @param name
     * @param value
     * @throws JSONException
     */
    public void putParams(String name, Object value) throws JSONException
    {
        mParams.put(name, value);
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
     * @return the reason
     */
    public String getReason()
    {
        return reason;
    }

    /**
     * @param reason the reason to set
     */
    public void setReason(String reason)
    {
        this.reason = reason;
    }

    /**
     * @return the id
     */
    public String getId()
    {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id)
    {
        this.id = id;
    }

}
