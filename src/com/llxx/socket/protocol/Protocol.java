/**
 * 
 */
package com.llxx.socket.protocol;

import com.llxx.command.Command;
import com.llxx.socket.service.Ll_SocketService;
import com.llxx.socket.wrap.Ll_ClientSocketWrap;

/**
 * @author 繁星
 * @describe 协议数据协议分装
 */
public abstract class Protocol extends Command
{
    public abstract void doAction(Ll_ClientSocketWrap wrap, Ll_SocketService service);
    
    public Protocol()
    {
        setAction(action());
    }
}
