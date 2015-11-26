package cn.libery.siyunote.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;

import cn.libery.siyunote.R;
import cn.libery.siyunote.utils.PixelUtils;

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
        LayoutInflater.from(context).inflate(R.layout.layout_multi_photo_view, null);
        image1 = (ImageView) findViewById(R.id.img_1);
        image2 = (ImageView) findViewById(R.id.img_2);
        image3 = (ImageView) findViewById(R.id.img_3);
        image4 = (ImageView) findViewById(R.id.img_4);
        image5 = (ImageView) findViewById(R.id.img_5);
        image6 = (ImageView) findViewById(R.id.img_6);
        int with = PixelUtils.getWith();
        LayoutParams params = new LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        int margin = PixelUtils.px2dp(30);
        params.height = PixelUtils.px2dp(50);
        params.width = with / 3;
        params.setMargins(margin, margin, margin, margin);
        image1.setLayoutParams(params);
        image2.setLayoutParams(params);
        image3.setLayoutParams(params);
        image4.setLayoutParams(params);
        image5.setLayoutParams(params);
        image6.setLayoutParams(params);
    }

    public void setImages(ArrayList<String> images) {
        ImageView[] imageViews = {image1, image2, image3, image4, image5, image6};
        for (int i = 0; i < images.size(); i++) {

        }
    }

}
