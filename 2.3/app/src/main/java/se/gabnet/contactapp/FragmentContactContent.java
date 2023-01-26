package se.gabnet.contactapp;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;


public class FragmentContactContent extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>, AdapterView.OnItemClickListener {

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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        contactsList = view.findViewById(R.id.contact_list_view);
        cursorAdapter = new SimpleCursorAdapter(
                context,
                R.layout.list_view_item,
                null,
                COLUMS,
                STORE_IDS,
                0
        );
        contactsList.setAdapter(cursorAdapter);
        contactsList.setOnItemClickListener(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {

    }
}