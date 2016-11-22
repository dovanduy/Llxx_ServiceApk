package com.llxx.socket.service;

import java.io.IOException;

import org.json.JSONObject;

import com.llxx.client.command.CommandManager;
import com.llxx.client.node.Ll_AccessibilityClient;
import com.llxx.command.Command;
import com.llxx.socket.handler.IProtocol;
import com.llxx.socket.handler.ProtocolJson;
import com.llxx.socket.handler.RequestHandler;
import com.llxx.socket.loger.Ll_Loger;
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
    static final boolean DEBUG_ON_MESSAGE = true;
    Thread mSocketThread;
    SocketRunnable mRunnable;
    IProtocol mProtocolJson;
    Object mAccessibilityClientLock = new Object();

    @Override
    public void onCreate()
    {
        super.onCreate();

        mProtocolJson = new ProtocolJson();
        mRunnable = new SocketRunnable();
        mRunnable.run();
        //mSocketThread = new Thread(mRunnable);
        //mSocketThread.start();
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

        @Override
        public void sendMessageByHash(String message, int hash) throws RemoteException
        {
            mRunnable.getService().sendMessage(message, hash);
        }

        @Override
        public void sendMessageExcludeHash(String message, int exincludehash) throws RemoteException
        {
            mRunnable.getService().sendMessage(message, 0, exincludehash);
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
            try
            {
                mService.start();
                Ll_Loger.d(TAG, "Ll_SocketServiceWrap- run>");
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }

        public Ll_SocketServiceWrap getService()
        {
            return mService;
        }
    }

    @Override
    public void onMessage(Ll_ClientSocketWrap wrap, Ll_Message message)
    {
        RequestHandler protocol = null;
        protocol = mProtocolJson.parseMessage(message);

        if (DEBUG_ON_MESSAGE)
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
                    if (!object.optBoolean("isToClient", false))
                    {
                        object.put("clientHash", wrap.hashCode());
                        Ll_AccessibilityClient.addMessage(new Ll_Message(object.toString()));
                    }
                    else
                    {
                        int hashCode = object.optInt("clientHash", 0);
                        if (hashCode == 0)
                        {
                            mRunnable.getService().sendMessage(message.getMessage(), 0, wrap.hashCode());
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
}
