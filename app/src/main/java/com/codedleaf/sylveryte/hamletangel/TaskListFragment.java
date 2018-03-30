package com.codedleaf.sylveryte.hamletangel;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;
import java.util.UUID;

/**
 * Created by sylveryte on 25/3/18.
 * Yay!
 */

public class TaskListFragment extends Fragment implements AngelListUpdatable {
    private RecyclerView mRecyclerView;
    private TaskAdapter mAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        AngelLab.getAngelLab(getContext()).addMeForUpdate(this);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.tasklistmenu,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_uploaded_tasks:
                AngelLab.getAngelLab(getActivity()).deleteUploadedTasks();
                updateUI();
                return true;
            case R.id.upload_tasks:
            {
                PostOffice postOffice=new PostOffice(getContext());

                List<HamletTask> tasks=AngelLab.getAngelLab(getContext()).getToBeUploadedTask();
                if(tasks.size()>0) {
                    postOffice.beginUpload(getActivity(),tasks);
                }
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.task_list_layout,container,false);
        mRecyclerView= v.findViewById(R.id.task_list_recycler);
        FloatingActionButton fabAdd = v.findViewById(R.id.fab_add);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));

        updateUI();

        fabAdd.setOnClickListener(new FloatingAddAction());
        return v;
    }

    private void updateUI() {
        AngelLab lab=AngelLab.getAngelLab(getActivity());
        List<HamletTask> hamletTasks= lab.getTasks();

        if(mAdapter==null)
        {
            mAdapter=new TaskAdapter(hamletTasks);
            mRecyclerView.setAdapter(mAdapter);
        }
        else {
            mAdapter.updateTasks(hamletTasks);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void updateUiFromLab() {
        updateUI();
    }

    private class TaskHolder extends RecyclerView.ViewHolder{
        private HamletTask mHamletTask;
        private TextView mText;
        private TextView mNotes;
        private TextView mDate;
        private LinearLayout mDifficultyIndicator;
        private View mView;

        TaskHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(getIntentForEdit(getActivity(),mHamletTask.getId()));
                }
            });
            mText=itemView.findViewById(R.id.task);
            mNotes=itemView.findViewById(R.id.notes);
            mDate=itemView.findViewById(R.id.duedate);
            mDifficultyIndicator=itemView.findViewById(R.id.difficulty_indicator);
            mView=itemView;
        }

        void updateView(HamletTask task)
        {
            mHamletTask=task;
            mText.setText(task.getTaskText());

            if(task.getNotes()==null || task.getNotes().trim().equals(""))
                mNotes.setVisibility(View.GONE);
            else {
                mNotes.setVisibility(View.VISIBLE);
                mNotes.setText(task.getNotes());
            }

            String date=task.getDate();
            if(date==null)
                mDate.setVisibility(View.GONE);
            else {
                mDate.setVisibility(View.VISIBLE);
                mDate.setText(date);
            }

            switch (task.getDifficulty())
            {
                case 1: mDifficultyIndicator.setBackgroundResource(R.color.colorTrivial);break;
                case 2: mDifficultyIndicator.setBackgroundResource(R.color.colorEasy);break;
                case 3: mDifficultyIndicator.setBackgroundResource(R.color.colorMedium);break;
                case 4: mDifficultyIndicator.setBackgroundResource(R.color.colorHard);break;
            }
            if(task.isUploaded()) {
                mView.setClickable(false);
                mView.setBackgroundResource(R.color.colorUploaded);
            }
            else {
                mView.setClickable(true);
                mView.setBackgroundResource(R.color.colorNotUploaded);
            }
        }
    }

    private class TaskAdapter extends RecyclerView.Adapter<TaskHolder>{
        private List<HamletTask> mTasks;
        TaskAdapter(List<HamletTask> tasks) {
            mTasks=tasks;
        }

        void updateTasks(List<HamletTask> tasks)
        {
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

    private class FloatingAddAction implements View.OnClickListener
    {
        @Override
        public void onClick(View v) {
            startActivity(getIntentForEdit(getContext(),null));
        }
    }

    public static Intent getIntentForEdit(Context context,UUID uuid)
    {
        Intent i=new Intent(context,EditActivity.class);
        if(uuid!=null)
            i.putExtra(EditTaskFragment.ARG_UUID_STRING_CODE,uuid.toString());
        return i;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }


}
