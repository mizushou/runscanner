package com.example.shouhei.runscanner.runs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

import com.example.shouhei.runscanner.data.Run;
import com.example.shouhei.runscanner.data.database.RunBaseHelper;
import com.example.shouhei.runscanner.data.database.RunCursorWrapper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.example.shouhei.runscanner.data.database.RunDbSchema.RunTable;

public class Runs {

    private static Runs sRuns;
    private SQLiteDatabase mDatabase;
    private Context mContext;

    // singleton
    public static Runs get(Context context) {
        if (sRuns == null) {
            sRuns = new Runs(context);
        }
        return sRuns;
    }

    private Runs(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new RunBaseHelper(mContext).getWritableDatabase();
    }

    // DB-Insert
    public void addRun(Run run) {

        ContentValues values = getContentValues(run);
        mDatabase.insert(RunTable.NAME, null, values);
    }

    // DB-Update
    public void updateRun(Run run) {

        String uuidString = run.getId().toString();
        ContentValues values = getContentValues(run);

        mDatabase.update(
                RunTable.NAME, values, RunTable.Cols.UUID + " =?", new String[] {uuidString});
    }

    // DB-Query[full search]
    public List<Run> getRuns() {

        List<Run> runsList = new ArrayList<>();

        // execute full search
        RunCursorWrapper cursor = queryRuns(null, null);

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                runsList.add(cursor.getRun());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return runsList;
    }

    // DB-Count
    public long getRowCount(long start, long end) {

        return DatabaseUtils.queryNumEntries(
                mDatabase,
                RunTable.NAME,
                RunTable.Cols.DATE + " >= ? AND " + RunTable.Cols.DATE + " < ?",
                new String[] {String.valueOf(start), String.valueOf(end)});
    }

    // DB-Total(int)
    public int getTotalInt(String column, long start, long end) {

        Cursor cursor =
                mDatabase.rawQuery(
                        String.format(
                                "SELECT TOTAL(%s) FROM %s WHERE %s >= ? AND %s < ?",
                                column, "runs", "date", "date"),
                        new String[] {String.valueOf(start), String.valueOf(end)});

        try {
            if (cursor.getCount() == 0) {
                return 0;
            }

            cursor.moveToFirst();
            return cursor.getInt(0);
        } finally {
            cursor.close();
        }
    }

    // DB-Total(double)
    public double getTotalDouble(String column, long start, long end) {

        Cursor cursor =
                mDatabase.rawQuery(
                        String.format(
                                "SELECT TOTAL(%s) FROM %s WHERE %s >= ? AND %s < ?",
                                column, "runs", "date", "date"),
                        new String[] {String.valueOf(start), String.valueOf(end)});

        try {
            if (cursor.getCount() == 0) {
                return 0;
            }

            cursor.moveToFirst();
            return cursor.getDouble(0);
        } finally {
            cursor.close();
        }
    }

    private RunCursorWrapper queryRuns(String whereClause, String[] whereArgs) {
        Cursor cursor =
                mDatabase.query(RunTable.NAME, null, whereClause, whereArgs, null, null, null);

        return new RunCursorWrapper(cursor);
    }

    public File getPhotoFile(Run run) {
        File fileDir = mContext.getFilesDir();
        return new File(fileDir, run.getPhotoFilename());
    }

    private static ContentValues getContentValues(Run run) {
        ContentValues values = new ContentValues();
        values.put(RunTable.Cols.UUID, run.getId().toString());
        values.put(RunTable.Cols.DATE, run.getDate());
        values.put(RunTable.Cols.DISTANCE, run.getDistance());
        values.put(RunTable.Cols.CALORIE, run.getCalorie());
        values.put(RunTable.Cols.DURATION, run.getDuration());
        values.put(RunTable.Cols.AVERAGE_PACE, run.getAvePace());
        values.put(RunTable.Cols.AVERAGE_HEART_RATE, run.getAveHeartRate());

        return values;
    }
}
