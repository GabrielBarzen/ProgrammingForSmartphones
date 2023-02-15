package se.gabnet.contactapp;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import kotlin.contracts.ContractBuilder;


public class FragmentContactContent extends Fragment {

    public FragmentContactContent() {
        // Required empty public constructor
    }


    public static FragmentContactContent newInstance(String param1, String param2) {
        FragmentContactContent fragment = new FragmentContactContent();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    Context context;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    ArrayAdapter<String> stringArrayAdapter;
    Button search;
    TextView searchText;

    public boolean getPermissions() {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            permissionActivity.launch(Manifest.permission.READ_CONTACTS);
            permissionActivity.launch(Manifest.permission.WRITE_CONTACTS);
            return permission;
        }
    }
    String[] projection = {
            ContactsContract.Profile._ID,
            ContactsContract.Profile.DISPLAY_NAME_PRIMARY,
    };
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        search = view.findViewById(R.id.search_button);
        searchText = view.findViewById(R.id.search_input_text_view);
        contactsList = view.findViewById(R.id.contact_list_view);
        stringArrayAdapter = new ArrayAdapter<>(context, R.layout.list_view_item);

        if (getPermissions()) {


            Cursor contactcursor = context.getContentResolver().query(ContactsContract.Contacts.CONTENT_URI,
                    projection, null, null, null);
            stringArrayAdapter.clear();
            while (contactcursor.moveToNext()) {

                stringArrayAdapter.add(contactcursor.getString(0) + ", " + contactcursor.getString(1));

            }
        } else {
            Toast.makeText(context,"Must accept to use, restart", Toast.LENGTH_LONG);
        }
        
        search.setOnClickListener(v -> {
            if (getPermissions()) {


                String selection = null;
                if (searchText.getText().toString().length() != 0) {
                    selection = ContactsContract.Profile.DISPLAY_NAME_PRIMARY + " like \"%" +  searchText.getText().toString() + "%\"";
                }

                Cursor contactcursor = context.getContentResolver().query(ContactsContract.Contacts.CONTENT_URI,
                        projection, selection, null, null);
                stringArrayAdapter.clear();
                while (contactcursor.moveToNext()) {

                    stringArrayAdapter.add(contactcursor.getString(0) + ", " + contactcursor.getString(1));

                }
            } else {
                Toast.makeText(context,"Must accept to use, restart", Toast.LENGTH_LONG);
            }
        });

        contactsList.setAdapter(stringArrayAdapter);
        
    }

    ActivityResultLauncher<String> permissionActivity;
    boolean permission = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        permissionActivity = registerForActivityResult(
                new ActivityResultContracts.RequestPermission(),
                v -> {
                    permission = v;
                }
        );

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_contact_content, container, false);
    }

    private static final int ID_INDEX = 0;
    private static final int KEY_INDEX = 1;

    private static final String[] CONTACT_CURSOR_PROJECTION =
            {
                    ContactsContract.Contacts._ID,
                    ContactsContract.Contacts.LOOKUP_KEY,
                    Build.VERSION.SDK_INT
                            >= Build.VERSION_CODES.HONEYCOMB ?
                            ContactsContract.Contacts.DISPLAY_NAME_PRIMARY :
                            ContactsContract.Contacts.DISPLAY_NAME

            };
    private static final String SELECTION =
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ?
                    ContactsContract.Contacts.DISPLAY_NAME_PRIMARY + " LIKE ?" :
                    ContactsContract.Contacts.DISPLAY_NAME + " LIKE ?";

    private String searchString;

    private String[] selectionArgs = { searchString };


    private final static String[] COLUMS = {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ? ContactsContract.Contacts.DISPLAY_NAME_PRIMARY :
                    ContactsContract.Contacts.DISPLAY_NAME
    };
    private final static int[] STORE_IDS = {
            android.R.id.text1
    };

    ListView contactsList;
    long contactId;
    String contactKey;
    Uri contactUri;
    private SimpleCursorAdapter cursorAdapter;
}