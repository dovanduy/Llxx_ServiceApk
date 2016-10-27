package com.llxx.socket.wrap;

import java.util.ArrayList;
import java.util.List;

import org.nanohttpd.protocols.http.IHTTPSession;
import org.nanohttpd.protocols.websockets.NanoWSD;
import org.nanohttpd.protocols.websockets.WebSocket;
import org.nanohttpd.samples.websockets.DebugWebSocketServer;

import com.llxx.socket.config.Configs;
import com.llxx.socket.loger.Ll_Loger;

/**
 * 
 * @author fanxin, eachen
 * @qq 461051343
 * @date 2016年8月18日
 * @describe 封装Socket服务端
 */
public class Ll_SocketServiceWrap extends NanoWSD
{
    static final String TAG = "SocketServiceWrap";
    private static final int PORT = 8082;
    private List<Ll_ClientSocketWrap> mList = new ArrayList<Ll_ClientSocketWrap>();

    Ll_MessageListener mMessageListener;

    public Ll_SocketServiceWrap(Ll_MessageListener listener)
    {
        super(PORT);
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
            if (mSocket.hashCode() != exclude
                    && (hash == mSocket.hashCode() || hash == 0))
            {
                if (!mSocket.isClose())
                {
                    mSocket.sendmsg(msg);
                    if (Configs.DEBUG_SOCKETSERVICEWRAP_SEND_SHORT)
                        Ll_Loger.d(TAG, "sendMessage->" + (msg.length() > 200
                                ? msg.substring(0, 200) : msg));
                    else
                        Ll_Loger.d(TAG, "sendMessage->" + msg);
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
        if (mList.contains(wrap))
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

    @Override
    protected WebSocket openWebSocket(IHTTPSession handshake)
    {
        Ll_ClientSocketWrap wrap = new Ll_ClientSocketWrap(handshake,
                mMessageListener);
        System.out.println("Ll_SocketServiceWrap.openWebSocket()");
        mList.add(wrap);
        return wrap;
    }
}
