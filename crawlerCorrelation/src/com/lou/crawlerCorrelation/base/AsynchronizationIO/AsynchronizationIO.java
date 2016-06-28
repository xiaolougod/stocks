package com.lou.crawlerCorrelation.base.AsynchronizationIO;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.sleepycat.je.tree.Key;

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

	public static void main(String[] args) {
		try {
			sel = Selector.open();
			// add download page
			setConnect("www.baidu.com", "/index.jsp", 80);
			setConnect("hao123.com", "/book.htm", 80);
			while (!sel.keys().isEmpty()) {
				if (sel.select(100) > 0) {
					Iterator<SelectionKey> it = sel.selectedKeys().iterator();
					while (it.hasNext()) {
						SelectionKey key = it.next();
						it.remove();
						processsSelectionKey(key);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}

	public static void processsSelectionKey(SelectionKey selkey) throws IOException {
		SocketChannel sChannel = (SocketChannel) selkey.channel();
		if (selkey.isValid() && selkey.isConnectable()) {
			boolean success = sChannel.finishConnect();
			if (!success) {
				selkey.cancel();
			}
			sendMessage(sChannel, "GET " + sc2Path.get(sChannel) + " HTTP/1.0\r\nAccept: */*\r\n\r\n");
		} else if (selkey.isReadable()) {
			String ret = readMessage(sChannel);
			if (ret != null && ret.length() > 0) {
				System.out.println(ret);
			} else {
				selkey.cancel();
			}
		}
	}

	/**
	 * @authorloukangwei
	 * @date2016年6月21日 下午5:03:07
	 * @returnTypeString
	 * @desc下载网页
	 */
	public static String readMessage(SocketChannel client) {
		String result = null;
		ByteBuffer buf = ByteBuffer.allocate(1024);

		try {
			int i = client.read(buf);
			buf.flip();
			if (1 != -1) {
				result = new String(buf.array(), 0, i);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * @authorloukangwei
	 * @date2016年6月21日 下午5:14:34
	 * @returnTypeboolean
	 * @desc发送http请求
	 */
	public static boolean sendMessage(SocketChannel client, String message) {
		try {
			ByteBuffer buf = ByteBuffer.allocate(1024);
			buf = ByteBuffer.wrap(message.getBytes());
		} catch (Exception e) {
			return true;
		}
		return false;
	}
}
