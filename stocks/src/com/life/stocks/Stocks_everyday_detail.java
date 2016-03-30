package com.life.stocks;

import com.life.stocks.Util.MysqlConnectionPool;
import com.life.stocks.Util._Mysqlcommon;
import com.life.stocks._interface.Sina_Interface;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.ResultSet;
import com.mysql.jdbc.Statement;
import java.io.PrintStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 		loukangwei
 * @typename	Stocks_everyday_detail
 * @desc		获取股票每日价格交易量等数据
 */
public class Stocks_everyday_detail {
	static MysqlConnectionPool pool = MysqlConnectionPool.getInstance();

	Connection conn = null;

	public static void main(String[] args) {
		Stocks_everyday_detail s = new Stocks_everyday_detail();
		List<String> list = s.getStocksCodeList("select * from stocks_name_code_mapping");
		List<String> sql_list = new ArrayList<String>();
		String sql_pre = "insert into stocks_detail(stocks_code,stocks_local_start,stocks_local_end,stock_highest,stocks_lowest,stocks_conclude_num,stocks_conclude_price,date) values";
		int count = 0;
		for (String str : list) {
			String urlStr = "http://hq.sinajs.cn/list=" + str;
			String str_value = Sina_Interface.getURLContent(urlStr);
			String[] strvalue = str_value.split("=")[1].replace('"', ' ').split(",");
			sql_list.add(sql_pre + "('" + str.substring(2) + "','" + strvalue[1] + "','" + strvalue[2] + "','"
					+ strvalue[4] + "','" + strvalue[5] + "','" + strvalue[8] + "','" + strvalue[9] + "','"
					+ strvalue[30] + "');");

			System.out.println(count++);
		}

		for (String sql : sql_list) {
			System.out.println(sql);
			_Mysqlcommon.insert(sql);
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
}