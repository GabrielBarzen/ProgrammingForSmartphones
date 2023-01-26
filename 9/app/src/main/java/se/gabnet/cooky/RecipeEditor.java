package se.gabnet.cooky;

import android.widget.ImageView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import se.gabnet.cooky.Model.Ingredient;
import se.gabnet.cooky.Model.Step;

public class RecipeEditor implements Serializable {
    private List<Step> steps;
    private List<Ingredient> ingredients;
    private ImageView image;
    private String description;
    private String title;
    private int id;

    public RecipeEditor(ImageView image) {
        this.steps = new ArrayList<>();
        this.ingredients = new ArrayList<>();
        this.image = image;
        this.description = "Enter description here";
        this.id = -1;
    }

    public RecipeEditor() {
        this.steps = new ArrayList<>();
        this.ingredients = new ArrayList<>();
        this.image = null; //TODO CREATE WHEN NEEDED
        this.description = "Enter description here";
        this.id = -1;
    }
    public RecipeEditor(List<Step> steps, List<Ingredient> ingredients, ImageView image, String description) {
        this.steps = new ArrayList<>();
        this.ingredients = new ArrayList<>();
        this.image = image;
        this.description = "Enter description here";
        this.id = -1;
    }

    public RecipeEditor setId(int id) {
        this.id = id;
        return this;
    }
    public int getId() {
        return id;
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

    public ImageView getImage() {
        return image;
    }

    public void setImage(ImageView image) {
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
        return "{" +
                " " + id +
                ", " + title +
                '}';
    }
}
