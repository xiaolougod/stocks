package com.life.stocks.Util;

import java.io.IOException;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;

public class HttpClientGetUtil {

	String responseStr;

	public HttpClientGetUtil(String url) {
		HttpClient client = new HttpClient();
		GetMethod method = new GetMethod(url);
		try {
			client.executeMethod(method);
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		responseStr = method.getResponseBodyAsString();
		method.releaseConnection();
	}

	public String getResponseStr() {
		return responseStr;
	}

	public void setResponseStr(String responseStr) {
		this.responseStr = responseStr;
	}
}

