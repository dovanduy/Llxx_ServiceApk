/**
 * 
 */
package com.llxx.socket.protocol;

import org.json.JSONException;
import org.json.JSONObject;

import com.llxx.command.Command;
import com.llxx.socket.service.Ll_SocketService;
import com.llxx.socket.wrap.Ll_ClientSocketWrap;

/**
 * @author 繁星
 * @describe 协议数据协议分装
 */
public abstract class Protocol extends Command
{
    private String classname = "";
    private String packageName = "";

    public abstract void doAction(Ll_ClientSocketWrap wrap, Ll_SocketService service);

    @Override
    public JSONObject getJsonObject()
    {
        JSONObject object = super.getJsonObject();
        if (object != null)
        {
            try
            {
                object.put("classname", getClassname());
                object.put("packagename", getPackageName());
                object.put("sucess", isRunOk());
                if (getCommandResult() != null)
                {
                    object.put("params", getCommandResult());
                }
                return object;
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * @return the classname
     */
    public String getClassname()
    {
        return classname;
    }

    /**
     * @param classname the classname to set
     */
    public void setClassname(String classname)
    {
        this.classname = classname;
    }

    /**
     * @return the packageName
     */
    public String getPackageName()
    {
        return packageName;
    }

    /**
     * @param packageName the packageName to set
     */
    public void setPackageName(String packageName)
    {
        this.packageName = packageName;
    }
}
