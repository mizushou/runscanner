package com.example.shouhei.runscanner;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

public abstract class SingleFragmentActivityWithNavBar extends AppCompatActivity {

    private Toolbar mToolbar;
    private DrawerLayout mDrawerLayout;

    protected abstract Fragment createFragment();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_frag_container_act_with_nav_bar);

        // TODO consider the place where tool bar and navigation view are inflated.
        // --------------Set up the tool bar as the app bar--------------
        // set up the tool bar
        mToolbar = findViewById(R.id.toolbar);
        if (mToolbar != null) {
            setupActionBar(mToolbar);
        }

        // set up the navigation drawer
        mDrawerLayout = findViewById(R.id.drawer_layout);
        if (mDrawerLayout != null) {
            mDrawerLayout.setStatusBarBackground(R.color.colorPrimaryDark);
        }

        // set up the navigation view
        NavigationView navigationView = findViewById(R.id.nav_view);
        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }

        // -------------------------------------------------------------

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.single_frag_container_with_nav_bar);

        if (fragment == null) {
            fragment = createFragment();
            fm.beginTransaction().add(R.id.single_frag_container_with_nav_bar, fragment).commit();
        }
    }

    private void setupActionBar(Toolbar toolbar) {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.runlist_navigation_menu_item:
                                Toast.makeText(
                                                getApplicationContext(),
                                                "Run List clicked!",
                                                Toast.LENGTH_SHORT)
                                        .show();
                                break;
                            case R.id.statistics_navigation_menu_item:
                                Toast.makeText(
                                                getApplicationContext(),
                                                "stats clicked!",
                                                Toast.LENGTH_SHORT)
                                        .show();
                                break;
                            case R.id.user_navigation_menu_item:
                                Toast.makeText(
                                                getApplicationContext(),
                                                "user clicked!",
                                                Toast.LENGTH_SHORT)
                                        .show();

                                break;
                            case R.id.share_navigation_menu_item:
                                Toast.makeText(
                                                getApplicationContext(),
                                                "share clicked!",
                                                Toast.LENGTH_SHORT)
                                        .show();

                                break;
                            default:
                                break;
                        }
                        // close the navigation drawer when an item is selected.
                        item.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
