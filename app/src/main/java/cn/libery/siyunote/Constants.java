package cn.libery.siyunote;

import android.os.Environment;

/**
 * Created by Libery on 2015/11/1.
 * Email:libery.szq@qq.com
 */
public class Constants {

    public static final String APP_NAME = "SiYuNote";
    public static final String FILE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + APP_NAME;
    public static final String IMAGE_PIPELINE_CACHE_DIR = APP_NAME;

    public static final String FIRST_START_MAIN = "share_key_first_start_app";// 是否首次启动应用主页
    public static final String NOTES_TYPE = "NOTES_TYPE";
    public static final int NOTES_ALL = 0;
    public static final int NOTES_WORK = 1;
    public static final int NOTES_LIFE = 2;
    public static final String EXTRA_MAX = "EXTRA_MAX";
    public static final String EXTRA_TIMESTAMP = "EXTRA_TIMESTAMP";
    public static final String VIEW_PAGER_POSITION = "VIEW_PAGER_POSITION";
    public static final String NOTE_TIME_STAMP = "NOTE_TIME_STAMP";
    public static final String LIST_TYPE = "RECYCLER_TYPE";
    public static final String LIST_LINEAR = "RECYCLER_LINEAR";
    public static final String LIST_GRID =  "RECYCLER_GRID";
}
