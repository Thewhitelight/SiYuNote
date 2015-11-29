package cn.libery.siyunote.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.libery.library_multiphotopick.photopick.ImageInfo;
import cn.libery.library_multiphotopick.photopick.PhotoOperate;
import cn.libery.library_multiphotopick.photopick.PhotoPickActivity;
import cn.libery.siyunote.Constants;
import cn.libery.siyunote.R;
import cn.libery.siyunote.model.wrapper.PhotoPickWrapper;
import cn.libery.siyunote.utils.TimeUtils;
import cn.libery.siyunote.widget.MultiPhotoPickView;

public class AddNoteActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.tv_time)
    TextView tvTime;
    @Bind(R.id.tv_num)
    TextView tvNum;
    @Bind(R.id.edt_input)
    EditText edtInput;
    @Bind(R.id.photo_view)
    MultiPhotoPickView photoView;
    @Bind(R.id.add_md)
    ImageView addMd;
    @Bind(R.id.add_photo)
    ImageView addPhoto;
    @Bind(R.id.add_voice)
    ImageView addVoice;
    private List<PhotoPickWrapper> mPhotoPickWrapperList = new ArrayList<>();
    private static final int REQUEST_PICK_PHOTO = 0x1;
    private static final int PIC_MAX = 6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        ButterKnife.bind(this);
        setTitle(R.string.title_activity_add);
        initView();
    }

    CharSequence size = "";

    private void initView() {
        long nowTime = Calendar.getInstance(Locale.CHINA).getTime().getTime();
        tvTime.setText(TimeUtils.yyyyMMddChinese(nowTime) + "  " + TimeUtils.hhMM(nowTime));
        edtInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                size = s;
            }

            @Override
            public void afterTextChanged(Editable s) {
                tvNum.setText(size.length() + "");
            }
        });

        photoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> urls = new ArrayList<>();
                for (int i = 0; i < mPhotoPickWrapperList.size(); i++) {
                    urls.add(mPhotoPickWrapperList.get(i).getUriString());
                }
                startActivity(ViewPagerActivity.intent(getApplicationContext(), urls, 0));
            }
        });
    }

    @OnClick(R.id.add_photo)
    void setAddPhoto() {
        pickMultiImage();
    }

    private void pickMultiImage() {
        Intent intent = new Intent(this, PhotoPickActivity.class);
        intent.putExtra(Constants.EXTRA_MAX, PIC_MAX);
        ArrayList<ImageInfo> pickImages = new ArrayList<>();
        for (PhotoPickWrapper item : mPhotoPickWrapperList) {
            pickImages.add(item.getImageInfo());
        }
        intent.putExtra("EXTRA_PICKED", pickImages);
        startActivityForResult(intent, REQUEST_PICK_PHOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_PICK_PHOTO) {
            try {
                @SuppressWarnings("unchecked")
                ArrayList<ImageInfo> pickPhotos = (ArrayList<ImageInfo>) data.getSerializableExtra("data");
                mPhotoPickWrapperList.clear();
                PhotoOperate photoOperate = new PhotoOperate(this);
                for (int i = 0; i < pickPhotos.size(); i++) {
                    ImageInfo info = pickPhotos.get(i);
                    Uri uri = Uri.parse(info.path);
                    File outputFile = photoOperate.scale(uri);
                    String outputUri = Uri.fromFile(outputFile).toString();
                    String fullUri = ImageInfo.pathAddPreFix(outputUri);
                    String fullUriSplit = fullUri.split(":///")[1];
                    PhotoPickWrapper wrapper = new PhotoPickWrapper(info, outputFile, fullUriSplit);
                    mPhotoPickWrapperList.add(wrapper);
                }
                if (mPhotoPickWrapperList.size() > 0) {
                    photoView.setVisibility(View.VISIBLE);
                    photoView.setImages(mPhotoPickWrapperList);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
