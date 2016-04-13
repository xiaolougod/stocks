package com.life.stocks;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.lang3.text.StrMatcher;

import com.life.stocks.Util.HttpClientPostUtil;
import com.life.stocks.Util.MysqlConnectionPool;
import com.life.stocks.Util.StocksattitudeUtil;
import com.life.stocks.Util._Mysqlcommon;
import com.life.stocks.businessachieve.StocksAttitudeAchieve;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.ResultSet;
import com.mysql.jdbc.Statement;

public class Stocks_attitude {

	static {
		java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit").setLevel(Level.OFF);
		java.util.logging.Logger.getLogger("org.apache.http").setLevel(Level.OFF);
	}
	static MysqlConnectionPool pool = MysqlConnectionPool.getInstance();

	Connection conn = null;

	public static void main(String[] args) {
		List<String> list_str = null;
		Stocks_attitude s = new Stocks_attitude();
		List<String> list = s.getStocksCodeList("select * from stocks_name_code_mapping");
		List<String> sql_list = new ArrayList<String>();
		Date date = new Date();
		SimpleDateFormat simpledate = new SimpleDateFormat("yyyy-MM-dd");
		String str_day = simpledate.format(date);
		for (String str_city : list) {
			Map<String, List<String>> map = new HashMap<String, List<String>>();
			StocksAttitudeAchieve a = new StocksAttitudeAchieve();
			try {
				a.stocks_attitude_hxw(map, str_city);
			} catch (Exception e) {
				addnone(map,str_city,1);
			}
			try {
				a.stocks_attitude_dfzq(map, str_city);
			} catch (Exception e) {
				addnone(map,str_city,2);
			}
			try {
				a.stocks_attitude_QQ(map, str_city);
			} catch (Exception e) {
				addnone(map,str_city,2);
			}
			try {
				a.stocks_attitude_ths(map, str_city);
			} catch (Exception e) {
				addnone(map,str_city,1);
			}
			list_str = map.get(str_city);
			if (list_str != null) {
				String sql_prefix = "insert into stocks_attitude(stocks_code,stocks_attitude_hxw,stocks_attitude_dfcfw_guanzhu,stocks_attitude_dfcfw_tieshu,stocks_attitude_tx_kankao,stocks_attitude_tx_kankong,stocks_attitude_ths_guanzhu,stocks_date)values";
				String sql = sql_prefix + "(" + str_city.substring(2) + "," + list_str.get(0) + "," + list_str.get(1)
						+ "," + list_str.get(2) + "," + list_str.get(3) + "," + list_str.get(4) + "," + list_str.get(5)
						+ ",'" + str_day + "');";
				_Mysqlcommon.insert(sql);
			}
		}
	}

	public List<String> getStocksCodeList(String query_sql) {
		ResultSet rs = null;
		List<String> list = new ArrayList<String>();
		try {
			this.conn = pool.getConnection();
			Statement statement = (Statement) this.conn.createStatement();
			rs = (ResultSet) statement.executeQuery(query_sql);
			while (rs.next()) {
				list.add(rs.getString("stocks_sina_interface"));
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			pool.release(this.conn);
		}
		return list;
	}

	/**
	 *@authorloukangwei
	 *@date2016年4月13日 下午5:14:46
	 *@returnTypevoid
	 *@desc添加报错时处理数据为空的功能
	 */
	public static void addnone(Map<String, List<String>> map, String str_city,int n) {
		List<String> _list = map.get(str_city);
		if (_list == null) {
			_list = new ArrayList<String>();
		}
		for(int i=0;i<n;i++)
		{
			_list.add("null");
		}
	}
}
