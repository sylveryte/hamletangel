package com.codedleaf.sylveryte.hamletangel;

/**
 * Created by sylveryte on 29/3/18.
 * Yay!
 */

public class HamletTaskDbSchema {
    public static final class HamletTaskTable{
        public static final String NAME="tasks";

        public static final class Cols{
            public static final String UUID = "uuid";
            public static final String TEXT = "text";
            public static final String TYPE = "type";
            public static final String NOTES = "notes";
            public static final String DIFFICULTY = "difficulty";
            public static final String UPLOADED = "uploaded";
            public static final String DUE_DATE = "due_date";
            public static final String TASKID = "taskid";
            public static final String TIMESTAMP = "local_timestamp";
        }
    }
}
