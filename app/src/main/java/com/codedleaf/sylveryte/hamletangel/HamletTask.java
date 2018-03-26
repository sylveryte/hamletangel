package com.codedleaf.sylveryte.hamletangel;

import android.util.Log;

import java.util.Date;
import java.util.UUID;

/**
 * Created by sylveryte on 25/3/18.
 */

public class HamletTask {
    public static String ID="HAMLET_ID";

    public static int TRIVIAL=1;
    public static String STR_TRIVIAL="Trivial";
    public static int EASY=2;
    public static String STR_EASY="Easy";
    public static int MEDIUM=3;
    public static String STR_MEDIUM="Medium";
    public static int HARD=4;
    public static String STR_HARD="Hard";

    public static String TODO="todo";

    private UUID mId;
    private String mTaskText;
    private String mTaskType;
    private String mNotes;
    private int mDifficulty;
    private boolean mUploaded;

    private String mDate;

    private String mTaskId;

    public HamletTask()
    {
        mId=UUID.randomUUID();
        mTaskType=TODO;
        mDifficulty=EASY;
        mUploaded=false;
        mDate=null;
    }

    @Override
    public String toString() {
        return "HamletTask{" +
                "mId='" + mId.toString() + '\'' +
                "mTaskText='" + mTaskText + '\'' +
                ", mTaskType='" + mTaskType + '\'' +
                ", mNotes='" + mNotes + '\'' +
                ", mDifficulty=" + mDifficulty +
                ", mDate=" + mDate +
                ", mTaskId='" + mTaskId + '\'' +
                '}';
    }

    public String getDifficultyString()
    {
        switch (mDifficulty){
            case 1:return STR_TRIVIAL;
            case 2:return STR_EASY;
            case 3:return STR_EASY;
            case 4:return STR_HARD;
        }
        return STR_EASY;
    }
    public String getTaskText() {
        return mTaskText;
    }

    public void setTaskText(String taskText) {
        mTaskText = taskText;
    }

    public String getTaskType() {
        return mTaskType;
    }

    public void setTaskType(String taskType) {
        mTaskType = taskType;
    }

    public String getNotes() {
        return mNotes;
    }

    public void setNotes(String notes) {
        mNotes = notes;
    }

    public int getDifficulty() {
        return mDifficulty;
    }

    public double getHabiticaDifficulty()
    {
        switch (mDifficulty){
            case 1:return 0.1;
            case 2:return 1;
            case 3:return 1.5;
            case 4:return 2;
        }
        return 1;
    }

    public void setDifficulty(int difficulty) {
        mDifficulty = difficulty;
    }

    public String getDate() {
        if(mDate==null)
            return "Set Date";
        return mDate;
    }

    public UUID getId() {
        return mId;
    }

    public boolean isUploaded() {
        return mUploaded;
    }

    public void setUploaded() {
        mUploaded = true;
    }

    public void setDate(String date)
    {
        mDate = date;
        Log.d("maan","someone changed the date");
    }

    public String getTaskId() {
        return mTaskId;
    }

    public void setTaskId(String taskId) {
        mTaskId = taskId;
    }
}
