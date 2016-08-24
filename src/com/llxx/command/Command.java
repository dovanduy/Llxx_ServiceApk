package com.llxx.command;

import org.json.JSONObject;

import com.llxx.socket.wrap.bean.Ll_Message;

import android.content.Context;

public abstract class Command
{
    Ll_Message message;

    public Command()
    {

    }

    /**
     * 解析设置的message数据
     * @return 是否解析成功
     */
    public abstract boolean prase();

    /**
     * 默认的Action
     * @return 默认的Action方式
     */
    public abstract String action();

    /**
     * 返回结果
     * @return 给客户端返回数据结果
     */
    public abstract String getResult(Context context);

    /**
     * 要执行的操作
     */
    private String action = "";

    /**
     * @return the action
     */
    public String getAction()
    {
        return action;
    }

    /**
     * @param action the action to set
     */
    public void setAction(String action)
    {
        this.action = action;
    }

    /**
     * @return the message
     */
    public Ll_Message getMessage()
    {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(Ll_Message message)
    {
        this.message = message;
    }

    /**
     * 获取JsonObject对象
     * @return
     */
    public JSONObject getJsonObject()
    {
        try
        {
            JSONObject object = new JSONObject();
            object.put("action", getAction());
            return object;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
