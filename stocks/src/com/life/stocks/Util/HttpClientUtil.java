package com.life.stocks.Util;

import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;

public class HttpClientUtil {
	public static void main(String[] args) throws IOException {
		HttpClient client = new HttpClient();
		// 设置代理服务器地址和端口
		// client.getHostConfiguration().setProxy("proxy_host_addr",proxy_port);
		// 使用GET方法，如果服务器需要通过HTTPS连接，那只需要将下面URL中的http换成https
		// HttpMethod method = new
		// GetMethod("http://10.1.14.20:8088/workflowController/service/todo/addTask");
		// 使用POST方法
		String url = "http://web.group.finance.qq.com/newstockgroup/webRssService/getAttitudeCount";
		PostMethod method = new PostMethod(url);
		method.setParameter("Content-Type", "application/x-www-form-urlencoded");
		method.setParameter("Content-Length", "68");
//		code=sz002575&openid=anonymous&_callback=postimul1j8968&_appName=web
        NameValuePair[] param = { new NameValuePair("code","sz002575"),
                new NameValuePair("openid","anonymous"),
                new NameValuePair("_callback","postimul1j8968"),
                new NameValuePair("_appName","web") } ;
        method.setRequestBody(param);
		try {
			client.executeMethod(method);
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 打印服务器返回的状态
		System.out.println(method.getResponseBodyAsString());
		// 释放连接
		method.releaseConnection();
	}
}
