package org.tools.fx.library.demo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ThreadSafeSimpleDateFormat {

  // SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
  // SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS")

  private final static ThreadLocal<SimpleDateFormat> tl = new ThreadLocal<SimpleDateFormat>() {
    protected SimpleDateFormat initialValue() {
      // return new SimpleDateFormat("dd/MM/yyyy");
      return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
  };

  // http://my.oschina.net/huangyong/blog/159725
  public String formatDate(Date input) {
    if (input == null) {
      return null;
    }

    return tl.get().format(input);
  }

  public static void main(String[] args) {
    Date dt = new Date();
    String dt2 = "2020-07-24 20:10:33";
    System.out.println(tl.get().format(dt));

    try {
      Date date2 = tl.get().parse(dt2);
      System.out.println(tl.get().format(date2));
    } catch (ParseException e) {
      e.printStackTrace();
    }



    String testdata[] = {"2019-12-24 09:12:26", "1999-07-11 20:15:05", "1919-01-31 16:01:51"};
    Runnable r[] = new Runnable[testdata.length];
    for (int i = 0; i < r.length; i++) {
      final int i2 = i;
      r[i] = new Runnable() {
        public void run() {
          try {
            for (int j = 0; j < 10000; j++) {
              String str = testdata[i2];
              String str2 = null;
              /* synchronized(df) */ {
                Date d = tl.get().parse(str);
                str2 = tl.get().format(d);
                System.out.println("i: " + i2 + "\tj: " + j + "\tThreadID: "
                    + Thread.currentThread().getId() + "\tThreadName: "
                    + Thread.currentThread().getName() + "\t" + str + "\t" + str2);
              }
              if (!str.equals(str2)) {
                throw new RuntimeException("date conversion failed after " + j
                    + " iterations. Expected " + str + " but got " + str2);
              }
            }
          } catch (ParseException e) {
            throw new RuntimeException("parse failed");
          }
        }
      };
      new Thread(r[i]).start();
    }
  }

}
