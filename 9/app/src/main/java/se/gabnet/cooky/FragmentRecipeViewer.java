package se.gabnet.cooky;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.google.android.material.internal.TextWatcherAdapter;

import java.util.ArrayList;

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

    DrawerLayout drawerLayout;
    Button addIngredientButton, addStepButton;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        drawerLayout = view.getRootView().findViewById(R.id.main_drawer_layout);
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        addIngredientButton = view.findViewById(R.id.add_ingredient_button);
        addStepButton = view.findViewById(R.id.add_step_button);


        addIngredientButton.setOnClickListener(v -> {
            View ingredient = View.inflate(context,R.layout.recipe_viewer_ingeredient,null);
            LinearLayout newParent = view.findViewById(R.id.ingredient_container_constraint_layout);

            EditText number = ingredient.findViewById(R.id.ingredient_amount_in_gml);
            EditText name = ingredient.findViewById(R.id.ingredient_text_view);
            CheckBox weight = ingredient.findViewById(R.id.weight_check);

            ImageButton removeButton = ingredient.findViewById(R.id.remove_button);
            removeButton.setOnClickListener(v1 -> {

                newParent.removeView(ingredient);
            });

            newParent.addView(ingredient);
        });

        addStepButton.setOnClickListener(v -> {
            View step = View.inflate(context,R.layout.recipe_viewer_step,null);
            LinearLayout newParent = view.findViewById(R.id.step_container_constraint_layout);

            EditText stepText = step.findViewById(R.id.step_text_view);
            ImageButton removeButton = step.findViewById(R.id.remove_button);

            removeButton.setOnClickListener(v1 -> {
                newParent.removeView(step);
            });
            stepText.setText("testingtesting");

            newParent.addView(step);

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