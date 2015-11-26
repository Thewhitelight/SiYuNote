package cn.libery.siyunote.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Libery on 2015/11/26.
 * Email:libery.szq@qq.com
 */
public class TimeUtils {

    private static final SimpleDateFormat sdf5 = new SimpleDateFormat("yyyy年MM月dd日", Locale.getDefault());

    private static final SimpleDateFormat sdf4 = new SimpleDateFormat("HH:mm", Locale.getDefault());

    public static String hhMM(long timeInMils) {
        return sdf4.format(new Date(timeInMils));
    }

    public static String yyyyMMddChinese(long date) {
        return sdf5.format(new Date(date));
    }
}
