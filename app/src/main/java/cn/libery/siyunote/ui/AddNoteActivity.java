package cn.libery.siyunote.ui;

import android.os.Bundle;

import butterknife.ButterKnife;
import cn.libery.siyunote.R;

public class AddNoteActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        ButterKnife.bind(this);
        setTitle(R.string.title_activity_add);
    }

}
