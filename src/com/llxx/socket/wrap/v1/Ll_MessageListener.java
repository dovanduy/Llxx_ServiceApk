package com.llxx.socket.wrap.v1;

import com.llxx.socket.wrap.bean.Ll_Message;
/**
 * 
 * @author fanxin, eachen
 * @qq 461051343
 * @date 2016年8月18日
 * @describe 监听Message消息
 *
 */
public interface Ll_MessageListener
{
    /**
     * 监听到客户端发来的消息，然后发送给本地服务端
     * @param wrap 
     * @param message
     */
    public void onMessage(Ll_ClientSocketWrap wrap, Ll_Message message);
}
