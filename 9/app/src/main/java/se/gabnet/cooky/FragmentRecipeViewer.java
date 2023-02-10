package se.gabnet.cooky;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
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
import android.widget.ScrollView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.ByteArrayInputStream;
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
    private Button addIngredientButton, addStepButton ;
    private ImageButton imageAddButton;
    private FloatingActionButton saveButton;
    private List<Ingredient> ingredients;
    private List<Step> steps;
    private RecipeEditor recipeEditor;
    private EditText title;
    private EditText description;
    private ImageView recipeImage;
    private ScrollView scrollView;


    public void save() {
        System.out.println(recipeEditor.printData());
        long newId = PersistentRecipeEditData.update(recipeEditor, context);
        recipeEditor.setId(newId);
    }

    int GALLERY_RESULT_CODE = 1;
    ActivityResult galleryResult = new ActivityResult(GALLERY_RESULT_CODE,null);



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addIngredientButton = view.findViewById(R.id.add_ingredient_button);
        addStepButton = view.findViewById(R.id.add_step_button);
        title = view.findViewById(R.id.recipe_title_text_view);
        description = view.findViewById(R.id.recipe_description_text_view);
        recipeImage = view.findViewById(R.id.recipe_image);
        saveButton = view.findViewById(R.id.save_button);
        scrollView = view.findViewById(R.id.editor_scroll_view);
        imageAddButton = view.findViewById(R.id.add_image_button);


        View.OnClickListener imageAddListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (requestPermission(Manifest.permission.READ_EXTERNAL_STORAGE)) {

                    galleryActivity.launch(
                            new PickVisualMediaRequest.Builder()
                                    .setMediaType((ActivityResultContracts.PickVisualMedia.VisualMediaType) ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE)
                                    .build()
                    );
                }
            }
        };
        
        imageAddButton.setOnClickListener(imageAddListener);
        
        recipeImage.setOnClickListener(imageAddListener);

        if (getArguments() != null) {
            recipeEditor = (RecipeEditor) getArguments().getSerializable("recipe");

        }

        if (recipeEditor == null) {
            recipeEditor = new RecipeEditor();
        } else {
            title.setText(recipeEditor.getTitle());
            description.setText(recipeEditor.getDescription());
            if (recipeEditor.getImage() != null) {
                recipeImage.setImageBitmap(BitmapFactory.decodeByteArray(recipeEditor.getImage(), 0, recipeEditor.getImage().length));
            }
            for (Ingredient ingredient : recipeEditor.getIngredients()) {
                addIngredient(view, ingredient);
            }
            for (Step step : recipeEditor.getSteps()) {
                addStep(view, step);
            }
        }

        ingredients = recipeEditor.getIngredients();
        steps = recipeEditor.getSteps();

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

    private boolean requestPermission(String check) {
        if (ActivityCompat.checkSelfPermission(context, check) == PackageManager.PERMISSION_GRANTED){
            return true;
        } else {
            permissionActivity.launch(check);
            return permission;
        }
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
    private ActivityResultLauncher<String> permissionActivity;
    private boolean permission;




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    ActivityResultLauncher<PickVisualMediaRequest> galleryActivity;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        permissionActivity = registerForActivityResult(
                new ActivityResultContracts.RequestPermission(),
                new ActivityResultCallback<Boolean>() {
                    @Override
                    public void onActivityResult(Boolean result) {
                        permission = result;
                    }
                }

        );
        galleryActivity = registerForActivityResult(
                new ActivityResultContracts.PickVisualMedia(),
                new ActivityResultCallback<Uri>() {
                    @Override
                    public void onActivityResult(Uri result) {
                        recipeImage.setImageURI(result);
                        imageAddButton.setVisibility(View.GONE);
                        BitmapDrawable bitmapDrawable = ((BitmapDrawable) recipeImage.getDrawable());
                        Bitmap bitmap = bitmapDrawable .getBitmap();
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                        byte[] imageInByte = stream.toByteArray();
                        recipeEditor.setImage(imageInByte);
                    }
                }
        );




        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recipe_viewer, container, false);
    }

}