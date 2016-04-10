package com.life.stocks;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.regex.Pattern;

import com.life.stocks.Util.MysqlConnectionPool;
import com.life.stocks.Util.StocksattitudeUtil;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.ResultSet;
import com.mysql.jdbc.Statement;

public class Stocks_attitude_hxw {

	static {
		java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit").setLevel(Level.OFF);
		java.util.logging.Logger.getLogger("org.apache.http").setLevel(Level.OFF);
	}
	static MysqlConnectionPool pool = MysqlConnectionPool.getInstance();

	Connection conn = null;

	public static void main(String[] args) {
		Stocks_attitude_hxw s = new Stocks_attitude_hxw();
		List<String> list = s.getStocksCodeList("select * from stocks_name_code_mapping");
		List<String> sql_list = new ArrayList<String>();
		String regEx = "[[^0-9]&&[^_]&&[^.]]";
		Date date = new Date();
		SimpleDateFormat simpledate = new SimpleDateFormat("yyyy-MM-dd");
		String str_day = simpledate.format(date);
		Pattern p = Pattern.compile(regEx);
		for (String str_city : list) {
			String str = str_city.substring(2);
			
//			// 和讯网帖数
//			StocksattitudeUtil stocksattitudeUtil_hxw = new StocksattitudeUtil();
//			stocksattitudeUtil_hxw.setUrl("http://guba.hexun.com/" + str + ",guba.html");
//			String st = stocksattitudeUtil_hxw.getJsString("pageText").split(",")[0];
//			Matcher m = p.matcher(st);
//			String hxw_t = m.replaceAll("").trim();
//
//			// 东方财富热线帖数和关注数 
//			StocksattitudeUtil stocksattitudeUtil_df_g = new StocksattitudeUtil();
//			stocksattitudeUtil_df_g.setUrl("http://guba.eastmoney.com/list," + str + ".html");
//			String df_g = stocksattitudeUtil_df_g.getJsStringByClassName("strongc1",true);
//			String df_t = stocksattitudeUtil_df_g.getJsStringByClassName("pager",true).split(" ")[1];
//			System.out.println(df_g+"##############################"+df_t);
			//腾讯股票
			StocksattitudeUtil stocksattitudeUtil_tx_g = new StocksattitudeUtil();
			stocksattitudeUtil_tx_g.setUrl("http://gu.qq.com/" + str_city);
			String tx_up = stocksattitudeUtil_tx_g.getJsStringByCSS("#mod-comment  .bar_up .up",true);
			String tx_down = stocksattitudeUtil_tx_g.getJsStringByCSS("#mod-comment .bar_down .down",true);
			System.out.println(tx_up+"##############################"+tx_down);
		}

		// for (String sql : sql_list) {
		// System.out.println(sql);
		// _Mysqlcommon.insert(sql);
		// }
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
}
