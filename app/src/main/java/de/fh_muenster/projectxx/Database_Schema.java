package de.fh_muenster.projectxx;

import android.provider.BaseColumns;

import java.sql.Timestamp;

/**
 * Created by user on 25.05.15.
 */
public final class Database_Schema {
    public Database_Schema(){}

    public static abstract class Projects implements BaseColumns {
        public static final String TABLE_NAME = "project";
        public static final String PROJECT_ID = "projectId";
        public static final String NAME = "name";
        public static final String DESCRIBTION = "describtion";
        public static final String CREATION_DATE = "creationDate";

    }
}
