package cn.libery.siyunote.ui;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.OnClick;
import cn.libery.siyunote.BuildConfig;
import cn.libery.siyunote.R;

import static butterknife.ButterKnife.bind;
import static butterknife.ButterKnife.unbind;

/**
 * Created by Libery on 2016/1/9.
 * Email:libery.szq@qq.com
 */
public class AboutActivity extends BaseActivity {

    @Bind(R.id.version_text)
    TextView versionText;
    @Bind(R.id.project_home)
    Button projectHomeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        bind(this);
        setTitle(R.string.about);
        versionText.setText(BuildConfig.VERSION_NAME);
    }

    @OnClick(R.id.version_text)
    void showVersion() {
        egg();
    }

    private void egg() {
        String msg = "BUILD_TYPE: " + BuildConfig.BUILD_TYPE + "\n"
                + "BUILD_TIME: " + BuildConfig.BUILD_TIME + "\n"
                + "GIT_COMMIT_ID: " + BuildConfig.GIT_COMMIT_ID;
        if (!TextUtils.isEmpty(msg)) {
            AlertDialog.Builder b = new AlertDialog.Builder(this);
            b.setMessage(msg);
            b.setPositiveButton("OK", null);
            b.show();
        }
    }


    @OnClick(R.id.project_home)
    void showProjectHome() {
        startViewAction(BuildConfig.GITHUB_URL);
    }

    private void startViewAction(String uriStr) {
        try {
            Uri uri = Uri.parse(uriStr);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbind(this);
    }
}
