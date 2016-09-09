package com.llxx.socket.wrap;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.llxx.socket.config.Configs;
import com.llxx.socket.loger.Ll_Loger;

import android.util.Log;

/**
 * 
 * @author fanxin, eachen
 * @qq 461051343
 * @date 2016年8月18日
 * @describe 封装Socket服务端
 */
public class Ll_SocketServiceWrap
{
    static final String TAG = "SocketServiceWrap";
    private static final int PORT = 8082;
    private List<Ll_ClientSocketWrap> mList = new ArrayList<Ll_ClientSocketWrap>();
    private ServerSocket server = null;
    private ExecutorService mExecutorService = null; //thread pool

    Ll_MessageListener mMessageListener;

    public Ll_SocketServiceWrap(Ll_MessageListener listener)
    {
        mMessageListener = listener;
    }

    /**
     * 获取端口
     * @return
     */
    public int getPort()
    {
        return PORT;
    }

    public void run()
    {
        try
        {
            server = new ServerSocket(PORT);
            mExecutorService = Executors.newCachedThreadPool(); //create a thread pool
            Log.e(TAG, "服务器已启动...");
            Socket client = null;
            while (true)
            {
                client = server.accept();
                //把客户端放入客户端集合中
                Ll_ClientSocketWrap wrap = new Ll_ClientSocketWrap(client, mMessageListener);
                mList.add(wrap);
                mExecutorService.execute(wrap); //start a new thread to handle the connection
                Ll_Loger.d(TAG, "client " + wrap + " connect to services");
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * 给指定Hash的客户端发送消息
     * @param msg
     */
    public void sendMessage(String msg, int hash, int exclude)
    {
        int num = mList.size();
        for (int index = 0; index < num; index++)
        {
            Ll_ClientSocketWrap mSocket = mList.get(index);
            if (mSocket.hashCode() != exclude && (hash == mSocket.hashCode() || hash == 0))
            {
                PrintWriter pout = null;
                if (mSocket.getSocket().isConnected())
                {
                    try
                    {
                        pout = new PrintWriter(
                                new BufferedWriter(new OutputStreamWriter(mSocket.getSocket().getOutputStream())),
                                true);
                        pout.println(msg);
                        if (Configs.DEBUG_SOCKETSERVICEWRAP_SEND_SHORT)
                            Ll_Loger.d(TAG, "sendMessage->" + (msg.length() > 200 ? msg.substring(0, 200) : msg));
                        else
                            Ll_Loger.d(TAG, "sendMessage->" + msg);
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
    
    /**
     * 移除指定的监听
     * @param wrap
     */
    public void remove(Ll_ClientSocketWrap wrap)
    {
        if(mList.contains(wrap))
        {
            mList.remove(wrap);
        }
    }

    public void sendMessage(String msg, int hash)
    {
        sendMessage(msg, hash, 0);
    }

    /**
     * 给所有的客户端发送消息
     * @param msg
     */
    public void sendMessage(String msg)
    {
        sendMessage(msg, 0);
    }
}
