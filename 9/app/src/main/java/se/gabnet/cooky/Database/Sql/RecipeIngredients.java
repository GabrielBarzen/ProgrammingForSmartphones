package se.gabnet.cooky.Database.Sql;

import android.provider.BaseColumns;

public class RecipeIngredients {
    private RecipeIngredients(){}
    public static class Entry implements BaseColumns {
        public static final String RECIPE = "recipe_id";
        public static final String NUMBER = "recipe_id";
        public static final String TABLE_NAME = "ingredients";
        public static final String COLUMN_TEXT = "text";
        public static final String COLUMN_WEIGHT = "weight";
        public static final String COLUMN_AMOUNT = "amount";
    }
}
