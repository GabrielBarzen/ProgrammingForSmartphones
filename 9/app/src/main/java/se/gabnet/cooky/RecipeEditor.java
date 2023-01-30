package se.gabnet.cooky;

import android.widget.ImageView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import se.gabnet.cooky.Model.Ingredient;
import se.gabnet.cooky.Model.Step;

public class RecipeEditor implements Serializable {
    private List<Step> steps = new ArrayList<>();
    private List<Ingredient> ingredients = new ArrayList<>();
    private byte[] image = {};
    private String description = "";
    private String title = "";
    private long id = -1;


    public long getId() {
        return id;
    }

    public RecipeEditor setId(long id) {
        this.id = id;
        return this;
    }


    public List<Step> getSteps() {
        return steps;
    }

    public void setSteps(List<Step> steps) {
        this.steps = steps;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public RecipeEditor setTitle(String title) {
        this.title = title;
        return this;
    }

    @Override
    public String toString() {
        return title;
    }

    public String printData() {
        String output = String.format("Title : %s, Description : %s, ingredients: %s, steps %s. ",title,description,ingredients.toString(),steps.toString());
        return output;
    }
}
