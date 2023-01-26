package se.gabnet.cooky;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;


public class FragmentRecipeList extends Fragment {

    private Context context;
    private ArrayAdapter<RecipeEditor> adapter;
    private ListView recipeListView;
    private FloatingActionButton newRecipeButton;
    private NavController navigationController;
    private RecipeEditor currentRecipe;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        navigationController = Navigation.findNavController(this.getActivity(),R.id.nav_host_fragment_view);
        super.onViewCreated(view, savedInstanceState);
        recipeListView = view.findViewById(R.id.recipe_list_list_view);
        newRecipeButton = view.findViewById(R.id.new_recipe_button);
        adapter = new ArrayAdapter<>(context,R.layout.activity_list_view_item);



        recipeListView.setOnItemClickListener((parent, view1, position, id) -> {
            Bundle recipeData = null;
            RecipeEditor recipe = (RecipeEditor) recipeListView.getItemAtPosition(position);
            System.out.println("Got recipe with id " + recipe.getId());

            if (currentRecipe != null) {
                if (currentRecipe.getId() != recipe.getId()) {
                    PersistentRecipeEditData.saveToDb(currentRecipe);
                    currentRecipe = PersistentRecipeEditData.getRecipe(recipe.getId());
                }
                recipeData = new Bundle();
                recipeData.putSerializable("recipe",currentRecipe);
            } else {
                currentRecipe = PersistentRecipeEditData.getRecipe(recipe.getId());
                recipeData = new Bundle();
                recipeData.putSerializable("recipe",currentRecipe);
            }

            newRecipeButton.setOnClickListener(v -> {
                if (currentRecipe != null) {
                    PersistentRecipeEditData.saveToDb(currentRecipe);
                }
                currentRecipe = new RecipeEditor();
            });

            navigationController.navigate(R.id.action_fragmentRecipeList_to_fragmentRecipeViewer, recipeData);
        });

        //TODO FETCH RECIPES FROM DATABASE AND ADD TO LIST
        List<RecipeEditor> recipeEditors = PersistentRecipeEditData.fetchRecipesFromDb();
        ArrayList<RecipeEditor> recipeEditors1 = new ArrayList<>();
        for (RecipeEditor recipeEditor : recipeEditors) {
            adapter.add(recipeEditor);
            recipeEditors1.add(recipeEditor);
        }
        for (RecipeEditor recipeEditor : recipeEditors) {
            adapter.add(recipeEditor);
            recipeEditors1.add(recipeEditor);
        }
        for (RecipeEditor recipeEditor : recipeEditors) {
            adapter.add(recipeEditor);
            recipeEditors1.add(recipeEditor);
        }
        adapter = new RecipeListArrayAdapter(context,R.layout.activity_list_view_item, recipeEditors1);

        recipeListView.setAdapter(adapter);

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    public static FragmentRecipeList newInstance(String param1, String param2) {
        FragmentRecipeList fragment = new FragmentRecipeList();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recipe_list, container, false);
    }
}