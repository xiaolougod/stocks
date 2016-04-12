package com.life.stocks._interface;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author loukangwei
 * @typename Sina_Interface
 * @desc 调用新浪股票详细数据接口
 */
public class Sina_Interface {
	public static String getURLContent(String urlStr) {
		URL url = null;
		BufferedReader in = null;
		StringBuffer sb = new StringBuffer();
		try {
			url = new URL(urlStr);
			in = new BufferedReader(new InputStreamReader(url.openStream(), "GBK"));
			String str = null;
			while ((str = in.readLine()) != null)
				sb.append(str);
		} catch (Exception localException) {
			localException.printStackTrace();
		} finally {
			try {
				if (in != null)
					in.close();
			} catch (IOException localIOException1) {
			}
		}
		String result = sb.toString();
		return result;
	}
}
