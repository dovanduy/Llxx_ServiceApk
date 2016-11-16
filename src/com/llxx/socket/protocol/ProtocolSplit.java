/**
 * 
 */
package com.llxx.socket.protocol;

import com.llxx.socket.loger.Ll_Loger;
import com.llxx.socket.wrap.bean.Ll_Message;

/**
 * @author 繁星
 * @describe 分隔协议描述
 */
public class ProtocolSplit implements IProtocol
{
    public static final String TAG = "ProtocolSplit";
    public static final String SP = "|";

    @Override
    public Protocol parseMessage(Ll_Message message)
    {
        String[] strings = { message.getMessage() };
        if (message.getMessage().contains("|"))
        {
            strings = message.getMessage().split("|");
        }
        String action = strings[0].trim().replaceAll("\r\n", "");
        Class<? extends Protocol> protocol = ProtocolManager.mProtocols
                .get(action);
        Ll_Loger.d(TAG, "parseMessage -> " + message.getMessage()
                + ", protocol ->" + protocol);
        if (protocol != null)
        {
            try
            {
                Protocol mProtocol = protocol.newInstance();
                mProtocol.setMessage(message);
                mProtocol.prase();
                return mProtocol;
            }
            catch (InstantiationException e)
            {
                e.printStackTrace();
            }
            catch (IllegalAccessException e)
            {
                e.printStackTrace();
            }
        }
        return null;
    }
}
