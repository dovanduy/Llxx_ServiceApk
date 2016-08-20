/**
 * 
 */
package com.llxx.socket.protocol;

import com.llxx.socket.wrap.bean.Ll_Message;

/**
 * @author 繁星
 * @describe 分隔协议描述
 */
public class ProtocolSplit implements IProtocol
{
    public static final String SP = "|";

    @Override
    public Protocol parseMessage(Ll_Message message)
    {
        String[] strings = message.getMessage().split("|");
        return null;
    }
}
