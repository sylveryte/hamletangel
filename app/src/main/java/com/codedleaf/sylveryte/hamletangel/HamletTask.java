package com.codedleaf.sylveryte.hamletangel;

import java.util.UUID;

/**
 * Created by sylveryte on 25/3/18.
 * Yay!
 */

public class HamletTask {
//    public static String ID="HAMLET_ID";

    private static final int TRIVIAL=1;
    private static final int EASY=2;
    private static final int MEDIUM=3;
    private static final int HARD=4;
    private static final String STR_TRIVIAL="Trivial";
    private static final String STR_EASY="Easy";
    private static final String STR_MEDIUM="Medium";
    private static final String STR_HARD="Hard";
    private static final String TODO="todo";

    private UUID mId;
    private String mTaskText;
    private String mTaskType;
    private String mNotes;
    private int mDifficulty;
    private boolean mUploaded;
    private String mDate;
    private String mTaskId;

    HamletTask(UUID uuid)
    {
        mId=uuid;
        mTaskType=TODO;
        mDifficulty=EASY;
        mUploaded=false;
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

    String getDifficultyString()
    {
        switch (mDifficulty){
            case 1:return STR_TRIVIAL;
            case 2:return STR_EASY;
            case 3:return STR_MEDIUM;
            case 4:return STR_HARD;
        }
        return STR_EASY;
    }
    String getTaskText() {
        return mTaskText;
    }

    void setTaskText(String taskText) {
        mTaskText = taskText;
    }

    String getTaskType() {
        return mTaskType;
    }

    void setTaskType(String taskType) {
        mTaskType = taskType;
    }

    public String getNotes() {
        return mNotes;
    }

    public void setNotes(String notes) {
        mNotes = notes;
    }

    int getDifficulty() {
        return mDifficulty;
    }

    double getHabiticaDifficulty()
    {
        switch (mDifficulty){
            case TRIVIAL:return 0.1;
            case EASY:return 1;
            case MEDIUM:return 1.5;
            case HARD:return 2;
        }
        return 1;
    }

    void setDifficulty(int difficulty) {
        mDifficulty = difficulty;
    }

    public String getDate() {
        return mDate;
    }

    public UUID getId() {
        return mId;
    }

    boolean isUploaded() {
        return mUploaded;
    }

    void setUploaded() {
        mUploaded = true;
    }

    private void setUploadedValue(boolean uploaded)
    {
        mUploaded=uploaded;
    }

    public void setDate(String date)
    {
        mDate = date;
    }

    String getTaskId() {
        return mTaskId;
    }

    void setTaskId(String taskId) {
        mTaskId = taskId;
    }

    HamletTask getCopy()
    {
        HamletTask hamletTask=new HamletTask(mId);
        hamletTask.setDifficulty(getDifficulty());
        hamletTask.setNotes(getNotes());
        hamletTask.setTaskText(getTaskText());
        hamletTask.setDate(getDate());
        hamletTask.setTaskType(getTaskType());
        hamletTask.setUploadedValue(isUploaded());
        return hamletTask;
    }
}
