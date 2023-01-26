package se.gabnet.cooky.Database.Sql;

import android.provider.BaseColumns;

public class RecipeSteps {
    private RecipeSteps(){}
    public static class Entry implements BaseColumns {
        public static final String RECIPE = "recipe_id";
        public static final String TABLE_NAME = "steps";
        public static final String NUMBER = "number";
        public static final String COLUMN_TEXT = "text";
    }
}
