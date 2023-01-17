package se.gabnet.phoneapp;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.loader.content.CursorLoader;

import android.provider.CallLog;
import android.telephony.PhoneStateListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;


public class Phone extends Fragment {
    Context context;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    private ListView listView;
    private ArrayAdapter<Number> adapter;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        listView = view.findViewById(R.id.phone_call_list);
        adapter = new ArrayAdapter<Number>(context,R.layout.activity_listview);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener((parent, view1, position, id) -> {
            Number Number = (Number) parent.getItemAtPosition(position);
            String phoneNumberUri = "tel:" + Number.getNumber().trim();
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse(phoneNumberUri));
            startActivity(intent);
        });

        requestPermission();
        System.out.println("Check if permission");
        if (permission) {
            System.out.println("Got permission");
            Uri calls = Uri.parse("content://call_log/calls");

            String[] fields = {
                    CallLog.Calls.NUMBER,
                    CallLog.Calls.CACHED_NUMBER_TYPE,
            };
            String order = CallLog.Calls.DATE + " DESC";

            CursorLoader cursorLoader = new CursorLoader(context);
            cursorLoader.setUri(calls);
            cursorLoader.setSortOrder(order);
            System.out.println("Loading cursor");
            Cursor managedCursor = cursorLoader.loadInBackground();
            System.out.println("Getting call log");
            int number = managedCursor.getColumnIndex(CallLog.Calls.NUMBER);
            int type = managedCursor.getColumnIndex(CallLog.Calls.TYPE);
            int date = managedCursor.getColumnIndex(CallLog.Calls.DATE);
            int name = managedCursor.getColumnIndex(CallLog.Calls.CACHED_NAME);
            while (managedCursor.moveToNext()) {
                Number number1 = new Number();
                number1.setNumber(managedCursor.getString(number));
                number1.setName(managedCursor.getString(name));
                number1.setDate(managedCursor.getString(date));
                number1.setType(managedCursor.getString(type));
                adapter.add(number1);
            }
            System.out.println("End getting call log");
        } else {
            String perm = "Need to grant permission to use app";
            Toast.makeText(context,perm, Toast.LENGTH_SHORT);
        }

    }

    int CALL_LOG_READ_CODE = 1;
    private void requestPermission() {
        if (ActivityCompat.checkSelfPermission(context,Manifest.permission.READ_CALL_LOG) == PackageManager.PERMISSION_GRANTED) {
            permission = true;
        } else {
            permissionActivity.launch(Manifest.permission.READ_CALL_LOG);
        }
    }


    boolean permission = false;
    ActivityResultLauncher<String> permissionActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        permissionActivity = registerForActivityResult(
                new ActivityResultContracts.RequestPermission(),
                new ActivityResultCallback<Boolean>() {
                    @Override
                    public void onActivityResult(Boolean result) {
                        if (result) permission = true;
                        else permission = false;
                    }
                }

        );
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_phone, container, false);
    }
}