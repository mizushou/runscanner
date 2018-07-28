package com.example.shouhei.runscanner.data.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.shouhei.runscanner.data.database.RunDbSchema.RunTable;

public class RunBaseHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "RunBase.db";

    public RunBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table "
                        + RunTable.NAME
                        + "( _id integer primary key autoincrement, "
                        + RunTable.Cols.UUID
                        + ","
                        + RunTable.Cols.DISTANCE
                        + ","
                        + RunTable.Cols.CALORIE
                        + ","
                        + RunTable.Cols.DURATION
                        + ","
                        + RunTable.Cols.AVERAGE_PACE
                        + ","
                        + RunTable.Cols.AVERAGE_HEART_RATE
                        + ","
                        + RunTable.Cols.DATE
                        + " integer"
                        + " )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}
}
