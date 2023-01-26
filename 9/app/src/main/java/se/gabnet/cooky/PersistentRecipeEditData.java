package se.gabnet.cooky;

import java.util.ArrayList;
import java.util.List;

import se.gabnet.cooky.Database.DatabaseController;

public class PersistentRecipeEditData {

    DatabaseController databaseController = DatabaseController.getDatabaseController();

    public static List<RecipeEditor> fetchRecipesFromDb() {
        ArrayList<RecipeEditor> recipeEditors = new ArrayList<>();

        //TODO SQLITE FETCH RECIPES
        recipeEditors.add(new RecipeEditor().setId(-1).setTitle("Baked beans"));
        recipeEditors.add(new RecipeEditor().setId(-2).setTitle("Chili"));
        recipeEditors.add(new RecipeEditor().setId(-3).setTitle("Ketchup"));
        recipeEditors.add(new RecipeEditor().setId(-4).setTitle("Mustard pie"));

        return recipeEditors;
    }

    public static void saveToDb(RecipeEditor currentRecipe) {
        //TODO SAVE RECIPE TO DB
    }

    public static RecipeEditor getRecipe(int id) {
        //TODO FETCH ONE RECIPE FROM DB
        RecipeEditor recipeEditor = new RecipeEditor();
        recipeEditor.setTitle("NOT IMPLEMENTED");
        recipeEditor.setId(id);
        return recipeEditor;
    }

    public static void deleteRecipe(RecipeEditor recipeEditor) {
    }
}


