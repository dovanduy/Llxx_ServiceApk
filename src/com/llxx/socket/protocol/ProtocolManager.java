/**
 * 
 */
package com.llxx.socket.protocol;

import java.util.HashMap;

import com.llxx.socket.protocol.wrap.ProtocolAm;
import com.llxx.socket.protocol.wrap.ProtocolQuery;
import com.llxx.socket.protocol.wrap.ProtocolScreen;

/**
 * @author 繁星
 * @describe 数据协议管理
 */
public class ProtocolManager
{
    public static final HashMap<String, Class<? extends Protocol>> mProtocols = new HashMap<String, Class<? extends Protocol>>();

    static
    {
        mProtocols.put("getwh", ProtocolScreen.class);
        mProtocols.put("query", ProtocolQuery.class);
        mProtocols.put("am", ProtocolAm.class);
    }
}
