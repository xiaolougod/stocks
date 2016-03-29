package com.life.stocks.Util;

import com.mysql.jdbc.Connection;
import java.sql.Statement;

public class _Mysqlcommon
{
  static MysqlConnectionPool pool = MysqlConnectionPool.getInstance();

  public static void insert(String sql) {
    try {
      Connection conn = pool.getConnection();

      Statement statement = conn.createStatement();

      statement.execute(sql);
      pool.release(conn);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}