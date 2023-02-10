package se.gabnet.cooky;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import se.gabnet.cooky.Database.DatabaseController;

public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }
    Context context;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);
        Preference myPref = (Preference) findPreference("delete_content");
        if (myPref != null) {

            myPref.setOnPreferenceClickListener(preference -> {
                DialogInterface.OnClickListener dialogListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                DatabaseController.getDatabaseController().clearDatabase();
                                Toast.makeText(context,"All recipes removed from the database.", Toast.LENGTH_SHORT).show();
                            case DialogInterface.BUTTON_NEGATIVE:

                                //No button clicked
                                break;
                        }
                    }
                };
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogListener)
                        .setNegativeButton("No", dialogListener).show();
                return true;
            });
        }
    }

}