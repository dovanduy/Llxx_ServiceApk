package com.llxx.socket.wrap;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;

import com.llxx.socket.loger.Ll_Loger;
import com.llxx.socket.wrap.bean.Ll_Message;

import android.text.TextUtils;

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
        try
        {
            socket.setSendBufferSize(1024 * 60 * 2);
            socket.setReceiveBufferSize(1024 * 60 * 2);
            socket.setTcpNoDelay(true);
            Ll_Loger.i(TAG, "setSendBufferSize ok!!!!!!!!!!!");
        }
        catch (SocketException e1)
        {
            e1.printStackTrace();
        }
        mListener = listener;
        try
        {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
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

    private StringBuilder input = new StringBuilder();
    private boolean isKeepListener = true;

    @Override
    public void run()
    {
        try
        {
            Ll_Loger.i(TAG, this + " wait for message");
            while (isKeepListener)
            {
                input.setLength(0); // clear

                int a;
                // (char) -1 is not equal to -1.
                // ready is checked to ensure the read call doesn't block.
                while ((a = in.read()) != -1 && in.ready())
                {
                    input.append((char) a);
                }
                String msg = input.toString();
                if (mListener != null && !TextUtils.isEmpty(msg))
                {
                    mListener.onMessage(this, new Ll_Message(msg));
                    Ll_Loger.d(TAG, this + " receive message -->" + msg);
                }
                // 客户端已经断开连接
                if (a == -1)
                {
                    isKeepListener = false;
                }
                Ll_Loger.i(TAG, this + " wait for message" + a);
            }
            try
            {
                socket.close();
                Ll_Loger.d(TAG, this + " close  -->" + socket);
            }
            catch (Throwable e)
            {
                e.printStackTrace();
            }
            Ll_Loger.d(TAG, this + "--> isClose() " + isClose());

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Socket is close
     * @return
     */
    public boolean isClose()
    {
        return socket.isClosed();
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
            pout = new PrintWriter(new BufferedWriter(new OutputStreamWriter(mSocket.getOutputStream())), true);
            Ll_Loger.d(TAG, "sendmsg -> " + msg);
            pout.println(msg);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
