package com.llxx.socket.service;

/**

系统的云服务接口，通过调用这个接口可以返回系统相关的配置信息，以及可以设置系统的参数

*/
interface ISocketService
{

	/**
	* 发送信息
	*/
	void sendMessage(in String message);
	
	void sendMessageByHash(in String message, in int hash);
	
	void sendMessageExcludeHash(in String message, in int exincludehash);
	
	/**
	* 获取端口号
	*/
	String getPort();
}