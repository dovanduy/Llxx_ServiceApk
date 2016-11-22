/**
 * 
 */
package com.llxx.socket.handler;

import org.json.JSONException;
import org.json.JSONObject;

import com.llxx.socket.loger.Ll_Loger;
import com.llxx.socket.wrap.bean.Ll_Message;

/**
 * @author 繁星
 * @describe Json协议封装
 */
public class ProtocolJson implements IProtocol
{
    public static final String TAG = "ProtocolJson";

    @Override
    public RequestHandler parseMessage(Ll_Message message)
    {
        try
        {
            JSONObject object = new JSONObject(message.getMessage());
            String action = object.optString("action", "");
            Class<? extends RequestHandler> protocol = ProtocolManager.mProtocols
                    .get(action);
            
            Ll_Loger.d(TAG, "parseMessage -> action : " + action
                    + ", protocol ->" + protocol);
            if (protocol != null)
            {
                try
                {
                    RequestHandler mProtocol = protocol.newInstance();
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
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
