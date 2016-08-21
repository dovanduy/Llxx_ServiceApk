package com.llxx.socket.wrap.bean;

/**
 * 
 * @author 繁星
 * @describe 终端发送过来的消息，分装了消息的内容，以及消息的封装格式
 */
public class Ll_Message
{
    /**
     * 消息使用Json作为数据格式传输
     */
    public static final int MSG_JSON = 0x01;

    /**
     * 消息使用 action|params... 的格式传输
     */
    public static final int MSG_SPLIT = 0x02;

    private String message = "";
    private int msgtype = MSG_SPLIT;

    public Ll_Message(String message)
    {
        super();
        this.message = message;
        if (message.startsWith("{") && message.endsWith("}"))
        {
            msgtype = MSG_JSON;
        }
    }

    /**
     * @return the message
     */
    public String getMessage()
    {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(String message)
    {
        this.message = message;
    }

    /**
     * @return the msgtype
     */
    public int getMsgtype()
    {
        return msgtype;
    }

    /**
     * @param msgtype the msgtype to set
     */
    public void setMsgtype(int msgtype)
    {
        this.msgtype = msgtype;
    }
}
