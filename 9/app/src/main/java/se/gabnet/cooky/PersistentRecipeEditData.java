package se.gabnet.cooky;

import java.util.List;

import se.gabnet.cooky.Database.DatabaseController;

public class PersistentRecipeEditData {

    static DatabaseController databaseController = DatabaseController.getDatabaseController();

    public static List<RecipeEditor> fetchRecipesFromDb() {
        return databaseController.getRecipeEditorList();
    }

    public static void saveToDb(RecipeEditor currentRecipe) {
        databaseController.updateRecipe(currentRecipe);
    }

    public static RecipeEditor getRecipe(RecipeEditor recipeEditor) {
        return databaseController.getRecipeEditor(recipeEditor);
    }

    public static void deleteRecipe(RecipeEditor recipeEditor) {
        databaseController.deleteRecipe(recipeEditor);
    }

    public static void update(RecipeEditor currentRecipe) {
        databaseController.updateRecipe(currentRecipe);
    }
}


