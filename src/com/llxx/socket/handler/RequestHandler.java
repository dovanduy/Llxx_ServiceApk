/**
 * 
 */
package com.llxx.socket.handler;

import com.llxx.command.Command;
import com.llxx.socket.service.Ll_SocketService;
import com.llxx.socket.wrap.Ll_ClientSocketWrap;

/**
 * @author 繁星
 * @describe 协议数据协议分装
 */
public abstract class RequestHandler extends Command
{
    private String classname = "";
    private String packageName = "";

    public abstract void doAction(Ll_ClientSocketWrap wrap, Ll_SocketService service);

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
