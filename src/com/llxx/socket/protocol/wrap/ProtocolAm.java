/**
 * 
 */
package com.llxx.socket.protocol.wrap;

import org.json.JSONException;
import org.json.JSONObject;

import com.llxx.socket.loger.Ll_Loger;
import com.llxx.socket.protocol.Protocol;
import com.llxx.socket.service.Ll_SocketService;
import com.llxx.socket.wrap.Ll_ClientSocketWrap;

import android.content.Context;

/**
 * @author 繁星
 * @describe 打开页面
 */
public class ProtocolAm extends Protocol
{
    public static final String TAG = "ProtocolAm";
    
    /**启动Acitivity*/
    public static final String START_ACTIVITY = "start_app";

    private String type = "";
    private String packagename = "";

    @Override
    public void doAction(Ll_ClientSocketWrap wrap, Ll_SocketService service)
    {
        switch (type)
        {
        case START_ACTIVITY:
            {
                try
                {
                    Ll_Loger.d(TAG, "start_app->" + packagename);
                    com.llxx.utils.AppUtils.openApp(packagename, service);
                    setRunOk(true);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
            break;
        default:
            break;
        }
    }

    @Override
    public boolean prase()
    {
        try
        {
            JSONObject object = new JSONObject(getMessage().getMessage());
            type = object.optString("type", "");
            packagename = object.optString("packagename", "");
            return true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public String action()
    {
        return "am";
    }

    @Override
    public String getResult(Context context)
    {
        JSONObject object = getJsonObject();
        if (object != null)
        {
            try
            {
                object.put("type", type);
                object.put("packagename", packagename);
                return object.toString();
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * @return the type
     */
    public String getType()
    {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type)
    {
        this.type = type;
    }

}
