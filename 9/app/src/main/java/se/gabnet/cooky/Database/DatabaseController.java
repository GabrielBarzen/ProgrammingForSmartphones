package se.gabnet.cooky.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
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
        deleteRecipe(recipeEditor);
        createRecipe(recipeEditor);

    }
    public void createRecipe(RecipeEditor recipeEditor) {
        SQLiteDatabase db = recipeDatabaseHelper.getWritableDatabase();

        ContentValues recipeMetaTableContents = new ContentValues();
        recipeMetaTableContents.put(RecipeMeta.Entry.COLUMN_TITLE,recipeEditor.getTitle());

        BitmapDrawable bitmapDrawable = ((BitmapDrawable) recipeEditor.getImage().getDrawable());
        Bitmap bitmap = bitmapDrawable.getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] imageInByte = stream.toByteArray();

        if (recipeEditor.getId() != -1)

        recipeMetaTableContents.put(RecipeMeta.Entry.COLUMN_IMAGE,imageInByte);
        recipeMetaTableContents.put(RecipeMeta.Entry.COLUMN_DESCRIPTION,recipeEditor.getDescription());

        long newRowId = db.insert(RecipeMeta.Entry.TABLE_NAME, null, recipeMetaTableContents);

        for (Step step : recipeEditor.getSteps()) {
            ContentValues recipeStepContents = new ContentValues();
            recipeMetaTableContents.put(RecipeSteps.Entry.RECIPE,newRowId);
            recipeMetaTableContents.put(RecipeSteps.Entry.COLUMN_TEXT,step.getText());
            recipeMetaTableContents.put(RecipeSteps.Entry.NUMBER,step.getNumber());
            db.insert(RecipeSteps.Entry.TABLE_NAME,null,recipeStepContents);
        }
        for (Ingredient ingredient : recipeEditor.getIngredients()) {
            ContentValues recipeIngredientContents = new ContentValues();
            recipeMetaTableContents.put(RecipeIngredients.Entry.RECIPE,newRowId);
            recipeMetaTableContents.put(RecipeIngredients.Entry.COLUMN_AMOUNT,ingredient.getAmount());
            recipeMetaTableContents.put(RecipeIngredients.Entry.COLUMN_TEXT,ingredient.getText());
            recipeMetaTableContents.put(RecipeIngredients.Entry.COLUMN_WEIGHT,ingredient.isWeight());
            recipeMetaTableContents.put(RecipeIngredients.Entry.NUMBER,ingredient.getNumber());
            db.insert(RecipeIngredients.Entry.TABLE_NAME,null,recipeIngredientContents);
        }
    }

    public void deleteRecipe(RecipeEditor recipeEditor) {
        SQLiteDatabase db = recipeDatabaseHelper.getWritableDatabase();

        String selection = RecipeMeta.Entry._ID + " LIKE ?";
        String[] selectionArgs = { String.valueOf(recipeEditor.getId()) };

        int deletedRows = db.delete(RecipeMeta.Entry.TABLE_NAME, selection, selectionArgs);

    }
    public List<RecipeEditor> getRecipeEditorList(RecipeEditor recipeEditor) {
        final String SQL = "";
        List<RecipeEditor> recipeEditorList = new ArrayList<>();
        return null;
    }
    public RecipeEditor getRecipeEditor(RecipeEditor recipeEditor) {
        RecipeEditor recipeEditorList = new RecipeEditor();
        final String SQL = "";
        return null;
    }

}
