package se.gabnet.cooky.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.provider.BaseColumns;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import se.gabnet.cooky.Database.Sql.RecipeIngredients;
import se.gabnet.cooky.Database.Sql.RecipeMeta;
import se.gabnet.cooky.Database.Sql.RecipeSteps;
import se.gabnet.cooky.Model.Ingredient;
import se.gabnet.cooky.Model.Step;
import se.gabnet.cooky.RecipeEditor;

public class DatabaseController {

    private RecipeDatabaseHelper recipeDatabaseHelper;


    public DatabaseController(Context context) {
        recipeDatabaseHelper = new RecipeDatabaseHelper(context);
        DatabaseController.databaseController = this;
    }

    public static DatabaseController getDatabaseController() {
        return DatabaseController.databaseController;
    }

    private static DatabaseController databaseController;

    public void updateRecipe(RecipeEditor recipeEditor) {
        System.out.println("DELETE OLD COPY IF EXIST");
        deleteRecipe(recipeEditor);
        System.out.println("CREATE NEW COPY");
        createRecipe(recipeEditor);

    }
    public void createRecipe(RecipeEditor recipeEditor) {
        System.out.println("SAVING RECIPE WITH ID " + recipeEditor.getId());
        SQLiteDatabase db = recipeDatabaseHelper.getWritableDatabase();
        ContentValues recipeMetaTableContents = new ContentValues();
        recipeMetaTableContents.put(RecipeMeta.Entry.COLUMN_TITLE,recipeEditor.getTitle());
        if (recipeEditor.getImage() != null) {
            recipeMetaTableContents.put(RecipeMeta.Entry.COLUMN_IMAGE, recipeEditor.getImage());
        }
        recipeMetaTableContents.put(RecipeMeta.Entry.COLUMN_DESCRIPTION,recipeEditor.getDescription());

        long newRowId = db.insert(RecipeMeta.Entry.TABLE_NAME, null, recipeMetaTableContents);

        if (recipeEditor.getSteps().size() != 0){
            for (Step step : recipeEditor.getSteps()) {
                ContentValues recipeStepContents = new ContentValues();

                recipeStepContents.put(RecipeSteps.Entry.RECIPE, newRowId);
                recipeStepContents.put(RecipeSteps.Entry.COLUMN_TEXT, step.getText());

                db.insert(RecipeSteps.Entry.TABLE_NAME, null, recipeStepContents);
            }
        }
        if (recipeEditor.getIngredients().size() != 0) {
            for (Ingredient ingredient : recipeEditor.getIngredients()) {
                ContentValues recipeIngredientContents = new ContentValues();
                recipeIngredientContents.put(RecipeIngredients.Entry.RECIPE, newRowId);
                recipeIngredientContents.put(RecipeIngredients.Entry.COLUMN_AMOUNT, ingredient.getAmount());
                recipeIngredientContents.put(RecipeIngredients.Entry.COLUMN_TEXT, ingredient.getText());
                recipeIngredientContents.put(RecipeIngredients.Entry.COLUMN_WEIGHT, ingredient.isWeight());
                db.insert(RecipeIngredients.Entry.TABLE_NAME, null, recipeIngredientContents);
            }
        }
    }

    public void deleteRecipe(RecipeEditor recipeEditor) {
        SQLiteDatabase db = recipeDatabaseHelper.getWritableDatabase();
        System.out.println("DELETING " + recipeEditor.getId());

        String selection = RecipeMeta.Entry._ID + " LIKE ?";
        String[] selectionArgs = { String.valueOf(recipeEditor.getId()) };

        int deletedRows = db.delete(RecipeMeta.Entry.TABLE_NAME, selection, selectionArgs);
        System.out.println("DELETED " + deletedRows + " ROWS");

    }

