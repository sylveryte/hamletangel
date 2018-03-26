package com.codedleaf.sylveryte.hamletangel;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sylveryte on 25/3/18.
 */

public class TaskListFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private TaskAdapter mAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.task_list_layout,container,false);
        mRecyclerView= v.findViewById(R.id.task_list_recycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));

        updateUI();

        return v;
    }

    private void updateUI() {
        AngelLab lab=AngelLab.getAngelLab(getActivity());
        List<HamletTask> hamletTasks= lab.getTasks();

        mAdapter=new TaskAdapter(hamletTasks);
        mRecyclerView.setAdapter(mAdapter);
    }

    private class TaskHolder extends RecyclerView.ViewHolder{
        private TextView mText;
        private TextView mNotes;
        private TextView mDifficultyLabel;
        private CheckBox mUploaded;

        public TaskHolder(View itemView) {
            super(itemView);
            mText=itemView.findViewById(R.id.task);
            mNotes=itemView.findViewById(R.id.notes);
            mDifficultyLabel=itemView.findViewById(R.id.difficulty_label);
            mUploaded=itemView.findViewById(R.id.uploaded);
        }

        public void updateView(HamletTask task)
        {
            mText.setText(task.getTaskText());
            mNotes.setText(task.getNotes());
            mDifficultyLabel.setText(task.getDifficultyString());
            mUploaded.setChecked(task.isUploaded());
        }
    }

    private class TaskAdapter extends RecyclerView.Adapter<TaskHolder>{
        private List<HamletTask> mTasks;
        public TaskAdapter(List<HamletTask> tasks) {
            mTasks=tasks;
        }

        @Override
        public TaskHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater=LayoutInflater.from(getActivity());
            View v=inflater.inflate(R.layout.task_list_item,parent,false);

            return new TaskHolder(v);
        }

        @Override
        public void onBindViewHolder(TaskHolder holder, int position) {
            holder.updateView(mTasks.get(position));
        }

        @Override
        public int getItemCount() {
            return mTasks.size();
        }
    }
}
