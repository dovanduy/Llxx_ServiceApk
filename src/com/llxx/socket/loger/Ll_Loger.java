/**
 * 
 */
package com.llxx.socket.loger;

import com.llxx.service.BuildConfig;

import android.util.Log;

/**
 * @author 李万隆
 * @date   2015年5月5日
 */
public class Ll_Loger
{
    public static boolean LOGED = BuildConfig.DEBUG;
    public static String LOG_BUILD = "llxx_log:";

    /**
     * verbose 日志
     * @param tag
     * @param msg
     */
    public static final void v(Object tag, String msg)
    {
        if (LOGED)
            Log.v(tag.getClass().getSimpleName(), LOG_BUILD + msg);
    }

    /**
     * 信息日志
     * @param tag
     * @param msg
     */
    public static final void i(Object tag, String msg)
    {
        if (LOGED)
            Log.i(tag.getClass().getSimpleName(), LOG_BUILD + msg);
    }

    /**
     * 错误日志
     * @param tag
     * @param msg
     */
    public static final void e(Object tag, String msg)
    {
        Log.e(tag.getClass().getSimpleName(), LOG_BUILD + msg);
    }

    /**
     * 错误日志
     * @param tag
     * @param msg
     */
    public static final void d(Object tag, String msg)
    {
        if (LOGED)
            Log.d(tag.getClass().getSimpleName(), LOG_BUILD + msg);
    }

    /**
     * 输出E
     * @param tag
     * @param msg
     */
    public static final void e(Object tag, Exception msg)
    {
        if (LOGED)
        {
            //            Writer writer = new StringWriter();
            //            PrintWriter printWriter = new PrintWriter(writer);
            //            msg.printStackTrace(printWriter);
            //            Throwable cause = msg.getCause();
            //            while (cause != null)
            //            {
            //                cause.printStackTrace(printWriter);
            //                cause = cause.getCause();
            //            }
            //            printWriter.close();
            //            String result = writer.toString();
            //              Log.e(tag.getClass().getSimpleName(), LOG_BUILD + result);

            StackTraceElement[] es = msg.getStackTrace();
            for (int i = 0; i < es.length; i++)
            {
                Log.e(tag.getClass().getSimpleName(),
                        LOG_BUILD + es[i].getClassName() + "." + es[i].getMethodName() + ":" + es[i].getLineNumber());
            }
        }
    }
}
