/**
 * 
 */
package com.llxx.socket.handler.wrap;

import com.llxx.socket.handler.RequestHandler;
import com.llxx.socket.loger.Ll_Loger;
import com.llxx.socket.service.Ll_SocketService;
import com.llxx.socket.wrap.Ll_ClientSocketWrap;

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
    public String action()
    {
        return "am";
    }

}
