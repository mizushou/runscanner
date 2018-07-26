package com.example.shouhei.runscanner.runs;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.shouhei.runscanner.runresult.RunResultActivity;
import com.example.shouhei.runscanner.R;
import com.example.shouhei.runscanner.data.Run;

import java.util.List;

public class RunsFragment extends Fragment {

    private static final String TAG = "RunsFragment";

    private RecyclerView mRunsRecyclerView;
    private RunAdapter mAdapter;
    private FloatingActionButton mAddFab;
    private View mNoRunView;
    private ImageView mNoRunIcon;
    private TextView mNoRunText;

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.runs_frag, container, false);

        // set up the RecyclerView
        mRunsRecyclerView = root.findViewById(R.id.run_recycler_view);
        mRunsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        Log.d(TAG, "onCreateView() is called");

        // set up add the fab
        mAddFab = root.findViewById(R.id.add_fab);
        mAddFab.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), RunResultActivity.class);
                        startActivity(intent);
                    }
                });

        // set up no the runs view
        mNoRunView = root.findViewById(R.id.no_run);
        mNoRunIcon = root.findViewById(R.id.no_run_icon);
        mNoRunText = root.findViewById(R.id.no_run_text);

        updateUI();
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
        Log.d(TAG, "onResume() is called");
    }

    private void updateUI() {
        Runs runs = Runs.get(getActivity());
        // get latest run's data from DB.
        List<Run> runList = runs.getRuns();

        if (mAdapter == null) {
            mAdapter = new RunAdapter(runList);
            mRunsRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.setRunList(runList);
            mAdapter.notifyDataSetChanged();
        }
    }

    // ===================Inner class#1 [Adapter]===================
    private class RunAdapter extends RecyclerView.Adapter<RunHolder> {
        // This is a snapshot of Run table in SQLite.
        private List<Run> mRunList;

        public RunAdapter(List<Run> runList) {

            mRunList = runList;
        }

        public void setRunList(List<Run> runList) {
            mRunList = runList;
        }

        @NonNull
        @Override
        public RunHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());

            return new RunHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull RunHolder holder, int position) {
            Run run = mRunList.get(position);
            holder.bind(run);
        }

        @Override
        public int getItemCount() {
            return mRunList.size();
        }
    }
    // ==============================================================

    // ====================Inner class#2 [Holder]====================
    private class RunHolder extends RecyclerView.ViewHolder {

        private TextView mDistanceTextView;
        private TextView mDurationTextView;
        private Run mRun;

        public RunHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.run_card, parent, false));
            itemView.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(
                                            getActivity(),
                                            mRun.getDistance() + "clicked!",
                                            Toast.LENGTH_SHORT)
                                    .show();
                        }
                    });

            mDistanceTextView = itemView.findViewById(R.id.run_distance);
            mDurationTextView = itemView.findViewById(R.id.run_duration);
        }

        public void bind(Run run) {
            mRun = run;
            //            mDistanceTextView.setText(getString(R.string.result_run_distance,
            // mRun.getDistance()));
            //            mDurationTextView.setText(getString(R.string.result_run_duration,
            // mRun.getDuration()));
        }
    }
    // ==============================================================
}
