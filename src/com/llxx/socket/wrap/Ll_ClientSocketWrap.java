package com.llxx.socket.wrap;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import com.llxx.socket.loger.Ll_Loger;

public class Ll_ClientSocketWrap implements Runnable
{
    private Socket socket;
    private BufferedReader in = null;
    String msg = "";

    public Ll_ClientSocketWrap(Socket socket, Ll_MessageListener listener)
    {
        this.socket = socket;
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

    @Override
    public void run()
    {
        try
        {
            while (true)
            {
                if ((msg = in.readLine()) != null)
                {

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
        Ll_Loger.LogD("", msg);
        Socket mSocket = this.socket;
        PrintWriter pout = null;
        try
        {
            pout = new PrintWriter(new BufferedWriter(new OutputStreamWriter(mSocket.getOutputStream())), true);
            pout.println(msg);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
