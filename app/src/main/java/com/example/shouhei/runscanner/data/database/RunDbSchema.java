package com.example.shouhei.runscanner.data.database;

public class RunDbSchema {
    // ---------------------inner class#1 [Table] ---------------------//
    public static final class RunTable {
        public static final String NAME = "runs";

        // ---------------------inner class#2 [Columns] ---------------------//
        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String DISTANCE = "distance";
            public static final String CALORIE = "calorie";
            public static final String DURATION = "duration";
            public static final String AVERAGE_PACE = "average_pace";
            public static final String AVERAGE_HEART_RATE = "averate_heart_rate";
            public static final String DATE = "date";
        }
    }
}
