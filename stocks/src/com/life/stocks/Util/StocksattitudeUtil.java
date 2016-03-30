package com.life.stocks.Util;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

public class StocksattitudeUtil {

	private String url;

	public String getJsString(String role) {
		HtmlUnitDriver driver = new HtmlUnitDriver();
		driver.setJavascriptEnabled(true);
		driver.get(url);
		WebElement element = driver.findElement(By.className(role));
		return element.getText();
	}

	public static void main(String[] args) {
		// ºÍÑ¶Íø
//		StocksattitudeUtil s_attitude = new StocksattitudeUtil();
//		 s_attitude.setUrl("http://guba.hexun.com/000007,guba.html");
//		 String st=s_attitude.getJsString("pageText");
//		 System.out.print(st);
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
