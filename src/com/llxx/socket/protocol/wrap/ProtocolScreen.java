/**
 * 
 */
package com.llxx.socket.protocol.wrap;

import com.llxx.socket.protocol.Protocol;

import android.content.Context;
import android.view.WindowManager;

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

    @SuppressWarnings("deprecation")
    @Override
    public String getResult(Context context)
    {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        int height = wm.getDefaultDisplay().getHeight();
        return "getwh|" + width + "|" + height;
    }

}
