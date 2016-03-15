package cn.libery.siyunote;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import cn.libery.siyunote.otto.BusProvider;
import cn.libery.siyunote.otto.ListTypeOtto;
import cn.libery.siyunote.otto.PagerPositionOtto;
import cn.libery.siyunote.ui.AboutActivity;
import cn.libery.siyunote.ui.AllNoteFragment;
import cn.libery.siyunote.ui.LifeNoteFragment;
import cn.libery.siyunote.ui.WorkNoteFragment;
import cn.libery.siyunote.utils.AppUtils;
import cn.libery.siyunote.utils.SharedPreferUtil;
import cn.libery.siyunote.utils.ToastUtil;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static butterknife.ButterKnife.bind;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.nav_view)
    NavigationView navView;
    @Bind(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @Bind(R.id.tabs)
    TabLayout tabs;
    @Bind(R.id.viewpager)
    ViewPager viewpager;
    private long firstBackPressedTime;
    private Adapter adapter;
    private boolean listType = AppUtils.isListLinear();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bind(this);
        BusProvider.getInstance().register(this);
        setSupportActionBar(toolbar);
        if (AppUtils.isFirstStartMain()) {
            SharedPreferUtil.put(Constants.FIRST_START_MAIN, false);
            SharedPreferUtil.put(Constants.LIST_TYPE, Constants.LIST_LINEAR);
        }
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();
        adapter = new Adapter(getSupportFragmentManager());
        if (viewpager != null) {
            setupViewPager(viewpager);
            tabs.setupWithViewPager(viewpager);
        }
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            int position = bundle.getInt(Constants.VIEW_PAGER_POSITION);
            viewpager.setCurrentItem(position, false);
        }
        navView.setNavigationItemSelectedListener(this);
        if (ContextCompat.checkSelfPermission(this, WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{WRITE_EXTERNAL_STORAGE}, 1);
        }
    }

    @Override
    public void onRequestPermissionsResult(final int requestCode, final String[] permissions, final int[]
            grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                ToastUtil.showAtUI("请在APP设置页面打开相应权限");
            }
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }

    private void setupViewPager(ViewPager viewpager) {
        AllNoteFragment mAllNoteFragment = AllNoteFragment.newInstance();
        adapter.addFragment(mAllNoteFragment, getString(R.string.action_all));
        WorkNoteFragment mWorkNoteFragment = WorkNoteFragment.newInstance();
        adapter.addFragment(mWorkNoteFragment, getString(R.string.action_work));
        LifeNoteFragment mLifeNoteFragment = LifeNoteFragment.newInstance();
        adapter.addFragment(mLifeNoteFragment, getString(R.string.action_life));
        viewpager.setAdapter(adapter);
    }

    @Subscribe
    public void setViewPagerPosition(PagerPositionOtto otto) {
        if (otto != null) {
            viewpager.setCurrentItem(otto.getPosition(), false);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_settings) {
            if (listType) {
                listType = false;
                SharedPreferUtil.put(Constants.LIST_TYPE, Constants.LIST_GRID);
                BusProvider.getInstance().post(new ListTypeOtto(Constants.LIST_GRID));
            } else {
                listType = true;
                SharedPreferUtil.put(Constants.LIST_TYPE, Constants.LIST_LINEAR);
                BusProvider.getInstance().post(new ListTypeOtto(Constants.LIST_LINEAR));
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_manage) {
            Uri uri = Uri.parse("mailto:442350442@qq.com");
            Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
            intent.putExtra(android.content.Intent.EXTRA_SUBJECT, getString(R.string.app_name) + BuildConfig
                    .VERSION_NAME + getString(R.string.feedback));
            startActivity(intent);
        } else if (id == R.id.nav_about) {
            startActivity(new Intent(this, AboutActivity.class));
        } else if (id == R.id.nav_share) {
            share();
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void share() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));
        intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.share_text, getString(R.string.app_down_url),
                BuildConfig.APP_DOWNLOAD_URL));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(Intent.createChooser(intent, getString(R.string.share)));
    }

    static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();

        public Adapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment, String title) {
            mFragments.add(fragment);
            mFragmentTitles.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitles.get(position);
        }
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            long secondBackPressedTime = System.currentTimeMillis();
            if (secondBackPressedTime - firstBackPressedTime > 2000) {
                Snackbar.make(tabs, "再按一次，退出应用", Snackbar.LENGTH_LONG).show();
                firstBackPressedTime = secondBackPressedTime;
            } else {
                finish();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BusProvider.getInstance().unregister(this);
    }

}
