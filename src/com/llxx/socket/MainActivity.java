package com.llxx.socket;

import com.llxx.service.R;
import com.llxx.socket.loger.Llxx_Loger;
import com.llxx.socket.service.SocketServerBinderUtils;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class MainActivity extends Activity implements OnClickListener
{
    static final String TAG = "MainActivity";
    SocketServerBinderUtils mBinderUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mBinderUtils = new SocketServerBinderUtils(getApplicationContext());
        mBinderUtils.bind();

        setContentView(R.layout.socket_control_layout);

        findViewById(R.id.socket_send).setOnClickListener(this);
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
                mBinderUtils.getService().sendMessage("socket_send");
                Llxx_Loger.LogD(TAG, "socket_send->socket_send");
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
