package com.llxx.socket.wrap;

import java.io.IOException;

import org.nanohttpd.protocols.http.IHTTPSession;
import org.nanohttpd.protocols.websockets.CloseCode;
import org.nanohttpd.protocols.websockets.WebSocket;
import org.nanohttpd.protocols.websockets.WebSocketFrame;

import com.llxx.socket.wrap.bean.Ll_Message;

public class Ll_ClientSocketWrap extends WebSocket
{
    public static final String TAG = "Ll_ClientSocketWrap";
    String msg = "";
    Ll_MessageListener mListener;
    boolean isClose = false;

    // Socket socket, Ll_MessageListener listener
    public Ll_ClientSocketWrap(IHTTPSession handshakeRequest,
            Ll_MessageListener listener)
    {
        super(handshakeRequest);
        mListener = listener;
    }

    /**
     * Socket is close
     * @return
     */
    public boolean isClose()
    {
        return isClose;
    }

    /**
     * 循环遍历客户端集合，给每个客户端都发送信息。
     */
    public void sendmsg(String msg)
    {
        try
        {
            send(msg);
            System.out.println("Ll_ClientSocketWrap.sendmsg()" + msg);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    protected void onOpen()
    {
        isClose = false;
        System.out.println("Ll_ClientSocketWrap.onOpen()");
    }

    @Override
    protected void onClose(CloseCode code, String reason,
            boolean initiatedByRemote)
    {
        isClose = true;
        System.out.println("Ll_ClientSocketWrap.onClose()");
    }

    @Override
    protected void onMessage(WebSocketFrame message)
    {
        mListener.onMessage(this, new Ll_Message(message.getTextPayload()));
    }

    @Override
    protected void onPong(WebSocketFrame pong)
    {
        
    }

    @Override
    protected void onException(IOException exception)
    {
        exception.printStackTrace();
        isClose = true;
    }
}
