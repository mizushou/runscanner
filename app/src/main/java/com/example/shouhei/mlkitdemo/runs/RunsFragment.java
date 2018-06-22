package com.example.shouhei.mlkitdemo.runs;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shouhei.mlkitdemo.MainActivity;
import com.example.shouhei.mlkitdemo.R;
import com.example.shouhei.mlkitdemo.data.Run;

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

    // set up RecyclerView
    View root = inflater.inflate(R.layout.runs_frag, container, false);
    mRunsRecyclerView = root.findViewById(R.id.run_recycler_view);
    mRunsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    Log.d(TAG, "onCreateView() is called");

    // set up add fab
    // TODO move fab from single_frag_container_act.xml to runs_frag.xml
    mAddFab = getActivity().findViewById(R.id.add_fab);
    mAddFab.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            Log.d(TAG, "Add FAB is clicked");
            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);
          }
        });

    // set up no runs view
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
    List<Run> runList = runs.getRunList();

    if (runList.size() == 0) {
      showNoRunsViews(getResources().getString(R.string.no_run_message), R.drawable.ic_no_run);
    } else {
      showRunsView();
    }

    mAdapter = new RunAdapter(runList);
    mRunsRecyclerView.setAdapter(mAdapter);
  }

  private void showNoRunsViews(String text, int iconRes) {
    mRunsRecyclerView.setVisibility(View.GONE);
    mNoRunView.setVisibility(View.VISIBLE);

    mNoRunIcon.setImageDrawable(getResources().getDrawable(iconRes));
    mNoRunText.setText(text);
  }

  private void showRunsView() {
    mRunsRecyclerView.setVisibility(View.VISIBLE);
    mNoRunView.setVisibility(View.GONE);
  }

  // ===================Inner class#1 [Adapter]===================
  private class RunAdapter extends RecyclerView.Adapter<RunHolder> {
    private List<Run> mRunList;

    public RunAdapter(List<Run> runList) {

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
      super(inflater.inflate(R.layout.run_item, parent, false));
      itemView.setOnClickListener(
          new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              Toast.makeText(getActivity(), mRun.getDistance() + "clicked!", Toast.LENGTH_SHORT)
                  .show();
            }
          });

      mDistanceTextView = itemView.findViewById(R.id.run_distance);
      mDurationTextView = itemView.findViewById(R.id.run_duration);
    }

    public void bind(Run run) {
      mRun = run;
      mDistanceTextView.setText(getString(R.string.result_run_distance, mRun.getDistance()));
      mDurationTextView.setText(getString(R.string.result_run_duration, mRun.getDuration()));
    }
  }
  // ==============================================================
}
