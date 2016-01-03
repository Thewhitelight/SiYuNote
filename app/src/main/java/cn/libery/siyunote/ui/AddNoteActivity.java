package cn.libery.siyunote.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import cn.libery.library_multiphotopick.photopick.ImageInfo;
import cn.libery.library_multiphotopick.photopick.PhotoOperate;
import cn.libery.library_multiphotopick.photopick.PhotoPickActivity;
import cn.libery.siyunote.Constants;
import cn.libery.siyunote.Intents;
import cn.libery.siyunote.R;
import cn.libery.siyunote.db.EventRecord;
import cn.libery.siyunote.model.wrapper.PhotoPickWrapper;
import cn.libery.siyunote.otto.BusProvider;
import cn.libery.siyunote.otto.RefreshOtto;
import cn.libery.siyunote.utils.AppUtils;
import cn.libery.siyunote.utils.TimeUtils;
import cn.libery.siyunote.widget.MultiPhotoPickView;

import static butterknife.ButterKnife.bind;
import static butterknife.ButterKnife.unbind;

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
    @Bind(R.id.add_photo)
    ImageView addPhoto;
    @Bind(R.id.add_voice)
    ImageView addVoice;
    @Bind(R.id.add_type)
    TextView addType;
    private List<PhotoPickWrapper> mPhotoPickWrapperList = new ArrayList<>();
    private static final int REQUEST_PICK_PHOTO = 0x1;
    private static final int PIC_MAX = 6;
    private int noteType;
    private long timestamp;

    public static Intent intent(Context context, int noteType) {
        return new Intents.Builder().setClass(context, AddNoteActivity.class)
                .add(Constants.NOTES_TYPE, noteType)
                .toIntent();
    }


    public static Intent intentEdit(Context context, long timeStamp) {
        return new Intents.Builder().setClass(context, AddNoteActivity.class)
                .add(Constants.NOTE_TIME_STAMP, timeStamp)
                .toIntent();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        timestamp = getIntent().getLongExtra(Constants.NOTE_TIME_STAMP, 0);
        noteType = getIntent().getIntExtra(Constants.NOTES_TYPE, 0);
        bind(this);

        setTitle(R.string.title_activity_add);
        initData();
    }

    CharSequence size = "";

    private void initData() {
        if (timestamp != 0) {
            EventRecord eventRecord = EventRecord.getByTimeStamp(timestamp);
            tvTime.setText(eventRecord.getTime());
            tvNum.setText(eventRecord.getContent().length() + "");
            edtInput.setText(eventRecord.getContent());
            edtInput.setSelection(eventRecord.getContent().length());
            addType.setText(AppUtils.getNoteType(eventRecord.getType()));

            String[] pics = eventRecord.getPictures().split(",");

            if (pics.length > 0) {
                ArrayList<String> urls = new ArrayList<>();
                Collections.addAll(urls, pics);
                photoView.setVisibility(View.VISIBLE);
                photoView.setImages(urls);
            }

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
        } else {
            tvTime.setText(TimeUtils.getNowTime());
            addType.setText(AppUtils.getNoteType(noteType));
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


    }

    @OnClick(R.id.add_photo)
    void setAddPhoto() {
        pickMultiImage();
    }

    @OnClick(R.id.add_type)
    void setNoteType() {
        final PopupMenu popUpMenu = new PopupMenu(this, addType);
        popUpMenu.inflate(R.menu.select_note_type);
        popUpMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_all:
                        noteType = 0;
                        addType.setText(R.string.action_all);
                        break;
                    case R.id.action_work:
                        noteType = 1;
                        addType.setText(R.string.action_work);
                        break;
                    case R.id.action_life:
                        noteType = 2;
                        addType.setText(R.string.action_life);
                        break;
                }
                popUpMenu.dismiss();
                return true;
            }
        });
        popUpMenu.show();
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
                    ArrayList<String> imgUrls = new ArrayList<>();
                    for (PhotoPickWrapper wrapper : mPhotoPickWrapperList) {
                        imgUrls.add(wrapper.getUriString());
                    }
                    photoView.setImages(imgUrls);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (!TextUtils.isEmpty(edtInput.getText()) || mPhotoPickWrapperList.size() > 0) {
            new AlertDialog.Builder(this)
                    .setTitle("笔记")
                    .setMessage("舍弃本次记录?")
                    .setPositiveButton("保存", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (timestamp != 0) {
                                updateRecord();
                            } else {
                                saveRecord();
                            }
                            BusProvider.getInstance().post(new RefreshOtto(true));
                            finish();
                        }
                    })
                    .setNegativeButton("不保存", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    })
                    .show();
        } else {
            super.onBackPressed();
        }

    }

    private void updateRecord() {
        EventRecord updateRecord = new EventRecord();
        updateRecord.setTime(TimeUtils.getNowTime());
        updateRecord.setType(noteType);
        updateRecord.setContent(edtInput.getText().toString());
        updateRecord.setPictures(getImgUrls());
        updateRecord.updateAll("timeStamp = ?", String.valueOf(timestamp));
    }

    private void saveRecord() {
        EventRecord record = new EventRecord();
        record.setTime(tvTime.getText().toString());
        record.setContent(edtInput.getText().toString());
        record.setType(noteType);
        record.setTimeStamp(Calendar.getInstance().getTimeInMillis());
        record.setPictures(getImgUrls());
        record.save();
    }

    private String getImgUrls() {
        String imgUrls = "";
        for (PhotoPickWrapper wrapper : mPhotoPickWrapperList) {
            imgUrls += wrapper.getUriString() + ",";
        }
        return imgUrls;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbind(this);
    }
}
