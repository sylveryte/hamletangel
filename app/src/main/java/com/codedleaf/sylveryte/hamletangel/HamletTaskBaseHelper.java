package com.codedleaf.sylveryte.hamletangel;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.codedleaf.sylveryte.hamletangel.HamletTaskDbSchema.HamletTaskTable;

/**
 * Created by sylveryte on 29/3/18.
 * Yay!
 */

public class HamletTaskBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION=1;
    private static final String DATABASE_NAME="hamletdatabase.db";

    HamletTaskBaseHelper(Context context){
        super(context,DATABASE_NAME,null,VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + HamletTaskTable.NAME +"("+
                "_id integer primary key autoincrement,"+
                HamletTaskTable.Cols.TIMESTAMP+" timestamp default CURRENT_TIMESTAMP,"+
                HamletTaskTable.Cols.TEXT+", "+
                HamletTaskTable.Cols.NOTES+", "+
                HamletTaskTable.Cols.UUID+", "+
                HamletTaskTable.Cols.DIFFICULTY+", "+
                HamletTaskTable.Cols.DUE_DATE+", "+
                HamletTaskTable.Cols.TASKID+", "+
                HamletTaskTable.Cols.UPLOADED+", "+
                HamletTaskTable.Cols.TYPE+
                ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
