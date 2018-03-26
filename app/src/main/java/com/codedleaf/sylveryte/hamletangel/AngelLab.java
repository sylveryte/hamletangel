package com.codedleaf.sylveryte.hamletangel;

import android.content.Context;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.UUID;

/**
 * Created by sylveryte on 25/3/18.
 */

public class AngelLab {
    private static AngelLab sAngelLab;

    private ArrayList<HamletTask> mTasks;

    public static AngelLab getAngelLab(Context context)
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

    public void addTask(HamletTask task)
    {
        if(task!=null && !mTasks.contains(task))
            mTasks.add(task);
    }

    public HamletTask getTaskById(UUID uuid)
    {
        for(HamletTask hamletTask:mTasks)
        {
            if(hamletTask.getId().equals(uuid))
            {
                return hamletTask;
            }
        }
        return null;
    }

    public ArrayList<HamletTask> getTasks() {
        return mTasks;
    }
}
