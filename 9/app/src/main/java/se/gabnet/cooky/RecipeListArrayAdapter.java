package se.gabnet.cooky;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.util.List;

public class RecipeListArrayAdapter extends ArrayAdapter<RecipeEditor> {

    private Context context;
    private List<RecipeEditor> recipes;
    private FragmentRecipeList fragmentRecipeList;

    public RecipeListArrayAdapter(@NonNull Context context, int resource, @NonNull List<RecipeEditor> recipes, FragmentRecipeList fragmentRecipeList) {
        super(context, resource, recipes);
        this.context = context;
        this.recipes = recipes;
        this.fragmentRecipeList = fragmentRecipeList;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        RecipeListArrayAdapter adapter = this;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.activity_list_view_item, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.recipe_id_title_text);
        textView.setText(recipes.get(position).toString());
        ImageButton buttonView = (ImageButton) rowView.findViewById(R.id.remove_button_recipe);
        buttonView.setOnClickListener(v -> {
                DialogInterface.OnClickListener dialogListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                Toast.makeText(context,"Removing " + recipes.get(position).getTitle() + "." , Toast.LENGTH_SHORT).show();
                                System.out.println("removing " + recipes.get(position));
                                PersistentRecipeEditData.deleteRecipe(recipes.get(position), context);
                                recipes.remove(position);
                                fragmentRecipeList.updateAdapter(adapter);
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                System.out.println("not removing " + recipes.get(position));
                                //No button clicked
                                break;
                        }
                    }
                };
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogListener)
                    .setNegativeButton("No", dialogListener).show();

        });

        return rowView;
    }



}
