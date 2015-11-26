package cn.libery.siyunote.ui;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.libery.siyunote.R;
import cn.libery.siyunote.utils.TimeUtils;

public class AddNoteActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.tv_time)
    TextView tvTime;
    @Bind(R.id.tv_num)
    TextView tvNum;
    @Bind(R.id.edt_input)
    EditText edtInput;

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
                tvNum.setText(size.length()+"");
            }
        });
    }

}
