package com.llxx.socket.wrap;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import com.llxx.socket.loger.Ll_Loger;
import com.llxx.socket.wrap.bean.Ll_Message;

public class Ll_ClientSocketWrap implements Runnable
{
    public static final String TAG = "Ll_ClientSocketWrap";
    private Socket socket;
    private BufferedReader in = null;
    String msg = "";
    Ll_MessageListener mListener;

    public Ll_ClientSocketWrap(Socket socket, Ll_MessageListener listener)
    {
        this.socket = socket;
        mListener = listener;
        try
        {
            in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }

    /**
     * 获取客户端的Socket
     * @return
     */
    public Socket getSocket()
    {
        return socket;
    }

    @Override
    public void run()
    {
        try
        {
            while (true)
            {
                Ll_Loger.i(TAG, this + " wait for message");
                if ((msg = in.readLine()) != null)
                {
                    if (mListener != null)
                    {
                        mListener.onMessage(this, new Ll_Message(msg));
                        Ll_Loger.d(TAG, this + " receive message -->" + msg);
                    }
                    
                }
                if(socket.isClosed())
                {
                    break;
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * 循环遍历客户端集合，给每个客户端都发送信息。
     */
    public void sendmsg(String msg)
    {
        Ll_Loger.d("", msg);
        Socket mSocket = this.socket;
        PrintWriter pout = null;
        try
        {
            pout = new PrintWriter(
                    new BufferedWriter(
                            new OutputStreamWriter(mSocket.getOutputStream())),
                    true);
            Ll_Loger.d(TAG, "sendmsg -> " + msg);
            pout.println(msg);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
