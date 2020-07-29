package org.tools.fx.library.tools;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FormatHelper {

    private static final double MB = 1024 * 1024.0;
    private static final double GB = 1024 * 1024 * 1024.0;
    private static final double TB = 1024 * 1024 * 1024 * 1024.0;
    // private static final long PB = TB * 1024;

    private final static ThreadLocal<SimpleDateFormat> tl = new ThreadLocal<SimpleDateFormat>() {
        protected SimpleDateFormat initialValue() {
            // return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
    };



    public static String format(long size) {
        // (Math.round(totalPrice*100))/100;
        if (size < MB) {
            return Math.round((size / 1024.0) * 100) / 100 + " KB";
        } else if (size < GB) {
            return Math.round((size / MB) * 100) / 100 + " MB";
        } else if (size < TB) {
            return Math.round((size / GB) * 100) / 100 + " GB";
        } else {
            return size + " KB";
        }
    }



    // http://my.oschina.net/huangyong/blog/159725
    public static String formatDate(Date dt) {
        if (dt == null) {
            return "";
        }
        return tl.get().format(dt);
    }

    public static String formatDate(long dt) {
        if (dt == 0) {
            return "";
        }
        return tl.get().format(new Date(dt));
    }
}
