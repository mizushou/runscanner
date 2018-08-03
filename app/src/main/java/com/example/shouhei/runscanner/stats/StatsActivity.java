package com.example.shouhei.runscanner.stats;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.shouhei.runscanner.R;
import com.example.shouhei.runscanner.SingleFragmentActivity;
import com.example.shouhei.runscanner.SingleFragmentActivityForStats;
import com.example.shouhei.runscanner.SingleFragmentActivityWithNavBar;
import com.example.shouhei.runscanner.runs.RunsFragment;

public class StatsActivity extends SingleFragmentActivityForStats {

    private static final String TAG = "StatsActivity";

    @Override
    protected Fragment createFragment() {
        return new StatsFragment();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() called");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() called");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() called");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }
}
