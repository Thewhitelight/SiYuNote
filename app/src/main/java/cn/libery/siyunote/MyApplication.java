package cn.libery.siyunote;

import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.litepal.LitePalApplication;

import java.io.File;

/**
 * Created by Libery on 2015/11/1.
 * Email:libery.szq@qq.com
 */
public class MyApplication extends LitePalApplication {

    private static MyApplication application;

    public static MyApplication getInstance() {
        return application;
    }

    @Override
    public void onCreate() {
        SpeechUtility.createUtility(this, ("appid=" + getString(R.string.iflytek_id)) + "," + SpeechConstant.ENGINE_MODE + "=" + SpeechConstant.MODE_MSC);
        super.onCreate();
        application = this;
        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration
                .Builder(this)
                .diskCache(new UnlimitedDiskCache(new File(Constants.FILE_PATH)))
                .memoryCacheSize(2 * 1024 * 1024)
                .diskCacheSize(1024 * 1014 * 50)
                .build();
        ImageLoader.getInstance().init(configuration);
    }
}
