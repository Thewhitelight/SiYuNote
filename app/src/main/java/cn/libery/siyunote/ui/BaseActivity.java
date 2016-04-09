package cn.libery.siyunote.ui;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import cn.libery.siyunote.R;

/**
 * Created by Libery on 2015/11/1.
 * Email:libery.szq@qq.com
 */
public class BaseActivity extends AppCompatActivity {

    public void setTitle(int title) {
        setTitle(getString(title));
    }

    public void setTitle(String title) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(title);
        setActionBar(toolbar);
    }

    private void setActionBar(Toolbar toolbar) {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    public void onToolbarBackPressed() {
        onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onToolbarBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
