package com.life.stocks;

import com.life.stocks.Util._Mysqlcommon;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

public class Stocks_code_name_mappoing {
	public static void main(String[] args) {
		String[] stocks_head_arry = { "sz300", "sh600", "sh601", "sh900", "sz000", "sz002", "sz200", "sh730", "sh700",
				"sz080", "sh580", "sz031" };
		String sql = "";
		for (String str : stocks_head_arry)
			for (int i = 0; i <= 999; i++) {
				String stocks_code = str + FomateStocks(i);
				String urlStr = "http://hq.sinajs.cn/list=" + stocks_code;
				String str_value = StocksDemonUtil.getURLContent(urlStr);
				String[] strvalue = str_value.split("=")[1].replace('"', ' ').split(",");
				if (strvalue.length > 3) {
					String code = stocks_code.substring(2);
					try {
						sql = "insert into stocks_name_code_mapping(stocks_code,stocks_name,stocks_sina_interface)values('"
								+ code + "','" + new String(strvalue[0].getBytes("UTF-8")) + "','" + stocks_code
								+ "');";
						_Mysqlcommon.insert(sql);
						System.out.println(sql);
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
				}
			}
	}

	public static String FomateStocks(int num) {
		String str = null;
		if (num < 10)
			str = "00" + num;
		else if ((num < 100) && (num > 10))
			str = "0" + num;
		else {
			str = "" + num;
		}
		return str;
	}
}