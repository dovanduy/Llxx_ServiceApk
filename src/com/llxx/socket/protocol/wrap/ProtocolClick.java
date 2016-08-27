/**
 * 
 */
package com.llxx.socket.protocol.wrap;

import org.json.JSONException;
import org.json.JSONObject;

import com.llxx.socket.protocol.Protocol;
import com.llxx.socket.service.Ll_SocketService;
import com.llxx.socket.wrap.Ll_ClientSocketWrap;

import android.content.Context;

/**
 * @author 繁星
 * @describe 通知页面
 */
public class ProtocolClick extends Protocol
{

    private String type = "";
    private String title = "";
    private String idname = "";

    @Override
    public void doAction(Ll_ClientSocketWrap wrap, Ll_SocketService service)
    {

    }

    @Override
    public boolean prase()
    {
        return false;
    }

    @Override
    public String action()
    {
        return "click";
    }

    @Override
    public String getResult(Context context)
    {
        JSONObject object = getJsonObject();
        if (object != null)
        {
            try
            {
                object.put("type", getType());
                object.put("title", getTitle());
                object.put("id", getIdname());
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

    /**
     * @return the title
     */
    public String getTitle()
    {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title)
    {
        this.title = title;
    }

    /**
     * @return the idname
     */
    public String getIdname()
    {
        return idname;
    }

    /**
     * @param idname the idname to set
     */
    public void setIdname(String idname)
    {
        this.idname = idname;
    }

    
}
