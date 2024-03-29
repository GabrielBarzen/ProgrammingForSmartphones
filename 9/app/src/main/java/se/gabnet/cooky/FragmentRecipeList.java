package se.gabnet.cooky;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        navigationController = Navigation.findNavController(this.getActivity(),R.id.nav_host_fragment_view);
        super.onViewCreated(view, savedInstanceState);

        recipeListView = view.findViewById(R.id.recipe_list_list_view);
        newRecipeButton = view.findViewById(R.id.new_recipe_button);
        adapter = new ArrayAdapter<>(context,R.layout.activity_list_view_item);

        RandomRecipe.update(context);

        recipeListView.setOnItemClickListener((parent, view1, position, id) -> {
            RecipeEditor recipe = (RecipeEditor) recipeListView.getItemAtPosition(position);
            recipe = DatabaseController.getDatabaseController().getRecipeEditor(recipe);
            System.out.println(recipe.printData());
            navigateToRecipe(recipe);
        });

        newRecipeButton.setOnClickListener(v -> {
            RecipeEditor recipe = new RecipeEditor();
            navigateToRecipe(recipe);
        });


        adapter = new RecipeListArrayAdapter(context,R.layout.activity_list_view_item, DatabaseController.getDatabaseController().getRecipeEditorList(), this);
        for (int i = 0; i < adapter.getCount(); i++) {
            System.out.println("Items in adapter " + adapter.getItem(i).getId());
        }
        recipeListView.setAdapter(adapter);
    }

    public void navigateToRecipe(RecipeEditor recipe){
        Bundle data = new Bundle();
        data.putSerializable("recipe",recipe);
        navigationController.navigate(R.id.action_fragmentRecipeList_to_fragmentRecipeViewer, data);
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