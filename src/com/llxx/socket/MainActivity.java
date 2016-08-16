package com.llxx.socket;

import com.llxx.service.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class MainActivity extends Activity implements OnClickListener
{

    Thread mSocketThread;
    SocketRunnable mRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.socket_control_layout);
        mRunnable = new SocketRunnable();
        mSocketThread = new Thread(mRunnable);
        mSocketThread.start();

        findViewById(R.id.socket_send).setOnClickListener(this);
    }

    /**
     * 运行Socket的线程
     * @describe 
     */
    class SocketRunnable implements Runnable
    {
        SocketService mService = null;

        @Override
        public void run()
        {
            mService = new SocketService();
            mService.run();
        }

        public SocketService getService()
        {
            return mService;
        }

    }

    int start = 0;

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
        case R.id.socket_send:
            try
            {
                System.out.println("MainActivity.onClick()->" + mRunnable.getService());
                mRunnable.getService().sendMessage("test " + (start++));
                System.out.println("MainActivity.onClick()-->" + (start++));
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            break;

        default:
            break;
        }
    }
}
