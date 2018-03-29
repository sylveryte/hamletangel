package com.codedleaf.sylveryte.hamletangel;

import android.content.Context;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * Created by sylveryte on 25/3/18.
 * Yay!
 */

class AngelLab {
    private static AngelLab sAngelLab;

    private ArrayList<HamletTask> mTasks;

    static AngelLab getAngelLab(Context context)
    {
        if(sAngelLab==null)
            sAngelLab=new AngelLab(context);
        return sAngelLab;
    }

    private AngelLab(Context context)
    {
        mTasks=new ArrayList<>();
        for (int i=1;i<22;i++)
        {

            HamletTask task=new HamletTask();
            if(i%2==0)
                task.setUploaded();
            task.setTaskText("Task #"+i);
            task.setNotes("The note about this task is something"+i+21);
            mTasks.add(task);
        }
    }

    void addUpdateTask(HamletTask task)
    {
        if(task!=null)
        {
            int i=getIndexOf(task);
            if(i==-1)
                mTasks.add(0,task);
            else
                update(task,i);
        }
    }

    private void update(HamletTask task,int index) {
        mTasks.remove(index);
        mTasks.add(0,task);
    }

    private int getIndexOf(HamletTask task)
    {
        for(HamletTask hamletTask:mTasks) {
            if (hamletTask.getId().equals(task.getId()))
                return mTasks.indexOf(hamletTask);
        }
        return -1;
    }

    HamletTask getTaskCopyById(UUID uuid)
    {
        for(HamletTask hamletTask:mTasks)
        {
            if(hamletTask.getId().equals(uuid))
            return hamletTask.getCopy();
        }
        return null;
    }

    ArrayList<HamletTask> getTasks()
    {
        ArrayList<HamletTask> tasks= new ArrayList<>();

        for (HamletTask task : mTasks) {
            if(!task.isUploaded())
                tasks.add(task);
        }
        for (HamletTask task : mTasks) {
            if(task.isUploaded())
               tasks.add(task);
        }
        return tasks;
    }

    void deleteTask(HamletTask hamletTask) {
        mTasks.remove(getIndexOf(hamletTask));
    }

    public void deleteUploadedTasks() {
        List<HamletTask> hamletTasks=new ArrayList<>(mTasks);
        for (HamletTask task :
                hamletTasks) {
            if (task.isUploaded())
                mTasks.remove(task);
        }
    }
}
