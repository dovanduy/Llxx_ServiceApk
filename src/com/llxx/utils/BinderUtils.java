/**
 * 
 */
package com.llxx.utils;

import com.llxx.socket.service.ISocketService;
import com.llxx.socket.service.ISocketService.Stub;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

/**
 * 
 * @author 李万隆
 * @qq 461051343
 * @date 2016年8月18日
 * @describe 绑定Socket服务
 *
 */
public class BinderUtils
{
    ISocketService mScoketService;
    Context context;

    /**
     * 
     */
    public BinderUtils(Context context)
    {
        this.context = context;
    }

    ServiceConnection mServiceConnect = new ServiceConnection()
    {
        @Override
        public void onServiceDisconnected(ComponentName name)
        {
            mScoketService = null;
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service)
        {
            mScoketService = ISocketService.Stub.asInterface(service);
        }
    };

    /**
     * 获取Socket服务
     * @return
     */
    public ISocketService getService()
    {
        if (mScoketService == null)
        {
            bind();
        }
        return mScoketService;
    }

    /**
     * 绑定
     */
    public void bind()
    {
        Intent intent = new Intent("com.llxx.socket.service.MAIN");
        intent = SecurityParent.createExplicitFromImplicitIntent(context,
                intent);
        if (intent != null)
        {
            try
            {
                // 绑定服务
                context.bindService(intent, mServiceConnect,
                        Context.BIND_AUTO_CREATE);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    /**
     * 取消绑定
     * @param context
     */
    public void unBinder(Context context)
    {
        if (mScoketService != null)
        {
            context.unbindService(mServiceConnect);
        }
    }
}
