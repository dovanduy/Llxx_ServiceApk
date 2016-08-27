package com.llxx.command;

import org.json.JSONObject;

import com.llxx.socket.wrap.bean.Ll_Message;

import android.content.Context;

public abstract class Command
{
    Ll_Message message;
    boolean isRunOk = false;
    
    /**
     * 是否是发送给客户端端的
     */
    boolean isToClient = false;

    /**
     * 客户端持有对象的Hash值
     */
    int clientHash = 0;
    
    /**
     * 返回客户端执行结果
     */
    JSONObject result = null;
    
    public Command()
    {
        setAction(action());
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
    
    /**
     * 获取JsonObject对象
     * @return
     */
    public JSONObject getErrorResult()
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

    /**
     * @return the isRunOk
     */
    public boolean isRunOk()
    {
        return isRunOk;
    }

    /**
     * @param isRunOk the isRunOk to set
     */
    public void setRunOk(boolean isRunOk)
    {
        this.isRunOk = isRunOk;
    }

    /**
     * @return the isToClient
     */
    public boolean isToClient()
    {
        return isToClient;
    }

    /**
     * @param isToClient the isToClient to set
     */
    public void setToClient(boolean isToClient)
    {
        this.isToClient = isToClient;
    }

    /**
     * @return the clientHash
     */
    public int getClientHash()
    {
        return clientHash;
    }

    /**
     * @param clientHash the clientHash to set
     */
    public void setClientHash(int clientHash)
    {
        this.clientHash = clientHash;
    }

    /**
     * @return the result
     */
    public JSONObject getCommandResult()
    {
        return result;
    }

    /**
     * @param result the result to set
     */
    public void setCommandResult(JSONObject result)
    {
        this.result = result;
    }
}
