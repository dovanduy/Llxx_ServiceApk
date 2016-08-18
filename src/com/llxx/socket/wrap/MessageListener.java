package com.llxx.socket.wrap;

import com.llxx.socket.wrap.bean.Ll_Message;
/**
 * 
 * @author 李万隆
 * @qq 461051343
 * @date 2016年8月18日
 * @describe 监听Message消息
 *
 */
public interface MessageListener
{
    /**
     * 监听到客户端发来的消息，然后发送给本地服务端
     * @param wrap 
     * @param message
     */
    public void onMessage(ClientSocketWrap wrap, Ll_Message message);
}
