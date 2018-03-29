package com.codedleaf.sylveryte.hamletangel;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Criteria;

import com.codedleaf.sylveryte.hamletangel.HamletTaskDbSchema.HamletTaskTable;

import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by sylveryte on 25/3/18.
 * Yay!
 */

class AngelLab {
    private static AngelLab sAngelLab;

    private ArrayList<HamletTask> mTasks;
    private Context mContext;
    private SQLiteDatabase mDatabase;

    static AngelLab getAngelLab(Context context)
    {
        if(sAngelLab==null)
            sAngelLab=new AngelLab(context);

        try {
            Postmaster.login("pneumazen","manoffire");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return sAngelLab;
    }

    private AngelLab(Context context)
    {
        mContext=context;
        mDatabase=new HamletTaskBaseHelper(mContext).getWritableDatabase();

        mTasks= new ArrayList<>();


        HamletTaskCursorWrapper cursor = queryHamletTasks(null,null);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                mTasks.add(0,cursor.getHamletTask());
                cursor.moveToNext();
            }
        }finally {
            cursor.close();
        }
    }

    void addUpdateTask(HamletTask task)
    {
        if(task!=null)
        {
            int i=getIndexOf(task);
            if(i==-1)
            {
                mTasks.add(0,task);
                mDatabase.insert(HamletTaskTable.NAME,null,getContentValues(task));
            }

            else
                update(task,i);
        }
    }

    private void update(HamletTask task,int index) {
        mTasks.remove(index);
        mTasks.add(0,task);
        mDatabase.update(HamletTaskTable.NAME,getContentValues(task),
                HamletTaskTable.Cols.UUID+" = ?",
                new String[]{task.getId().toString()});
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
        return new ArrayList<>(mTasks);
    }

    void deleteTask(HamletTask hamletTask) {
        mTasks.remove(getIndexOf(hamletTask));
        mDatabase.delete(HamletTaskTable.NAME,
                HamletTaskTable.Cols.UUID+" = ?",
                new String[]{hamletTask.getId().toString()});
    }

    public void deleteUploadedTasks() {
        List<HamletTask> hamletTasks=new ArrayList<>(mTasks);
        for (HamletTask task :
                hamletTasks) {
            if (task.isUploaded())
            {
                deleteTask(task);
            }

        }
    }

    //Database stuff

    private static ContentValues getContentValues(HamletTask hamletTask){
        ContentValues values = new ContentValues();
        values.put(HamletTaskTable.Cols.UUID,hamletTask.getId().toString());
        values.put(HamletTaskTable.Cols.DIFFICULTY,hamletTask.getDifficulty());
        values.put(HamletTaskTable.Cols.DUE_DATE,hamletTask.getDate());
        values.put(HamletTaskTable.Cols.TEXT,hamletTask.getTaskText());
        values.put(HamletTaskTable.Cols.NOTES,hamletTask.getNotes());
        values.put(HamletTaskTable.Cols.TYPE,hamletTask.getTaskType());
        values.put(HamletTaskTable.Cols.UPLOADED,hamletTask.isUploaded()?1:0);
        values.put(HamletTaskTable.Cols.TASKID ,hamletTask.getTaskId());
        values.put(HamletTaskTable.Cols.TIMESTAMP,
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        return values;
    }

    private HamletTaskCursorWrapper queryHamletTasks(String whereClause, String[] whereArgs){
        Cursor cursor=mDatabase.query(
                HamletTaskTable.NAME,
                null,
                whereClause,
                whereArgs,
//                null,
                null,
//                null
//                HamletTaskTable.Cols.UPLOADED,
                null,
                HamletTaskTable.Cols.TIMESTAMP +" asc "
//                null
        );
        return new HamletTaskCursorWrapper(cursor);
    }
}
