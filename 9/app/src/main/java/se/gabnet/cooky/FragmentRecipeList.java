package se.gabnet.cooky;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

import se.gabnet.cooky.Database.DatabaseController;


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
            System.out.println("GETTING " + recipe.getId());

            if (currentRecipe != null) {
                System.out.println("ATTEMPT SAVE BEFORE LOADING NEW");
                if (currentRecipe.getId() != recipe.getId()) {
                    System.out.println("UPDATING OLD");
                    PersistentRecipeEditData.update(currentRecipe);
                    System.out.println("FETCHING NEW WITH ID " + recipe.getId());
                    currentRecipe = PersistentRecipeEditData.getRecipe(recipe);
                    System.out.println("GOT NEW WITH ID " + recipe.getId());
                }
                recipeData = new Bundle();
                recipeData.putSerializable("recipe",currentRecipe);
            } else {
                currentRecipe = PersistentRecipeEditData.getRecipe(recipe);
                recipeData = new Bundle();
                recipeData.putSerializable("recipe",currentRecipe);
            }

            navigationController.navigate(R.id.action_fragmentRecipeList_to_fragmentRecipeViewer, recipeData);
        });
        boolean pressed = false;
        newRecipeButton.setOnClickListener(v -> {
            System.out.println("PRESSING NEW RECIPE CLICKED");
            if (!pressed) {
                if (currentRecipe != null) {
                    System.out.println("SAVING BEFORE SWITCHING");
                    PersistentRecipeEditData.saveToDb(currentRecipe);
                }
                int newId = adapter.getCount() + 1;
                currentRecipe = new RecipeEditor(newId);
                System.out.println("ADDING NEW RECIPE TO DB");
                PersistentRecipeEditData.saveToDb(currentRecipe);

                Bundle recipeData = new Bundle();
                recipeData.putSerializable("recipe", currentRecipe);
                navigationController.navigate(R.id.action_fragmentRecipeList_to_fragmentRecipeViewer, recipeData);
            }
        });

        adapter = new RecipeListArrayAdapter(context,R.layout.activity_list_view_item, DatabaseController.getDatabaseController().getRecipeEditorList(), this);
        for (int i = 0; i < adapter.getCount(); i++) {
            System.out.println("Items in adapter " + adapter.getItem(i).getId());
        }
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

    public void updateAdapter(RecipeListArrayAdapter adapter) {
        recipeListView.setAdapter(adapter);
    }
}