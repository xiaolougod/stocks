package com.life.stocks.Util;

import java.io.IOException;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;

public class HttpClientPostUtil {

	String responseStr;

	public HttpClientPostUtil(String url, Map<String, String> headsParamMap, NameValuePair[] postBody) {
		HttpClient client = new HttpClient();
		PostMethod method = new PostMethod(url);
		for (String key : headsParamMap.keySet()) {
			method.setParameter(key, headsParamMap.get(key));
		}
		method.setRequestBody(postBody);
		try {
			client.executeMethod(method);
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
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

// public static void main(String[] args) throws IOException {
// HttpClient client = new HttpClient();
// // ���ô����������ַ�Ͷ˿�
// // client.getHostConfiguration().setProxy("proxy_host_addr",proxy_port);
// // ʹ��GET����������������Ҫͨ��HTTPS���ӣ���ֻ��Ҫ������URL�е�http����https
// // HttpMethod method = new
// //
// GetMethod("http://10.1.14.20:8088/workflowController/service/todo/addTask");
// // ʹ��POST����
// String url =
// "http://web.group.finance.qq.com/newstockgroup/webRssService/getAttitudeCount";
// PostMethod method = new PostMethod(url);
// method.setParameter("Content-Type", "application/x-www-form-urlencoded");
// method.setParameter("Content-Length", "68");
// // code=sz002575&openid=anonymous&_callback=postimul1j8968&_appName=web
// NameValuePair[] param = { new NameValuePair("code", "sz000005"), new
// NameValuePair("openid", "anonymous"),
// new NameValuePair("_callback", "postimul1j8968"), new
// NameValuePair("_appName", "web") };
// method.setRequestBody(param);
// try {
// client.executeMethod(method);
// } catch (HttpException e) {
// e.printStackTrace();
// } catch (IOException e) {
// e.printStackTrace();
// }
// String responseStr = method.getResponseBodyAsString();
// // ��ӡ���������ص�״̬
// System.out.println(responseStr);
// // �ͷ�����
// method.releaseConnection();
// }
