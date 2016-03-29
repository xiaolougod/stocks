package com.life.stocks.Util;

import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Vector;

public class MysqlConnectionPool
{
  private Vector<Connection> pool;
  private String url;
  private String username;
  private String password;
  private String driverClassName;
  private int poolSize = 1;

  private static MysqlConnectionPool instance = null;

  private MysqlConnectionPool()
  {
    init();
  }

  private void init()
  {
    this.pool = new Vector(this.poolSize);
    readConfig();
    addConnection();
  }

  public synchronized void release(Connection conn)
  {
    this.pool.add(conn);
  }

  public synchronized void closePool()
  {
    for (int i = 0; i < this.pool.size(); i++) {
      try {
        ((Connection)this.pool.get(i)).close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
      this.pool.remove(i);
    }
  }

  public static MysqlConnectionPool getInstance()
  {
    if (instance == null) {
      instance = new MysqlConnectionPool();
    }
    return instance;
  }

  public synchronized Connection getConnection()
  {
    if (this.pool.size() > 0) {
      Connection conn = (Connection)this.pool.get(0);
      this.pool.remove(conn);
      return conn;
    }
    return null;
  }

  private void addConnection()
  {
    Connection conn = null;
    for (int i = 0; i < this.poolSize; i++)
      try
      {
        Class.forName(this.driverClassName);
        conn = (Connection)DriverManager.getConnection(this.url, this.username, this.password);
        this.pool.add(conn);
      }
      catch (ClassNotFoundException e) {
        e.printStackTrace();
      } catch (SQLException e) {
        e.printStackTrace();
      }
  }

  private void readConfig()
  {
    this.driverClassName = "com.mysql.jdbc.Driver";
    this.username = "root";
    this.password = "156967043021";
    this.url = "jdbc:mysql://120.25.198.92:3306/db_gp";
    this.poolSize = 4;
  }
}