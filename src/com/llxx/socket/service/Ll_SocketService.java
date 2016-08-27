package com.llxx.socket.service;

import org.json.JSONObject;

import com.llxx.client.command.CommandManager;
import com.llxx.command.Command;
import com.llxx.socket.loger.Ll_Loger;
import com.llxx.socket.protocol.IProtocol;
import com.llxx.socket.protocol.Protocol;
import com.llxx.socket.protocol.ProtocolJson;
import com.llxx.socket.protocol.ProtocolSplit;
import com.llxx.socket.wrap.Ll_ClientSocketWrap;
import com.llxx.socket.wrap.Ll_MessageListener;
import com.llxx.socket.wrap.Ll_SocketServiceWrap;
import com.llxx.socket.wrap.bean.Ll_Message;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

public class Ll_SocketService extends Service implements Ll_MessageListener
{

    static final String TAG = "SocketService";
    Thread mSocketThread;
    SocketRunnable mRunnable;
    IProtocol mProtocolJson;
    IProtocol mProtocolSplit;
    Ll_ClientSocketWrap mAccessibilityClient;
    Object mAccessibilityClientLock = new Object();

    @Override
    public void onCreate()
    {
        super.onCreate();

        mProtocolJson = new ProtocolJson();
        mProtocolSplit = new ProtocolSplit();
        mRunnable = new SocketRunnable();
        mSocketThread = new Thread(mRunnable);
        mSocketThread.start();
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent arg0)
    {
        return mService.asBinder();
    }

    /**
     * 服务
     */
    ISocketService mService = new ISocketService.Stub()
    {
        @Override
        public void sendMessage(String message) throws RemoteException
        {
            mRunnable.getService().sendMessage(message);
        }

        @Override
        public String getPort() throws RemoteException
        {
            return String.valueOf(mRunnable.getService().getPort());
        }
    };

    /**
     * 运行Socket的线程
     * @describe 
     */
    class SocketRunnable implements Runnable
    {
        Ll_SocketServiceWrap mService = null;

        @Override
        public void run()
        {
            mService = new Ll_SocketServiceWrap(Ll_SocketService.this);
            mService.run();
        }

        public Ll_SocketServiceWrap getService()
        {
            return mService;
        }
    }

    @Override
    public void onMessage(Ll_ClientSocketWrap wrap, Ll_Message message)
    {
        Protocol protocol = null;
        if (message.getMsgtype() == Ll_Message.MSG_JSON)
        {
            protocol = mProtocolJson.parseMessage(message);
        }
        else
        {
            protocol = mProtocolSplit.parseMessage(message);
        }

        Ll_Loger.d(TAG, "SocketService.onMessage()->" + message.getMessage() + "," + protocol);

        if (protocol != null)
        {
            protocol.doAction(wrap, this);
            wrap.sendmsg(protocol.getResult(getApplicationContext()));
        }
        else
        {
            try
            {
                JSONObject object = new JSONObject(message.getMessage());
                String action = object.optString("action", "");
                Class<? extends Command> command = CommandManager.mProtocols.get(action);
                if (command != null)
                {
                    if(!object.optBoolean("isToClient", false))
                    {
                        object.put("clientHash", wrap.hashCode());
                        mAccessibilityClient.sendmsg(object.toString());
                    }
                    else
                    {
                        int hashCode = object.optInt("clientHash", 0);
                        if(hashCode == 0)
                        {
                            mRunnable.getService().sendMessage(message.getMessage());
                        }
                        else
                        {
                            mRunnable.getService().sendMessage(message.getMessage(), hashCode);
                        }
                    }
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

            // XXX DO ERROR
        }
    }

    /**
     * 设置辅助服务的客户端
     * @param wrap
     */
    public void setAccessibilityClient(Ll_ClientSocketWrap wrap)
    {
        synchronized (mAccessibilityClientLock)
        {
            mAccessibilityClient = wrap;
        }
    }
}
