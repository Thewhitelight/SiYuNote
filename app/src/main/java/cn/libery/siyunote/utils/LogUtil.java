package cn.libery.siyunote.utils;

import android.util.Log;

import cn.libery.siyunote.BuildConfig;


/**
 * Created by Libery on 2015/7/27.
 * Email:libery.szq@qq.com
 */
public class LogUtil {

    public static int LEVEL;

    public LogUtil() {
        if (BuildConfig.DEBUG) {
            LEVEL = 5;
        } else {
            LEVEL = -1;
        }
    }


    public final static int VERBOSE = 4;

    public final static int DEBUG = 3;

    public final static int INFO = 2;

    public final static int WARN = 1;

    public final static int ERROR = 0;


    public static void v(String tag, String msg) {
        if (LEVEL >= VERBOSE) {
            Log.v(tag, msg);
        }
    }

    public static void d(String tag, String msg) {
        if (LEVEL >= DEBUG) {
            Log.d(tag, msg);
        }
    }

    public static void i(String tag, String msg) {
        if (LEVEL >= INFO) {
            Log.i("***" + tag + "***", msg);
        }
    }

    public static void w(String tag, String msg) {
        if (LEVEL >= WARN) {
            Log.w(tag, msg);
        }
    }

    public static void e(String tag, String msg) {
        if (LEVEL >= ERROR) {
            Log.e(tag, msg);
        }
    }


    public static void v(String msg) {
        if (LEVEL >= VERBOSE) {
            v(BuildConfig.APPLICATION_ID, msg);
        }
    }

    public static void d(String msg) {
        if (LEVEL >= DEBUG) {
            d(BuildConfig.APPLICATION_ID, msg);
        }
    }

    public static void i(String msg) {
        if (LEVEL >= INFO) {
            i(BuildConfig.APPLICATION_ID, msg);
        }
    }

    public static void w(String msg) {
        if (LEVEL >= WARN) {
            w(BuildConfig.APPLICATION_ID, msg);
        }
    }

    public static void e(String msg) {
        if (LEVEL >= ERROR) {
            e(BuildConfig.APPLICATION_ID, msg);
        }
    }

}
