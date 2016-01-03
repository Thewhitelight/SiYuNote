package cn.libery.siyunote;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import cn.libery.siyunote.otto.BusProvider;
import cn.libery.siyunote.otto.PagerPositionOtto;
import cn.libery.siyunote.ui.AllNoteFragment;
import cn.libery.siyunote.ui.LifeNoteFragment;
import cn.libery.siyunote.ui.WorkNoteFragment;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bind(this);
        BusProvider.getInstance().register(this);
        setSupportActionBar(toolbar);

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

  /*  @OnClick(R.id.fab)
    void addNote() {
       *//* Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();*//*
        startActivity(new Intent(this, AddNoteActivity.class));
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camara) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
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
                Toast.makeText(getApplicationContext(), "再按一次，退出应用", Toast.LENGTH_SHORT).show();
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
