package com.llxx.socket.service;

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
    Thread mSocketThread;
    SocketRunnable mRunnable;

    @Override
    public void onCreate()
    {
        super.onCreate();

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
        Ll_Loger.LogD(TAG, "SocketService.onMessage()->" + message.getMessage());
    }
}
