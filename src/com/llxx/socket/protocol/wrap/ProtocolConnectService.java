package com.llxx.socket.protocol.wrap;

import org.json.JSONObject;

import com.llxx.socket.loger.Ll_Loger;
import com.llxx.socket.protocol.Protocol;
import com.llxx.socket.service.Ll_SocketService;
import com.llxx.socket.wrap.Ll_ClientSocketWrap;

import android.content.Context;

/**
 * 
 * @author 繁星
 * @describe 客户端连接到服务端
 *
 */
public class ProtocolConnectService extends Protocol
{

    public static final String TAG = "ProtocolConnectService";


    @Override
    public boolean prase()
    {
        return false;
    }

    @Override
    public String action()
    {
        return "accessibility_connect_service";
    }

    @Override
    public String getResult(Context context)
    {
        JSONObject msg = getJsonObject();
        if (msg != null)
        {
            return msg.toString();
        }
        return "";
    }

    @Override
    public void doAction(Ll_ClientSocketWrap wrap, Ll_SocketService service)
    {
        Ll_Loger.i(TAG, "AccessibilityClient connect to service");
        service.setAccessibilityClient(wrap);
    }

}
