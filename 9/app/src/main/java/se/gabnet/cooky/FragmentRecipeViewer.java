package se.gabnet.cooky;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.ByteArrayOutputStream;
import java.util.List;

import se.gabnet.cooky.Database.DatabaseController;
import se.gabnet.cooky.Model.Ingredient;
import se.gabnet.cooky.Model.Step;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentRecipeViewer#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentRecipeViewer extends Fragment {


    public FragmentRecipeViewer() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentRecipeViewer.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentRecipeViewer newInstance(String param1, String param2) {
        FragmentRecipeViewer fragment = new FragmentRecipeViewer();

        return fragment;
    }
Context context;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }
    /*TODO
       1. Get recipe from bundle
       1.1 populate with existing info
       2. Save recipe
       3. update recipe as changes are made
       4. save to db at some point
       5. handle exiting this interface

     */


    private DrawerLayout drawerLayout;
    private Button addIngredientButton, addStepButton;
    private FloatingActionButton saveButton;
    private List<Ingredient> ingredients;
    private List<Step> steps;
    private RecipeEditor recipeEditor;
    private EditText title;
    private EditText description;
    private ImageView imageView;


    public void save() {
        DatabaseController.getDatabaseController().updateRecipe(recipeEditor);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        drawerLayout = view.getRootView().findViewById(R.id.main_drawer_layout);
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        addIngredientButton = view.findViewById(R.id.add_ingredient_button);
        addStepButton = view.findViewById(R.id.add_step_button);
        title = view.findViewById(R.id.recipe_title_text_view);
        description = view.findViewById(R.id.recipe_description_text_view);
        imageView = view.findViewById(R.id.recipe_image);
        saveButton = view.findViewById(R.id.save_button);

        if (getArguments() != null) {
            recipeEditor = (RecipeEditor) getArguments().getSerializable("recipe");
        }

        if (recipeEditor == null) {
            recipeEditor = new RecipeEditor();
        } else {
            title.setText(recipeEditor.getTitle());
            description.setText(recipeEditor.getDescription());
            imageView.setImageBitmap(BitmapFactory.decodeByteArray(recipeEditor.getImage(),0,recipeEditor.getImage().length));
            for (Ingredient ingredient : recipeEditor.getIngredients()) {
                addIngredient(view, ingredient);
            }
            for (Step step : recipeEditor.getSteps()) {
                addStep(view, step);
            }
        }

        ingredients = recipeEditor.getIngredients();
        steps = recipeEditor.getSteps();

        imageView.setOnClickListener(v -> {
            //TODO Open gallery get image and replace it in imageview
            Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            recipeEditor.setImage(baos.toByteArray());
        });

        title.addTextChangedListener(new ListUpdateOnTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                recipeEditor.setTitle(s.toString());
            }
        });

        description.addTextChangedListener(new ListUpdateOnTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                recipeEditor.setDescription(s.toString());
            }
        });

        addIngredientButton.setOnClickListener(v -> {
            Ingredient ingredient = new Ingredient();
            ingredients.add(ingredient);
            addIngredient(view, ingredient);

        });

        addStepButton.setOnClickListener(v -> {
            Step step = new Step();
            steps.add(step);
            addStep(view,step);

        });

        saveButton.setOnClickListener(v -> {
            save();
        });
    }


    private void addIngredient(View view, Ingredient ingredient) {
        View ingredientView = View.inflate(context,R.layout.recipe_viewer_ingeredient,null);
        LinearLayout newParent = view.findViewById(R.id.ingredient_container_constraint_layout);

        EditText amount = ingredientView.findViewById(R.id.ingredient_amount_in_gml);
        EditText text = ingredientView.findViewById(R.id.ingredient_text_view);
        CheckBox weight = ingredientView.findViewById(R.id.weight_check);

        amount.setText(String.valueOf(ingredient.getAmount()));
        text.setText(ingredient.getText());
        weight.setChecked(ingredient.isWeight());

        text.addTextChangedListener(new ListUpdateOnTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                ingredient.setText(s.toString());
                System.out.println("new ingredient text : " + ingredients.get(0).getText());
            }
        });
        amount.addTextChangedListener(new ListUpdateOnTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                try {
                    ingredient.setAmount(Integer.parseInt(s.toString()));
                } catch (NumberFormatException e) {
                    System.out.println("NOT NUMBER IN NUMBER FIELD");
                }
            }
        });

        weight.setChecked(true);
        weight.setOnClickListener(n -> {
            if (weight.isChecked()) {
                weight.setText("g");
            } else {
                weight.setText("ml");
            }
        });

        try {
            ingredient.setAmount(Integer.parseInt(amount.getText().toString()));
        } catch (NumberFormatException e) {
            System.out.println("NOT NUMBER IN NUMBER FIELD");
        }


        ImageButton removeButton = ingredientView.findViewById(R.id.remove_button);
        removeButton.setOnClickListener(v1 -> {
            newParent.removeView(ingredientView);
            ingredients.remove(ingredient);
        });

        newParent.addView(ingredientView);
    }


    private void addStep(View view, Step step) {

        View stepView = View.inflate(context,R.layout.recipe_viewer_step,null);
        LinearLayout newParent = view.findViewById(R.id.step_container_constraint_layout);

        EditText stepText = stepView.findViewById(R.id.step_text_view);
        ImageButton removeButton = stepView.findViewById(R.id.remove_button);

        stepText.setText(step.getText());

        removeButton.setOnClickListener(v1 -> {
            newParent.removeView(stepView);
        });
        stepText.setText(step.getText());
        newParent.addView(stepView);

        stepText.addTextChangedListener(new ListUpdateOnTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                step.setText(s.toString());
            }
        });

    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recipe_viewer, container, false);
    }
}