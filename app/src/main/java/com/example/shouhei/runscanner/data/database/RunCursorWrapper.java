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
        String distance = getString(getColumnIndex(RunTable.Cols.DISTANCE));
        String calorie = getString(getColumnIndex(RunTable.Cols.CALORIE));
        String duration = getString(getColumnIndex(RunTable.Cols.DURATION));
        String avePace = getString(getColumnIndex(RunTable.Cols.AVERAGE_PACE));
        String aveHeartRate = getString(getColumnIndex(RunTable.Cols.AVERAGE_HEART_RATE));

        Run run = new Run(UUID.fromString(uuidString));
        run.setDistance(distance);
        run.setCalorie(calorie);
        run.setDuration(duration);
        run.setAvePace(avePace);
        run.setAveHeartRate(aveHeartRate);

        return run;
    }
}
