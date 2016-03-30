package com.life.stocks;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.life.stocks._interface.Sina_Interface;

public class StocksDemonUtil
{
  public static void main(String[] args)
  {
    int count = 0;
    String[] array = { "000005", "002575", "sh000001", "600713" };
    while (true) {
      for (String str : array) {
        if (str.substring(0, 2).equals("00"))
          str = "sz" + str;
        else if (str.substring(0, 2).equals("60")) {
          str = "sh" + str;
        }
        String urlStr = "http://hq.sinajs.cn/list=" + str;
        String str_value = Sina_Interface.getURLContent(urlStr);
        String[] strvalue = str_value.split("=")[1].replace('"', ' ').split(",");
        Float kaipan = Float.valueOf(strvalue[2]);
        Float danqian = Float.valueOf(strvalue[3]);

        Float top = Float.valueOf(strvalue[4]);
        Float low = Float.valueOf(strvalue[5]);
        float lv1 = (danqian.floatValue() - kaipan.floatValue()) / kaipan.floatValue() * 100.0F;
        float lv_h = (low.floatValue() - kaipan.floatValue()) / kaipan.floatValue() * 100.0F;
        float lv_l = (top.floatValue() - kaipan.floatValue()) / kaipan.floatValue() * 100.0F;
        float num = Float.valueOf(strvalue[8]).floatValue() / 100.0F;
        float mon = Float.valueOf(strvalue[9]).floatValue() / 1.0E+008F;
        System.out.println(danqian + "*" + strvalue[0].substring(0, strvalue[0].length() / 2) + lv1 + "**" + 
          lv_h + "**" + lv_l + "**" + num + "**" + mon);
      }
      System.out.println("=======================================================" + count++);
      try {
        Thread th = new Thread();
        Thread.sleep(3000L);
      }
      catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }
}