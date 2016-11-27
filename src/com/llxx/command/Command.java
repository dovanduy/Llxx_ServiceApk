package com.llxx.command;

import org.json.JSONObject;

import com.llxx.socket.wrap.bean.Ll_Message;

public abstract class Command
{
    public static final String PARAMS = "params";
    Ll_Message message;
    boolean isRunOk = false;

    CommandBean mCommandBean;
    CommandResult mCommandResult;

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
    public boolean prase()
    {
        mCommandBean = new CommandBean(getMessage());
        mCommandResult = new CommandResult(mCommandBean.getAction());
        if (!mCommandBean.isSucess())
        {
            mCommandResult.setReason("parse requer json error!!");
            return false;
        }
        mCommandResult.setId(mCommandBean.getId());
        mCommandResult.setSucess(mCommandBean.isSucess());
        return true;
    }

    /**
     * 
     * @return
     */
    public CommandBean getCommand()
    {
        return mCommandBean;
    }

    public CommandResult getResultObject()
    {
        return mCommandResult;
    }

    /**
     * 获取结果
     * @return
     */
    public String getResult()
    {
        return getResultObject().getResult().toString();
    }

    /**
     * 获取描述
     * @return
     */
    public String getDescribe()
    {
        return getCommand().getDescribe();
    }

    /**
     * 默认的Action
     * @return 默认的Action方式
     */
    public abstract String action();

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
}
