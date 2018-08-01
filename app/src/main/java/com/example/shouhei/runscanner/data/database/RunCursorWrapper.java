package com.example.shouhei.runscanner.data.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.example.shouhei.runscanner.data.Run;

import java.util.UUID;

import static com.example.shouhei.runscanner.data.database.RunDbSchema.*;

public class RunCursorWrapper extends CursorWrapper {

    public RunCursorWrapper(Cursor cursor) {

        super(cursor);
    }

    public Run getRun() {

        String uuidString = getString(getColumnIndex(RunTable.Cols.UUID));
        long date = getLong(getColumnIndex(RunTable.Cols.DATE));
        double distance = getDouble(getColumnIndex(RunTable.Cols.DISTANCE));
        int calorie = getInt(getColumnIndex(RunTable.Cols.CALORIE));
        int duration = getInt(getColumnIndex(RunTable.Cols.DURATION));
        int avePace = getInt(getColumnIndex(RunTable.Cols.AVERAGE_PACE));
        int aveHeartRate = getInt(getColumnIndex(RunTable.Cols.AVERAGE_HEART_RATE));

        Run run = new Run(UUID.fromString(uuidString));
        run.setDate(date);
        run.setDistance(distance);
        run.setCalorie(calorie);
        run.setDuration(duration);
        run.setAvePace(avePace);
        run.setAveHeartRate(aveHeartRate);

        return run;
    }
}
