package cn.libery.siyunote;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.litepal.LitePalApplication;

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
        super.onCreate();
        application = this;
        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration
                .Builder(this)
                .memoryCacheSize(2 * 1024 * 1024)
                .diskCacheSize(1024 * 1014 * 50)
                .build();
        ImageLoader.getInstance().init(configuration);
    }
}
