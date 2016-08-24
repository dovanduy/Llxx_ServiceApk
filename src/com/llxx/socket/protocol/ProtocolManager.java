/**
 * 
 */
package com.llxx.socket.protocol;

import java.util.HashMap;

import com.llxx.socket.protocol.wrap.ProtocolConnectService;
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
        mProtocols.put("accessibility_connect_service", ProtocolConnectService.class);
        
    }
}
