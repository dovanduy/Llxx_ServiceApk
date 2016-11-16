/**
 * 
 */
package com.llxx.socket.protocol;

import com.llxx.socket.wrap.bean.Ll_Message;

/**
 * @author 繁星
 * @describe 类描述
 */
public interface IProtocol
{
    /**
     * 解析数据协议
     * @param message
     * @return
     */
    public Protocol parseMessage(Ll_Message message);
}
