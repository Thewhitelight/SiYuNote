package cn.libery.siyunote.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import butterknife.Bind;
import butterknife.OnClick;
import cn.libery.siyunote.Constants;
import cn.libery.siyunote.Intents;
import cn.libery.siyunote.R;
import cn.libery.siyunote.db.EventRecord;
import cn.libery.siyunote.otto.BusProvider;
import cn.libery.siyunote.otto.RefreshOtto;
import cn.lightsky.infiniteindicator.InfiniteIndicatorLayout;
import cn.lightsky.infiniteindicator.slideview.BaseSliderView;
import cn.lightsky.infiniteindicator.slideview.DefaultSliderView;

import static butterknife.ButterKnife.bind;

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
    private long timeStamp;
    private int random;

    public static Intent intent(Context context, long timeStamp, int position) {
        return new Intents.Builder().setClass(context, NoteDetailActivity.class)
                .add(Constants.EXTRA_TIMESTAMP, timeStamp)
                .add(Constants.VIEW_PAGER_POSITION, position)
                .toIntent();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_note);
        bind(this);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
        }
        timeStamp = getIntent().getLongExtra(Constants.EXTRA_TIMESTAMP, 0);
        collapsingToolbar.setExpandedTitleColor(Color.WHITE);
        collapsingToolbar.setTitle(getResources().getString(R.string.note_detail));
        random = new Random().nextInt(10);
    }

    @Override
    protected void onResume() {
        super.onResume();
        iniData();
    }

    private void iniData() {
        EventRecord record = EventRecord.getByTimeStamp(timeStamp);
        noteTime.setText(record.getTime());
        noteContent.setText(record.getContent());
        ArrayList<String> urls = new ArrayList<>();
        record = EventRecord.getByTimeStamp(timeStamp);
        if (!TextUtils.isEmpty(record.getPictures())) {
            String[] recordUrls = record.getPictures().split(",");
            Collections.addAll(urls, recordUrls);
            initCircleViewPager(urls);
        } else {
            indicatorDefaultCircle.removeSlider();
            @DrawableRes final int[] res = new int[]{R.drawable.bg_1, R.drawable.bg_10, R.drawable.bg_2,
                    R.drawable.bg_3, R.drawable.bg_4, R.drawable.bg_5,
                    R.drawable.bg_6, R.drawable.bg_7, R.drawable.bg_8,
                    R.drawable.bg_9
            };
            DefaultSliderView sliderView = new DefaultSliderView(getApplicationContext());
            sliderView.image(res[random])
                    .setScaleType(BaseSliderView.ScaleType.CenterCrop)
                    .showImageResForEmpty(R.color.image_loading_bg_color);
            indicatorDefaultCircle.addSlider(sliderView);
            indicatorDefaultCircle.setIndicatorPosition();
        }
    }


    private void initCircleViewPager(final ArrayList<String> urls) {
        indicatorDefaultCircle.removeSlider();
        for (int i = 0; i < urls.size(); i++) {
            String url = urls.get(i).split("file:/")[1];
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
        indicatorDefaultCircle.startAutoScroll();
        indicatorDefaultCircle.setIndicatorPosition();
    }

    @OnClick(R.id.note_edit)
    void addNote() {
        startActivity(AddNoteActivity.intentEdit(this, timeStamp));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.delete_note, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_delete) {

            new AlertDialog.Builder(this)
                    .setMessage("您确定要删除此便签吗")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            EventRecord.deleteBy(timeStamp);
                            BusProvider.getInstance().post(new RefreshOtto(true));
                            finish();
                        }
                    })
                    .setNegativeButton("取消", null)
                    .show();
        }
        return super.onOptionsItemSelected(item);
    }

}
