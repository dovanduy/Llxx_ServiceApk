/**
 * 
 */
package com.llxx.socket.handler.wrap;

import com.llxx.socket.handler.RequestHandler;
import com.llxx.socket.loger.Ll_Loger;
import com.llxx.socket.service.Ll_SocketService;
import com.llxx.socket.wrap.Ll_ClientSocketWrap;

import android.content.pm.PackageManager.NameNotFoundException;

/**
 * @author 繁星
 * @describe 打开页面
 */
public class AmRequestHandler extends RequestHandler
{
    public static final String TAG = "ProtocolAm";

    /**启动Acitivity*/
    public static final String START_ACTIVITY = "start_app";

    @Override
    public void doAction(Ll_ClientSocketWrap wrap, Ll_SocketService service)
    {
        String type = getCommand().getParams("type", "");
        String packagename = getCommand().getParams("packagename", "");

        Ll_Loger.d(TAG, "doAction -> type" + type);
        switch (type)
        {
        case START_ACTIVITY:
            {
                Ll_Loger.d(TAG, "start_app->" + packagename);
                try
                {
                    com.llxx.utils.AppUtils.openApp(packagename, service);
                    getResultObject().setSucess(true);
                }
                catch (NameNotFoundException e)
                {
                    e.printStackTrace();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    getResultObject().setSucess(false);
                }
            }
            break;
        default:
            getResultObject().setReason("no match type");
            getResultObject().setSucess(false);
            break;
        }
    }

    @Override
    public String action()
    {
        return "am";
    }

}
