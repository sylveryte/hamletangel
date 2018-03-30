package com.codedleaf.sylveryte.hamletangel;

/**
 * Created by sylveryte on 29/3/18.
 * Yay!
 */

class HamletTaskDbSchema {
    static final class HamletTaskTable{
        static final String NAME="tasks";

        static final class Cols{
            static final String UUID = "uuid";
            static final String TEXT = "text";
            static final String TYPE = "type";
            static final String NOTES = "notes";
            static final String DIFFICULTY = "difficulty";
            static final String UPLOADED = "uploaded";
            static final String DUE_DATE = "due_date";
            static final String TASKID = "taskid";
            static final String TIMESTAMP = "local_timestamp";
        }
    }
}
