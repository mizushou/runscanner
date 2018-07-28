package com.example.shouhei.runscanner;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

public abstract class SingleFragmentActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private TabLayout mTabs;

    protected abstract Fragment createFragment();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_frag_container_act);

        // TODO consider the place where tool bar is inflated.
        // --------------Set up the tool bar and tabs as the app bar--------------

        // set up the status bar
        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.statusBar));

        // set up the tool bar
        mToolbar = findViewById(R.id.toolbar);
        if (mToolbar != null) {
            setupActionBar(mToolbar);
        }

        // set up the tabs
//        mTabs = findViewById(R.id.tabs);
//        mTabs.addTab(mTabs.newTab().setIcon(R.drawable.ic_photo_library));
//        mTabs.addTab(mTabs.newTab().setIcon(R.drawable.ic_camera));
//        mTabs.addOnTabSelectedListener(
//                new TabLayout.OnTabSelectedListener() {
//                    @Override
//                    public void onTabSelected(TabLayout.Tab tab) {
//                        if (tab.getText().equals("Gallery")) {
//                            Toast.makeText(getApplicationContext(), "hai", Toast.LENGTH_SHORT)
//                                    .show();
//                        }
//                    }
//
//                    @Override
//                    public void onTabUnselected(TabLayout.Tab tab) {}
//
//                    @Override
//                    public void onTabReselected(TabLayout.Tab tab) {}
//                });

        // -------------------------------------------------------------

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.single_frag_container);

        if (fragment == null) {
            fragment = createFragment();
            fm.beginTransaction().add(R.id.single_frag_container, fragment).commit();
        }
    }

    private void setupActionBar(Toolbar toolbar) {
        setSupportActionBar(toolbar);
    }
}
