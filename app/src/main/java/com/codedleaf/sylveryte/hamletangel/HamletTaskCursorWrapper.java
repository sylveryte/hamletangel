package com.codedleaf.sylveryte.hamletangel;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.codedleaf.sylveryte.hamletangel.HamletTaskDbSchema.HamletTaskTable;

import java.util.UUID;

/**
 * Created by sylveryte on 29/3/18.
 * Yay!
 */

public class HamletTaskCursorWrapper extends CursorWrapper {
    public HamletTaskCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public HamletTask getHamletTask()
    {
        String uuidString= getString(getColumnIndex(HamletTaskTable.Cols.UUID));
        String task= getString(getColumnIndex(HamletTaskTable.Cols.TEXT));
        String notes= getString(getColumnIndex(HamletTaskTable.Cols.NOTES));
        String duedate= getString(getColumnIndex(HamletTaskTable.Cols.DUE_DATE));
        int difficulty= getInt(getColumnIndex(HamletTaskTable.Cols.DIFFICULTY));
        String type = getString(getColumnIndex(HamletTaskTable.Cols.TYPE));
        boolean uploaded = getInt(getColumnIndex(HamletTaskTable.Cols.UPLOADED)) == 1;
        String taskid= getString(getColumnIndex(HamletTaskTable.Cols.TASKID));

        HamletTask hamletTask=new HamletTask(UUID.fromString(uuidString));
        hamletTask.setTaskText(task);
        hamletTask.setNotes(notes);
        hamletTask.setDate(duedate);
        hamletTask.setDifficulty(difficulty);
        hamletTask.setTaskType(type);
        if(uploaded)
            hamletTask.setUploaded();
        hamletTask.setTaskId(task);

        return hamletTask;
    }
}
