package cn.libery.siyunote.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

import cn.libery.siyunote.R;
import cn.libery.siyunote.utils.ImageLoaderOptions;

/**
 * Created by Libery on 2015/11/10.
 * Email:libery.szq@qq.com
 */
public class MultiPhotoPickView extends LinearLayout {

    private ImageView image1, image2, image3, image4, image5, image6;

    public MultiPhotoPickView(Context context) {
        super(context);
        init(context);
    }


    public MultiPhotoPickView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.layout_multi_photo_view, this);
        image1 = (ImageView) findViewById(R.id.img_1);
        image2 = (ImageView) findViewById(R.id.img_2);
        image3 = (ImageView) findViewById(R.id.img_3);
        image4 = (ImageView) findViewById(R.id.img_4);
        image5 = (ImageView) findViewById(R.id.img_5);
        image6 = (ImageView) findViewById(R.id.img_6);
    }

    public void setImages(ArrayList<String> images) {
        ImageView[] imageViews = {image1, image2, image3, image4, image5, image6};
        int size = images.size();
        for (int i = 0; i < size; i++) {
            ImageLoader.getInstance().displayImage(images.get(i), imageViews[i], ImageLoaderOptions.getOptions());
        }
        if (size < 6) {
            imageViews[size + 1].setBackgroundResource(R.drawable.bg_img_add);
        }
    }

}
