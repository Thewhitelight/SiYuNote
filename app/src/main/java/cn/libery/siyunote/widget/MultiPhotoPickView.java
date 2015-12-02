package cn.libery.siyunote.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.libery.siyunote.R;
import cn.libery.siyunote.utils.ImageLoaderOptions;

/**
 * Created by Libery on 2015/11/10.
 * Email:libery.szq@qq.com
 */
public class MultiPhotoPickView extends LinearLayout {

    @Bind(R.id.img_1)
    ImageView img1;
    @Bind(R.id.img_2)
    ImageView img2;
    @Bind(R.id.img_3)
    ImageView img3;
    @Bind(R.id.img_4)
    ImageView img4;
    @Bind(R.id.img_5)
    ImageView img5;
    @Bind(R.id.img_6)
    ImageView img6;

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
        ButterKnife.bind(this);
    }

    public void setImages(List<String> images) {
        ImageView[] imageViews = {img1, img2, img3, img4, img5, img6};
        int size = images.size();
        for (ImageView image : imageViews) {
            image.setVisibility(GONE);
        }
        for (int i = 0; i < size; i++) {
            imageViews[i].setVisibility(VISIBLE);
            ImageLoader.getInstance().displayImage(images.get(i), imageViews[i], ImageLoaderOptions.getOptions());
        }
    }

}
