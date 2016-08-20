/**
 * 
 */
package com.llxx.socket.protocol.wrap;

import com.llxx.socket.protocol.Protocol;

import android.content.Context;

/**
 * @author 繁星
 * @describe 获取屏幕的宽高
 */
public class ProtocolScreen extends Protocol
{

    @Override
    public boolean prase()
    {
        return false;
    }

    @Override
    public String action()
    {
        return "getwh";
    }

    @Override
    public String getResult(Context context)
    {
        return null;
    }

}
