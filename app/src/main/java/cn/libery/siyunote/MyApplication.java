package cn.libery.siyunote;

import com.facebook.drawee.backends.pipeline.Fresco;

import org.litepal.LitePalApplication;

import cn.libery.siyunote.utils.ImagePipelineConfigFactory;

/**
 * Created by Libery on 2015/11/1.
 * Email:libery.szq@qq.com
 */
public class MyApplication extends LitePalApplication {

    private static MyApplication application;

    public static MyApplication getInstance() {

        if (application != null) {
            application = new MyApplication();
        }
        return application;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this, ImagePipelineConfigFactory.getImagePipelineConfig(this));
    }
}
