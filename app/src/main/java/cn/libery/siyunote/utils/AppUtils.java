package cn.libery.siyunote.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * Created by Libery on 2015/11/29.
 * Email:libery.szq@qq.com
 */
public class AppUtils {

    public static PackageInfo getPackageInfo(Context context) {
        PackageInfo packInfo = null;
        try {
            PackageManager packageManager = context.getPackageManager();
            packInfo = packageManager.getPackageInfo(context.getPackageName(), PackageManager.GET_ACTIVITIES);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return packInfo;
    }

    public static <T> T getMetaData(Context context, String name) {
        try {
            ApplicationInfo ai = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            if (ai.metaData != null) {
                return (T) ai.metaData.get(name);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getNoteType(int noteType) {
        String noteTypeStr = "";
        switch (noteType) {
            case 0:
                noteTypeStr = "全部";
                break;
            case 1:
                noteTypeStr = "工作";
                break;
            case 2:
                noteTypeStr = "生活";
                break;
        }
        return noteTypeStr;
    }
}
