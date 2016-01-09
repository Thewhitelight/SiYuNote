package cn.libery.siyunote.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import cn.libery.siyunote.Constants;

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

    /**
     * 首次启动主页
     */
    public static boolean isFirstStartMain() {
        return SharedPreferUtil.get(Constants.FIRST_START_MAIN);
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

    /**
     * recyclerView type is LIST_LINEAR
     */
    public static boolean isListLinear() {
        return SharedPreferUtil.get(Constants.LIST_TYPE, Constants.LIST_LINEAR).equals(Constants.LIST_LINEAR);
    }
}
