package com.llxx.socket;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class SocketClient implements Runnable
{
    private static final String HOST = "127.0.0.1";
    private static final int PORT = 9999;
    private Socket socket = null;
    private BufferedReader in = null;
    private PrintWriter out = null;
    private String content = "";

    /**
     * 发送消息
     * @param msg
     */
    public void send(String msg)
    {
        if (socket.isConnected())
        {
            if (!socket.isOutputShutdown())
            {
                out.println(msg);
            }
        }
    }

    public void run()
    {
        try
        {
            socket = new Socket(HOST, PORT);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
        try
        {
            while (true)
            {
                if (!socket.isClosed())
                {
                    if (socket.isConnected())
                    {
                        if (!socket.isInputShutdown())
                        {
                            if ((content = in.readLine()) != null)
                            {
                                content += "\n";
                                System.out.println("SocketClient.run()->" + content);
                            }
                            else
                            {

                            }
                        }
                    }
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
