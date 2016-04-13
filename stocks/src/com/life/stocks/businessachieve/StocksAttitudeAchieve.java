package com.life.stocks.businessachieve;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.httpclient.NameValuePair;

import com.life.stocks.Util.HttpClientGetUtil;
import com.life.stocks.Util.HttpClientPostUtil;
import com.life.stocks.Util.StocksattitudeUtil;

/**
 * @author loukangwei
 * @typename StocksAttitudeAchieve
 * @desc 各个网站具体实现
 */
public class StocksAttitudeAchieve {
	/**
	 * @authorloukangwei
	 * @date2016年4月11日 下午2:48:01
	 * @returnTypeMap<String,List<String>>
	 * @desc和讯网
	 */
	public Map<String, List<String>> stocks_attitude_hxw(Map<String, List<String>> map, String str_city) {
		String str = str_city.substring(2);
		String regEx = "[[^0-9]&&[^_]&&[^.]]";
		Pattern p = Pattern.compile(regEx);
		StocksattitudeUtil stocksattitudeUtil_hxw = new StocksattitudeUtil();
		stocksattitudeUtil_hxw.setUrl("http://guba.hexun.com/" + str + ",guba.html");
		String st = stocksattitudeUtil_hxw.getJsStringByClassName("pageText", false).split(",")[0];
		Matcher m = p.matcher(st);
		String hxw_t = m.replaceAll("").trim();
		List<String> list = map.get(str_city);
		if (list == null) {
			list = new ArrayList<String>();
		}
		list.add(hxw_t);
		map.put(str_city, list);
		return map;
	}

	/**
	 * @authorloukangwei
	 * @date2016年4月11日 下午3:03:14
	 * @returnTypeMap<String,List<String>>
	 * @desc关注度+帖数
	 */
	public Map<String, List<String>> stocks_attitude_dfzq(Map<String, List<String>> map, String str_city) {
		String str = str_city.substring(2);
		StocksattitudeUtil stocksattitudeUtil_df_g = new StocksattitudeUtil();
		stocksattitudeUtil_df_g.setUrl("http://guba.eastmoney.com/list," + str + ".html");
		// 关注度
		String df_g = stocksattitudeUtil_df_g.getJsStringByClassName("strongc1", true);
		// 帖数
		String df_t = stocksattitudeUtil_df_g.getJsStringByClassName("pager", true).split(" ")[1];
		List<String> list = map.get(str_city);
		if (list == null) {
			list = new ArrayList<String>();
		}
		list.add(df_g);
		list.add(df_t);
		map.put(str_city, list);
		return map;
	}

	/**
	 * @authorloukangwei
	 * @date2016年4月11日 下午3:09:40
	 * @returnTypeMap<String,List<String>>
	 * @descQQ股票看好看空
	 */
	public Map<String, List<String>> stocks_attitude_QQ(Map<String, List<String>> map, String str_city) {
		String url = "http://web.group.finance.qq.com/newstockgroup/webRssService/getAttitudeCount";
		Map<String, String> headmap = new HashMap<String, String>();
		headmap.put("Content-Type", "application/x-www-form-urlencoded");
		headmap.put("Content-Length", "68");
		NameValuePair[] param = { new NameValuePair("code", str_city), new NameValuePair("openid", "anonymous"),
				new NameValuePair("_callback", "postimul1j8968"), new NameValuePair("_appName", "web") };
		String responseStr = new HttpClientPostUtil(url, headmap, param).getResponseStr();
		String kangduo_m = responseStr.substring(responseStr.indexOf("kanduo") + 9);
		String kangduo = kangduo_m.substring(0, kangduo_m.indexOf("\""));
		String kangkong_m = responseStr.substring(responseStr.indexOf("kankong") + 10);
		String kangkong = kangkong_m.substring(0, kangkong_m.indexOf("\""));
		List<String> list = map.get(str_city);
		if (list == null) {
			list = new ArrayList<String>();
		}
		list.add(kangduo);
		list.add(kangkong);
		map.put(str_city, list);
		return map;
	}

	/**
	 *@authorloukangwei
	 *@date2016年4月12日 下午2:44:57
	 *@returnTypeMap<String,List<String>>
	 *@desc同花顺股票信息(HtmlUnitDriver)对js的支持好改换城httpclient
	 */
	public Map<String, List<String>> stocks_attitude_ths(Map<String, List<String>> map, String str_city) {
		String str = str_city.substring(2);
		String url="http://t.10jqka.com.cn/guba/" + str + "/";
		String responseStr = new HttpClientGetUtil(url).getResponseStr();
		String split_reg="<span class=\"fs16 redtext\">";
		String guanzhu = responseStr.substring(responseStr.indexOf(split_reg) + split_reg.length()).split("</span>")[0];
		List<String> list = map.get(str_city);
		if (list == null) {
			list = new ArrayList<String>();
		}
		list.add(guanzhu);
		map.put(str_city, list);
		return map;
	}

}
