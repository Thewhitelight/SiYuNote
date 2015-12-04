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

    public static final String NOTES_TYPE = "NOTES_TYPE";
    public static final int NOTES_ALL = 0;
    public static final int NOTES_WORK = 1;
    public static final int NOTES_LIFE = 2;
    public static final String EXTRA_MAX = "EXTRA_MAX";
    public static final String EXTRA_TIMESTAMP = "EXTRA_TIMESTAMP";
}
