package com.lou.crawlerCorrelation.base.AsynchronizationIO;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Map;

/**
 * @author loukangwei
 * @typename AsynchronizationIO
 * @desc 异步IO NIO 实现爬虫网页的下载
 */
public class AsynchronizationIO {

	public static Selector sel = null;

	public static Map<SocketChannel, String> sc2Path = new HashMap<SocketChannel, String>();

	public static void setConnect(String ip, String path, int port) {
		SocketChannel client;
		try {
			client = SocketChannel.open();
			client.configureBlocking(false);
			client.connect(new InetSocketAddress(ip, port));
			client.register(sel, SelectionKey.OP_CONNECT | SelectionKey.OP_READ);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
	
	public static void main(String[] args)
	{
		try {
			sel= Selector.open();
			setConnect("www.baidu.com","/index.jsp");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