    public List<RecipeEditor> getRecipeEditorList() {
        SQLiteDatabase db = recipeDatabaseHelper.getReadableDatabase();
//        public static final String TABLE_NAME = "recipe";
//        public static final String COLUMN_TITLE = "title";
//        public static final String COLUMN_DESCRIPTION = "description";
//        public static final String COLUMN_IMAGE = "image";
// Define a projection that specifies which columns from the database
// you will actually use after this query.
        String[] projection = {
                RecipeMeta.Entry._ID,
                RecipeMeta.Entry.COLUMN_TITLE,
        };

// Filter results WHERE "title" = 'My Title'
        String selection = null;
        String[] selectionArgs = null;

// How you want the results sorted in the resulting Cursor
        String sortOrder =
                BaseColumns._ID + " DESC";

        Cursor cursor = db.query(
                RecipeMeta.Entry.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                null,              // The columns for the WHERE clause
                null,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                sortOrder               // The sort order
        );

        List<RecipeEditor> recipeEditors = new ArrayList<>();
        while(cursor.moveToNext()) {

            long recipeID = cursor.getLong(cursor.getColumnIndexOrThrow(BaseColumns._ID));
            System.out.println("FROM DBCONTROLLER GETTING RECIPE WITH ID : " +recipeID+ " AND PUTTING IN RECIPEEDITORS");
            String recipeTitle = cursor.getString(cursor.getColumnIndexOrThrow(RecipeMeta.Entry.COLUMN_TITLE));
            recipeEditors.add(new RecipeEditor(recipeID).setTitle(recipeTitle));
        }
        cursor.close();

        return recipeEditors;
    }

    public RecipeEditor getRecipeEditor(RecipeEditor recipeEditor) {
        SQLiteDatabase db = recipeDatabaseHelper.getReadableDatabase();



        String selection = RecipeMeta.Entry._ID + " = ?";
        long id = recipeEditor.getId();
        String[] selectionArgs = {String.valueOf(id)};

        String sortOrder =
                RecipeMeta.Entry._ID + " ASC";

        System.out.println("EXECUTING QUERY WITH ARGS " + Arrays.toString(selectionArgs) + " WHERE ID IS SET TO " + id + " AND SELECTION IS " + selection);
        Cursor metaCursor = db.query(
                RecipeMeta.Entry.TABLE_NAME,   // The table to query
                null,               // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                sortOrder               // The sort order
        );

        RecipeEditor recipeEditorOut = new RecipeEditor(recipeEditor.getId());
        while(metaCursor.moveToNext()) {

            String recipeTitle = metaCursor.getString(metaCursor.getColumnIndexOrThrow(RecipeMeta.Entry.COLUMN_TITLE));
            String recipeDescription = metaCursor.getString(metaCursor.getColumnIndexOrThrow(RecipeMeta.Entry.COLUMN_DESCRIPTION));
            byte[] recipeImage = metaCursor.getBlob(metaCursor.getColumnIndexOrThrow(RecipeMeta.Entry.COLUMN_IMAGE));

            recipeEditorOut.setTitle(recipeTitle);
            recipeEditorOut.setDescription(recipeTitle);
            recipeEditorOut.setImage(recipeImage);

        }
        metaCursor.close();

        sortOrder = RecipeSteps.Entry._ID + " ASC";
        selection = RecipeSteps.Entry.RECIPE + " = ?";

        Cursor stepCursor = db.query(
                RecipeSteps.Entry.TABLE_NAME,   // The table to query
                null,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                sortOrder               // The sort order
        );


        while(stepCursor.moveToNext()) {

            Step step = new Step();

            String stepText = stepCursor.getString(stepCursor.getColumnIndexOrThrow(RecipeSteps.Entry.COLUMN_TEXT));

            step.setText(stepText);


            recipeEditorOut.getSteps().add(step);
        }
        stepCursor.close();

        sortOrder = RecipeIngredients.Entry._ID + " ASC";
        selection = RecipeIngredients.Entry.RECIPE + " = ?";

        Cursor ingredientCursor = db.query(
                RecipeIngredients.Entry.TABLE_NAME,   // The table to query
                null,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                sortOrder               // The sort order
        );

        while(ingredientCursor.moveToNext()) {
            Ingredient ingredient = new Ingredient();

            String text = ingredientCursor.getString(ingredientCursor.getColumnIndexOrThrow(RecipeIngredients.Entry.COLUMN_TEXT));
            boolean weight = ingredientCursor.getInt(ingredientCursor.getColumnIndexOrThrow(RecipeIngredients.Entry.COLUMN_WEIGHT)) == 1;
            int amount = ingredientCursor.getInt(ingredientCursor.getColumnIndexOrThrow(RecipeIngredients.Entry.COLUMN_AMOUNT));

            ingredient.setText(text);
            ingredient.setAmount(amount);
            ingredient.setWeight(weight);

            recipeEditorOut.getIngredients().add(ingredient);
        }
        ingredientCursor.close();


        return recipeEditorOut;
    }

    public void clearDatabase() {
        recipeDatabaseHelper.clear();
    }

}
