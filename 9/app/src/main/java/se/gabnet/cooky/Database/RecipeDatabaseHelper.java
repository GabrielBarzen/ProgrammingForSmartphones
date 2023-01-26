package se.gabnet.cooky.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import se.gabnet.cooky.Database.Sql.RecipeIngredients;
import se.gabnet.cooky.Database.Sql.RecipeMeta;
import se.gabnet.cooky.Database.Sql.RecipeSteps;

public class RecipeDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Cooky.db";
    private static final int DATABASE_VERSION = 1;

    private static final String SQL_CREATE_META_TABLE =
            "CREATE TABLE " + RecipeMeta.Entry.TABLE_NAME + " (" +
                RecipeMeta.Entry._ID + " INTEGER PRIMARY KEY," +
                RecipeMeta.Entry.COLUMN_TITLE + " TEXT," +
                RecipeMeta.Entry.COLUMN_IMAGE + " BLOB," +
                RecipeMeta.Entry.COLUMN_DESCRIPTION + " TEXT)";

    private static final String SQL_CREATE_STEPS_TABLE =
            "CREATE TABLE " + RecipeSteps.Entry.TABLE_NAME + " (" +
                RecipeSteps.Entry.RECIPE + " INTEGER," +
                    RecipeSteps.Entry.NUMBER + " INTEGER," +

                    RecipeSteps.Entry.COLUMN_TEXT + " TEXT," +
            "   FOREIGN KEY(recipe_id) REFERENCES recipe ON DELETE CASCADE)";

    private static final String SQL_CREATE_INGREDIENT_TABLE =
            "CREATE TABLE " + RecipeIngredients.Entry.TABLE_NAME + " (" +
                RecipeIngredients.Entry.RECIPE + " INTEGER," +
                    RecipeSteps.Entry.NUMBER + " INTEGER," +

                    RecipeIngredients.Entry.COLUMN_TEXT + " TEXT," +
                RecipeIngredients.Entry.COLUMN_WEIGHT + " INTEGER," +
                RecipeIngredients.Entry.COLUMN_AMOUNT + " INTEGER," +
            "   FOREIGN KEY(recipe_id) REFERENCES recipe ON DELETE CASCADE);";

    private static final String SQL_DELETE_INGREDIENTS =
            "DROP TABLE IF EXISTS " + RecipeIngredients.Entry.TABLE_NAME +";";
    private static final String SQL_DELETE_STEPS =
            "DROP TABLE IF EXISTS " + RecipeSteps.Entry.TABLE_NAME +";";
    private static final String SQL_DELETE_META =
            "DROP TABLE IF EXISTS " + RecipeMeta.Entry.TABLE_NAME+ ";";


    RecipeDatabaseHelper(Context context) {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_META_TABLE);
        db.execSQL(SQL_CREATE_STEPS_TABLE);
        db.execSQL(SQL_CREATE_INGREDIENT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_INGREDIENTS);
        db.execSQL(SQL_DELETE_STEPS);
        db.execSQL(SQL_DELETE_META);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

}
