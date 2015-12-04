package cn.libery.siyunote.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.libery.siyunote.Constants;
import cn.libery.siyunote.Intents;
import cn.libery.siyunote.R;
import cn.libery.siyunote.db.EventRecord;
import cn.lightsky.infiniteindicator.InfiniteIndicatorLayout;
import cn.lightsky.infiniteindicator.slideview.BaseSliderView;
import cn.lightsky.infiniteindicator.slideview.DefaultSliderView;

/**
 * Created by Libery on 2015/12/4.
 * Email:libery.szq@qq.com
 */
public class NoteDetailActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;
    @Bind(R.id.appbar)
    AppBarLayout appbar;
    @Bind(R.id.main_content)
    CoordinatorLayout mainContent;
    @Bind(R.id.indicator_default_circle)
    InfiniteIndicatorLayout indicatorDefaultCircle;
    @Bind(R.id.note_time)
    TextView noteTime;
    @Bind(R.id.note_content)
    TextView noteContent;
    @Bind(R.id.note_edit)
    FloatingActionButton noteEdit;

    public static Intent intent(Context context, long timeStamp) {
        return new Intents.Builder().setClass(context, NoteDetailActivity.class)
                .add(Constants.EXTRA_TIMESTAMP, timeStamp).toIntent();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_note);
        ButterKnife.bind(this);
        long timeStamp = getIntent().getLongExtra(Constants.EXTRA_TIMESTAMP, 0);
        EventRecord record = EventRecord.getByTimeStamp(timeStamp);
        if (!TextUtils.isEmpty(record.getPictures())) {
            ArrayList<String> urls = new ArrayList<>();
            String[] recordUrls = record.getPictures().split(",");
            Collections.addAll(urls, recordUrls);
            initCircleViewPager(urls);
        }
        collapsingToolbar.setExpandedTitleColor(Color.WHITE);
        collapsingToolbar.setTitle(getResources().getString(R.string.note_detail));
        noteTime.setText(record.getTime());
        noteContent.setText(record.getContent());
    }


    private void initCircleViewPager(final ArrayList<String> urls) {
        for (int i = 0; i < urls.size(); i++) {
            String url = urls.get(i);
            final int index = i;
            DefaultSliderView sliderView = new DefaultSliderView(getApplicationContext());
            sliderView.image(new File(url))
                    .setScaleType(BaseSliderView.ScaleType.CenterCrop)
                    .showImageResForEmpty(R.color.image_loading_bg_color)
                    .setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                        @Override
                        public void onSliderClick(BaseSliderView slider) {
                            Intent intent = ViewPagerActivity.intent(getApplicationContext(), urls, index);
                            startActivity(intent);
                        }
                    });
            indicatorDefaultCircle.addSlider(sliderView);
        }
        indicatorDefaultCircle.setIndicatorPosition();
    }
}
