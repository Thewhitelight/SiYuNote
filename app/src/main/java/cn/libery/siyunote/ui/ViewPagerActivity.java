package cn.libery.siyunote.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import cn.libery.siyunote.Intents;
import cn.libery.siyunote.R;
import cn.libery.siyunote.utils.ImageLoaderOptions;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by Libery on 2015/11/29.
 * Email:libery.szq@qq.com
 */
public class ViewPagerActivity extends Activity {

    private static final String EXTRA_URLS = "extra_urls";
    private static final String EXTRA_INDEX = "extra_index";

    private List<String> mUrls;
    private int mIndex;

    public static Intent intent(Context context, ArrayList<String> urls, int index) {
        return new Intents.Builder().setClass(context, ViewPagerActivity.class)
                .add(EXTRA_URLS, urls).add(EXTRA_INDEX, index).toIntent();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);

        ViewPager viewPager = (ViewPager) findViewById(R.id.vp_pic);
        if (getIntent() != null) {
            mUrls = getIntent().getStringArrayListExtra(EXTRA_URLS);
            mIndex = getIntent().getIntExtra(EXTRA_INDEX, 0);
        }
        viewPager.setAdapter(new ViewPagerAdapter());
        viewPager.setCurrentItem(mIndex);
    }

    public class ViewPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mUrls.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            PhotoView photoView = new PhotoView(container.getContext());
            ImageLoader.getInstance().displayImage(mUrls.get(position), photoView, ImageLoaderOptions.getOptions());
            container.addView(photoView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            PhotoViewAttacher mAttache = new PhotoViewAttacher(photoView);
            mAttache.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
                @Override
                public void onPhotoTap(View view, float v, float v1) {
                    finish();
                }
            });
            return photoView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

}
