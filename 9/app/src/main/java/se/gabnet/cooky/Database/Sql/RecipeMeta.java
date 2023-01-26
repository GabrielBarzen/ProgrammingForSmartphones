package se.gabnet.cooky.Database.Sql;


import android.provider.BaseColumns;

public class RecipeMeta {
    private RecipeMeta(){}
    public static class Entry implements BaseColumns {
        public static final String TABLE_NAME = "recipe";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_IMAGE = "image";
    }
}
