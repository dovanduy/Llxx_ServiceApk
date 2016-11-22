/**
 * 
 */
package com.llxx.socket.handler;

import java.util.HashMap;

import com.llxx.socket.handler.wrap.AmRequestHandler;
import com.llxx.socket.handler.wrap.QueryRequestHandler;

/**
 * @author 繁星
 * @describe 数据协议管理
 */
public class ProtocolManager
{
    public static final HashMap<String, Class<? extends RequestHandler>> mProtocols = new HashMap<String, Class<? extends RequestHandler>>();

    static
    {
        mProtocols.put("query", QueryRequestHandler.class);
        mProtocols.put("am", AmRequestHandler.class);
    }
}
